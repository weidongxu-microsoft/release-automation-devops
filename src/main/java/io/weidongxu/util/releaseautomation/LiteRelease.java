package io.weidongxu.util.releaseautomation;

import com.azure.core.credential.BasicAuthenticationCredential;
import com.azure.core.credential.TokenCredential;
import com.azure.core.exception.HttpResponseException;
import com.azure.core.http.HttpMethod;
import com.azure.core.http.HttpPipeline;
import com.azure.core.http.HttpPipelineBuilder;
import com.azure.core.http.HttpRequest;
import com.azure.core.http.HttpResponse;
import com.azure.core.http.policy.HttpLogDetailLevel;
import com.azure.core.http.policy.HttpLogOptions;
import com.azure.core.management.AzureEnvironment;
import com.azure.core.management.profile.AzureProfile;
import com.azure.core.util.Configuration;
import com.azure.core.util.CoreUtils;
import com.azure.dev.DevManager;
import com.azure.dev.models.Pipeline;
import com.azure.dev.models.Run;
import com.azure.dev.models.RunPipelineParameters;
import com.azure.dev.models.RunState;
import com.azure.dev.models.Timeline;
import com.azure.dev.models.TimelineRecord;
import com.azure.dev.models.TimelineRecordState;
import com.azure.dev.models.Variable;
import com.azure.identity.AzureCliCredentialBuilder;
import org.kohsuke.github.GHIssueState;
import org.kohsuke.github.GHPullRequest;
import org.kohsuke.github.GHPullRequestReview;
import org.kohsuke.github.GHPullRequestReviewEvent;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LiteRelease {

    private static final Logger LOGGER = LoggerFactory.getLogger(LiteRelease.class);

    private static final String USER = Configuration.getGlobalConfiguration().get("DEVOPS_USER");
    private static final String PASS = Configuration.getGlobalConfiguration().get("DEVOPS_PAT");
    private static final String ORGANIZATION = "azure-sdk";
    private static final String PROJECT_INTERNAL = "internal";
    private static final String PROJECT_PUBLIC = "public";

    private static final String GITHUB_TOKEN = Configuration.getGlobalConfiguration().get("GITHUB_PAT");
    private static final String GITHUB_ORGANIZATION = "Azure";
    private static final String GITHUB_PROJECT = "azure-sdk-for-java";
    private static final String SPECS_PROJECT = "azure-rest-api-specs";

    private static final int LITE_CODEGEN_PIPELINE_ID = 2238;

    private static final String CI_CHECK_ENFORCER_NAME = "https://aka.ms/azsdk/checkenforcer";
    private static final String CI_PREPARE_PIPELINES_NAME = "prepare-pipelines";

    private static final String API_SPECS_YAML_PATH = "https://raw.githubusercontent.com/Azure/azure-sdk-for-java/main/eng/automation/api-specs.yaml";

    private static final String MAVEN_ARTIFACT_PATH_PREFIX = "https://central.sonatype.com/artifact/com.azure.resourcemanager/";

    private static final InputStream IN = System.in;
    private static final PrintStream OUT = System.out;

    private static final boolean PROMPT_CONFIRMATION = true;
    private static final boolean PREFER_STABLE_TAG = false;

    private static final long POLL_SHORT_INTERVAL_MINUTE = 1;
    private static final long POLL_LONG_INTERVAL_MINUTE = 5;
    private static final long MILLISECOND_PER_MINUTE = 60 * 1000;

    public static void main(String[] args) throws Exception {
        TokenCredential tokenCredential;
        if (CoreUtils.isNullOrEmpty(PASS)) {
            LOGGER.info("No AzureDevOps PAT provided, using Azure DevOps CLI authentication.");
            tokenCredential = new AzureCliCredentialBuilder().build();
        } else {
            LOGGER.info("AzureDevOps PAT found, using Basic authentication.");
            tokenCredential = new BasicAuthenticationCredential(USER, PASS);
        }
        DevManager manager = DevManager.configure()
            .withLogOptions(new HttpLogOptions().setLogLevel(HttpLogDetailLevel.NONE))
            .authenticate(
                tokenCredential,
                new AzureProfile(AzureEnvironment.AZURE));

        GitHub github = new GitHubBuilder()
            .withOAuthToken(GITHUB_TOKEN).build();

        GHRepository sdkRepository = github.getRepository(GITHUB_ORGANIZATION + "/" + GITHUB_PROJECT);
        GHRepository specsRepository = github.getRepository(GITHUB_ORGANIZATION + "/" + SPECS_PROJECT);

        Configure configure = getConfigure();
        String tspConfigUrl = configure.getTspConfig();
        String service;
        String sdk;
        String prKeyword;
        Map<String, Variable> variables = new HashMap<>();
        Map<String, String> templateParameters = new HashMap<>();

        if (!CoreUtils.isNullOrEmpty(tspConfigUrl)) { // generate from TypeSpec
            TspConfig tspConfig = TspConfig.parse(specsRepository, HTTP_PIPELINE, tspConfigUrl);
            service = tspConfig.getService();
            if (CoreUtils.isNullOrEmpty(service)) {
                throw new IllegalArgumentException("\"service\" must not be null if Generated from TypeSpec.");
            }
            prKeyword = service;
            OUT.println("Releasing from TypeSpec, tsp-config file with commitID: \n" + tspConfig.getUrl());
            OUT.println("service: " + service);
            OUT.println("package-dir: " + tspConfig.getPackageDir());

            variables.put("README", new Variable().withValue(service));
            variables.put("TSP_CONFIG", new Variable().withValue(tspConfig.getUrl()));
            if (!CoreUtils.isNullOrEmpty(configure.getVersion())) {
                variables.put("VERSION", new Variable().withValue(configure.getVersion()));
            }
            templateParameters.put("RELEASE_TYPE", "TypeSpec");
            // TODO(xiaofei) determine SDK name
            sdk = service;
        } else { // generate from Swagger
            String swagger = configure.getSwagger();
            // Generated PR title would contain swagger parameter.
            prKeyword = swagger;
            Spec spec = getSpec(swagger);
            service = spec.service;
            String suffix = spec.suffix;
            if (!spec.exists) {
                if (!CoreUtils.isNullOrEmpty(configure.getService())) {
                    service = configure.getService();
                }
                if (!CoreUtils.isNullOrEmpty(configure.getSuffix())) {
                    suffix = configure.getSuffix();
                }
            }
            sdk = service;
            if (!CoreUtils.isNullOrEmpty(suffix)) {
                sdk = service + "-" + suffix;
            }

            OUT.println("swagger: " + swagger);
            OUT.println("service: " + service);
            OUT.println("sdk: " + sdk);

            String tag = configure.getTag();
            if (CoreUtils.isNullOrEmpty(tag)) {
                ReadmeConfigure readmeConfigure = Utils.getReadmeConfigure(HTTP_PIPELINE, swagger);
                readmeConfigure.print(OUT, 3);

                tag = readmeConfigure.getDefaultTag();
                if (tag == null) {
                    tag = readmeConfigure.getTagConfigures().iterator().next().getTagName();
                }
                if (PREFER_STABLE_TAG) {
                    if (tag.endsWith("-preview")) {
                        Optional<String> stableTag = readmeConfigure.getTagConfigures().stream()
                            .map(ReadmeConfigure.TagConfigure::getTagName)
                            .filter(name -> !name.endsWith("-preview"))
                            .findFirst();
                        if (stableTag.isPresent()) {
                            tag = stableTag.get();
                        }
                    }
                }
                OUT.println("choose tag: " + tag + ". Override?");
                Scanner s = new Scanner(IN);
                String input = s.nextLine();
                if (!input.trim().isEmpty()) {
                    tag = input.trim();
                }
            }
            OUT.println("tag: " + tag);

            if (configure.isAutoVersioning() && !tag.contains("-preview")) {
                ReadmeConfigure readmeConfigure = Utils.getReadmeConfigure(HTTP_PIPELINE, swagger);
                final String tagToRelease = tag;
                Optional<ReadmeConfigure.TagConfigure> tagConfigure = readmeConfigure.getTagConfigures().stream()
                    .filter(t -> Objects.equals(tagToRelease, t.getTagName()))
                    .findFirst();
                boolean previewInputFileInTag = tagConfigure.isPresent()
                    && tagConfigure.get().getInputFiles().stream().anyMatch(f -> f.contains("/preview/"));

                if (!previewInputFileInTag) {
                    // if stable is released, and current tag is also stable
                    VersionConfigure.parseVersion(HTTP_PIPELINE, service).ifPresent(sdkVersion -> {
                        if (sdkVersion.isStableReleased()) {
                            configure.setAutoVersioning(false);
                            configure.setVersion(sdkVersion.getCurrentVersionAsStable());

                            OUT.println("release for stable: " + configure.getVersion());
                        }
                    });
                }
            }

            variables.put("README", new Variable().withValue(swagger));
            variables.put("TAG", new Variable().withValue(tag));
            if (!configure.isAutoVersioning()) {
                variables.put("VERSION", new Variable().withValue(configure.getVersion()));
            }
            if (!CoreUtils.isNullOrEmpty(configure.getService())) {
                variables.put("SERVICE", new Variable().withValue(configure.getService()));
            }
            if (!CoreUtils.isNullOrEmpty(configure.getSuffix())) {
                variables.put("SUFFIX", new Variable().withValue(configure.getSuffix()));
            }

            templateParameters.put("RELEASE_TYPE", "Swagger");
        }

        runLiteCodegen(manager, variables, templateParameters);
        OUT.println("wait 1 minutes");
        Thread.sleep(POLL_SHORT_INTERVAL_MINUTE * MILLISECOND_PER_MINUTE);

        mergeGithubPR(sdkRepository, manager, prKeyword, service);

        runLiteRelease(manager, service, sdk);

        mergeGithubVersionPR(sdkRepository, service, sdk);

        String sdkMavenUrl = MAVEN_ARTIFACT_PATH_PREFIX + "azure-resourcemanager-" + sdk + "/1.0.0-beta.1/versions";
        Utils.openUrl(sdkMavenUrl);

        System.exit(0);
    }

    private static void runLiteCodegen(DevManager manager, Map<String, Variable> variables, Map<String, String> templateParameters) throws InterruptedException {
        // run pipeline
        Run run = manager.runs().runPipeline(ORGANIZATION, PROJECT_INTERNAL, LITE_CODEGEN_PIPELINE_ID,
                new RunPipelineParameters().withVariables(variables).withTemplateParameters(templateParameters));
        int buildId = run.id();

        // wait for complete
        while (run.state() != RunState.COMPLETED && run.state() != RunState.CANCELING) {
            OUT.println("build id: " + buildId + ", state: " + run.state());

            OUT.println("wait 1 minutes");
            Thread.sleep(POLL_SHORT_INTERVAL_MINUTE * MILLISECOND_PER_MINUTE);

            run = manager.runs().get(ORGANIZATION, PROJECT_INTERNAL, LITE_CODEGEN_PIPELINE_ID, buildId);
        }
    }

    private static void mergeGithubPR(GHRepository repository, DevManager manager, String prKeyword, String service) throws InterruptedException, IOException {
        List<GHPullRequest> prs = repository.getPullRequests(GHIssueState.OPEN);

        GHPullRequest pr = prs.stream()
                .filter(p -> p.getTitle().startsWith("[Automation] Generate Fluent Lite from") && p.getTitle().contains(prKeyword))
                .findFirst().orElse(null);

        if (pr != null) {
            int prNumber = pr.getNumber();

            String prUrl = "https://github.com/Azure/azure-sdk-for-java/pull/" + prNumber + "/files";
            OUT.println("GitHub pull request: " + prUrl);
            Utils.openUrl(prUrl);

            // wait for CI
            waitForChecks(pr, manager, prNumber, service);

            if (PROMPT_CONFIRMATION) {
                Utils.promptMessageAndWait(IN, OUT,
                        "'Yes' to approve and merge GitHub pull request: https://github.com/Azure/azure-sdk-for-java/pull/" + prNumber);
            }

            // make PR ready
            Utils.prReady(HTTP_PIPELINE, GITHUB_TOKEN, prNumber);
            Thread.sleep(POLL_SHORT_INTERVAL_MINUTE * MILLISECOND_PER_MINUTE);

            // approve PR
            GHPullRequestReview review = pr.createReview().event(GHPullRequestReviewEvent.APPROVE).create();

            // merge PR
            pr.refresh();
            pr.merge("", pr.getHead().getSha(), GHPullRequest.MergeMethod.SQUASH);
            OUT.println("Pull request merged: " + prNumber);
        } else {
            throw new IllegalStateException("github pull request not found");
        }
    }

    private static void runLiteRelease(DevManager manager, String service, String sdk) throws InterruptedException {
        List<String> releaseTemplateParameters = getReleaseTemplateParameters(manager, GITHUB_ORGANIZATION, GITHUB_PROJECT, service);
        if (releaseTemplateParameters.isEmpty()) {
            LOGGER.warn("release parameters not found in ci.yml");
        }

        // find pipeline
        Pipeline pipeline = findSdkPipeline(manager, service, false);

        if (pipeline != null) {
            Map<String, Object> buildDefinition = Utils.getDefinition(manager, ORGANIZATION, PROJECT_INTERNAL, pipeline.id());
            if (!Utils.isDefinitionEnabled(buildDefinition)) {
                OUT.println("enable pipeline");
                Utils.enableDefinition(manager, ORGANIZATION, PROJECT_INTERNAL, pipeline.id(), buildDefinition);
            }

            Map<String, String> templateParameters = new HashMap<>();
            for (String releaseTemplateParameter : releaseTemplateParameters) {
                templateParameters.put(releaseTemplateParameter,
                        String.valueOf(releaseTemplateParameter.startsWith("release_azureresourcemanager") && releaseTemplateParameter.contains(sdk.replace("-", ""))));
            }

            // run pipeline
            Run run = manager.runs().runPipeline(ORGANIZATION, PROJECT_INTERNAL, pipeline.id(),
                    new RunPipelineParameters().withTemplateParameters(templateParameters));
            int buildId = run.id();

            String buildUrl = "https://dev.azure.com/azure-sdk/internal/_build/results?buildId=" + buildId;
            OUT.println("DevOps build: " + buildUrl);
            Utils.openUrl(buildUrl);

            OUT.println("wait 5 minutes");
            Thread.sleep(POLL_LONG_INTERVAL_MINUTE * MILLISECOND_PER_MINUTE);
            // poll until approval is available
            ReleaseState state = null;
            while (state == null || state.getApprovalIds().isEmpty()) {
                OUT.println("wait 5 minutes");
                Thread.sleep(POLL_LONG_INTERVAL_MINUTE * MILLISECOND_PER_MINUTE);

                Timeline timeline = manager.timelines().get(ORGANIZATION, PROJECT_INTERNAL, buildId, null);
                state = getReleaseState(timeline);
            }

            if (PROMPT_CONFIRMATION) {
                Utils.promptMessageAndWait(IN, OUT,
                        "'Yes' to approve release: " + state.getName());
            }

            // approve new release
            OUT.println("prepare to release: " + state.getName());
            Utils.approve(state.getApprovalIds(), manager, ORGANIZATION, PROJECT_INTERNAL);
            OUT.println("approved release: " + state.getName());

            // poll until release completion
            while (state.getState() != TimelineRecordState.COMPLETED) {
                OUT.println("wait 5 minutes");
                Thread.sleep(POLL_LONG_INTERVAL_MINUTE * MILLISECOND_PER_MINUTE);

                Timeline timeline = manager.timelines().get(ORGANIZATION, PROJECT_INTERNAL, buildId, null);
                state = getReleaseState(timeline);
            }
        } else {
            throw new IllegalStateException("release pipeline not found for service: " + service);
        }
    }

    private static void mergeGithubVersionPR(GHRepository repository, String service, String sdk) throws InterruptedException, IOException {
        List<GHPullRequest> prs = repository.getPullRequests(GHIssueState.OPEN);

        GHPullRequest pr = prs.stream()
                .filter(p -> p.getTitle().equals("Increment versions for " + sdk + " releases")
                        || p.getTitle().equals("Increment version for " + sdk + " releases"))
                .findFirst().orElse(null);

        if (pr != null) {
            int prNumber = pr.getNumber();

            // approve PR
            GHPullRequestReview review = pr.createReview().event(GHPullRequestReviewEvent.APPROVE).create();

            String prUrl = "https://github.com/Azure/azure-sdk-for-java/pull/" + prNumber;
            OUT.println("GitHub pull request: " + prUrl);
            Utils.openUrl(prUrl);

            // wait for check enforcer
            waitForCommitSuccess(pr, prNumber);

            pr.refresh();
            if (Boolean.TRUE.equals(pr.isMerged())) {
                OUT.println("Pull request auto merged: " + prNumber);
            } else {
                // merge PR
                pr.merge("", pr.getHead().getSha(), GHPullRequest.MergeMethod.SQUASH);
                OUT.println("Pull request merged: " + prNumber);
            }
        } else {
            throw new IllegalStateException("github pull request not found");
        }
    }

    private static ReleaseState getReleaseState(Timeline timeline) {
        List<ReleaseState> states = new ArrayList<>();
        for (TimelineRecord record : timeline.records()) {
            if ("Releasing: 1 libraries".equals(record.name()) && "stage1".equals(record.identifier())
                    || record.name().startsWith("Release: azure-resourcemanager-")) {
                states.add(Utils.getReleaseState(record, timeline));
            }
        }

        if (states.size() == 1) {
            ReleaseState state = states.iterator().next();
            OUT.println("release: " + state.getName() + ", state: " + state.getState());
            return state;
        } else {
            throw new IllegalStateException("release candidate not correct");
        }
    }

    private static final HttpPipeline HTTP_PIPELINE = new HttpPipelineBuilder().build();

    private static CheckRun getCheck(List<CheckRun> checkRuns, String name) {
        return checkRuns.stream()
                .filter(p -> p.getName().equals(name))
                .findAny().orElse(null);
    }

    private static void waitForChecks(GHPullRequest pr, DevManager manager,
                                      int prNumber, String sdk) throws InterruptedException, IOException {
        // wait a bit
        OUT.println("wait 1 minutes");
        Thread.sleep(POLL_SHORT_INTERVAL_MINUTE * MILLISECOND_PER_MINUTE);

        Pipeline pipeline = findSdkPipeline(manager, sdk, true);
        String javaSdkCheckName = pipeline != null ? pipeline.name() : ("java - " + sdk + " - ci");

        boolean ciPipelineReady = pipeline != null;
        boolean ciPipelineEnabled = true;

        if (ciPipelineReady) {
            Map<String, Object> buildDefinition = Utils.getDefinition(manager, ORGANIZATION, PROJECT_PUBLIC, pipeline.id());
            if (!Utils.isDefinitionEnabled(buildDefinition)) {
                ciPipelineEnabled = false;
                OUT.println("enable pipeline");
                Utils.enableDefinition(manager, ORGANIZATION, PROJECT_PUBLIC, pipeline.id(), buildDefinition);
            }
        }

        if (!ciPipelineReady) {
            LOGGER.info("prepare pipeline");

            // comment to create sdk CI
            pr.comment("/azp run prepare-pipelines");

            // wait for prepare pipelines
            pr.refresh();
            waitForCheckSuccess(pr, prNumber, CI_PREPARE_PIPELINES_NAME, pr.getHead().getSha());

            // comment to run the newly created sdk CI
            pr.comment("/azp run " + javaSdkCheckName);
        } else {
            // comment to run the previously disabled sdk CI
            if (!ciPipelineEnabled) {
                pr.comment("/azp run " + javaSdkCheckName);
                // wait a bit before potentially another "/azp run" comment
                OUT.println("wait 1 minutes");
                Thread.sleep(POLL_SHORT_INTERVAL_MINUTE * MILLISECOND_PER_MINUTE);
            }

            // trigger live tests, if available
            String testPipelineName = "java - " + sdk + " - mgmt - tests";
            boolean testPipelineAvailable = manager.pipelines().list(ORGANIZATION, PROJECT_INTERNAL).stream()
                    .anyMatch(p -> p.name().equals(testPipelineName));
            if (testPipelineAvailable) {
                // comment to trigger tests.mgmt
                pr.comment("/azp run " + testPipelineName);
            }
        }

        // wait for sdk CI
        waitForCheckSuccess(pr, prNumber, javaSdkCheckName);

        // wait for check enforcer
        waitForCommitSuccess(pr, prNumber);
    }

    private static CommitStatus getCommitStatusForCheckEnforcer(String sha) {
        return Arrays.stream(Utils.getCommitStatuses(HTTP_PIPELINE, GITHUB_TOKEN, sha))
                .filter(s -> CI_CHECK_ENFORCER_NAME.equals(s.getContext()))
                .findFirst().orElse(null);
    }

    private static void waitForCommitSuccess(GHPullRequest pr, int prNumber) throws InterruptedException, IOException {
        String commitSHA = pr.getHead().getSha();

        CommitStatus status = getCommitStatusForCheckEnforcer(commitSHA);
        while (status == null || !"success".equals(status.getState())) {
            if (status == null) {
                OUT.println("pr number: " + prNumber + ", wait for " + CI_CHECK_ENFORCER_NAME);
            } else {
                OUT.println("pr number: " + prNumber + ", " + CI_CHECK_ENFORCER_NAME + " state: " + status.getState());
            }

            OUT.println("wait 1 minutes");
            Thread.sleep(POLL_SHORT_INTERVAL_MINUTE * MILLISECOND_PER_MINUTE);

            // refresh head commit
            pr.refresh();
            commitSHA = pr.getHead().getSha();
            status = getCommitStatusForCheckEnforcer(commitSHA);
        }
    }

    private static void waitForCheckSuccess(GHPullRequest pr,
                                            int prNumber, String checkName) throws InterruptedException, IOException {
        waitForCheckSuccess(pr, prNumber, checkName, null);
    }

    private static void waitForCheckSuccess(GHPullRequest pr,
                                            int prNumber, String checkName, String fixedCommitSHA) throws InterruptedException, IOException {
        final String pullRequestCheckName = "java - pullrequest";

        String commitSHA = fixedCommitSHA;
        if (commitSHA == null) {
            // refresh head commit
            pr.refresh();
            commitSHA = pr.getHead().getSha();
        }
        CheckRunListResult checkRunResult = Utils.getCheckRuns(HTTP_PIPELINE, GITHUB_TOKEN, commitSHA);
        CheckRun check = getCheck(checkRunResult.getCheckRuns(), checkName);
        CheckRun pullRequestCheck = getCheck(checkRunResult.getCheckRuns(), pullRequestCheckName);
        while (check == null || !"success".equals(check.getConclusion()) || pullRequestCheck == null || !"success".equals(pullRequestCheck.getConclusion())) {
            if (check == null) {
                OUT.println("pr number: " + prNumber + ", wait for " + checkName);
            } else {
                OUT.println("pr number: " + prNumber + ", " + checkName + " status: " + check.getStatus() + ", conclusion: " + check.getConclusion());
            }

            OUT.println("wait 1 minutes");
            Thread.sleep(POLL_SHORT_INTERVAL_MINUTE * MILLISECOND_PER_MINUTE);

            if (fixedCommitSHA == null) {
                // refresh head commit
                pr.refresh();
                commitSHA = pr.getHead().getSha();
            }
            checkRunResult = Utils.getCheckRuns(HTTP_PIPELINE, GITHUB_TOKEN, commitSHA);
            check = getCheck(checkRunResult.getCheckRuns(), checkName);
            pullRequestCheck = getCheck(checkRunResult.getCheckRuns(), pullRequestCheckName);
        }
    }

    private static Spec getSpec(String swaggerName) {
        Matcher matcher = Pattern.compile("specification/([^/]+)/resource-manager(/.*)*/readme.md")
                .matcher(swaggerName);
        if (matcher.matches()) {
            swaggerName = matcher.group(1);
            String subspec = matcher.group(2);
            if (!CoreUtils.isNullOrEmpty(subspec)) {
                swaggerName += subspec;
            }
        }

        HttpRequest request = new HttpRequest(HttpMethod.GET, API_SPECS_YAML_PATH);
        HttpResponse response = HTTP_PIPELINE.send(request).block();
        if (response.getStatusCode() == 200) {
            String configYaml = response.getBodyAsString().block();
            response.close();

            Yaml yaml = new Yaml();
            Map<String, Object> config = yaml.load(configYaml);

            String serviceName = swaggerName;
            String suffix = null;
            boolean exists = false;
            if (config.containsKey(swaggerName)) {
                exists = true;
                Map<String, String> detail = (Map<String, String>) config.get(swaggerName);
                if (detail.containsKey("service")) {
                    serviceName = detail.get("service");
                }
                if (detail.containsKey("suffix")) {
                    suffix = detail.get("suffix");
                }
            }

            serviceName = serviceName.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9_]", "");

            return new Spec(serviceName, suffix, exists);
        } else {
            response.close();
            throw new HttpResponseException(response);
        }
    }

    private static Configure getConfigure() throws IOException {
        Configure configure = null;
        Yaml yaml = new Yaml();
        Path configInPath = Paths.get("configure.yml");
        if (Files.exists(configInPath)) {
            try (InputStream in = new FileInputStream(configInPath.toFile())) {
                configure = yaml.loadAs(in, Configure.class);
            }
        }
        if (configure == null) {
            try (InputStream in = LiteRelease.class.getResourceAsStream("/configure.yml")) {
                configure = yaml.loadAs(in, Configure.class);
            }
        }
        return configure;
    }


    private static List<String> getReleaseTemplateParameters(DevManager manager,
                                                             String organization, String project, String sdk) {
        List<String> releaseTemplateParameters;

        String ciUrl = String.format("https://raw.githubusercontent.com/%1$s/%2$s/main/sdk/%3$s/azure-resourcemanager-%3$s/ci.yml",
                organization, project, sdk);
        releaseTemplateParameters = getReleaseTemplateParameters(manager, ciUrl);
        if (releaseTemplateParameters == null) {
            ciUrl = String.format("https://raw.githubusercontent.com/%1$s/%2$s/main/sdk/%3$s/ci.yml",
                    organization, project, sdk);
            releaseTemplateParameters = getReleaseTemplateParameters(manager, ciUrl);
        }

        if (releaseTemplateParameters == null) {
            throw new IllegalStateException("failed to get ci.yml: " + ciUrl);
        }

        return releaseTemplateParameters;
    }

    private static List<String> getReleaseTemplateParameters(DevManager manager, String url) {
        List<String> releaseTemplateParameters = new ArrayList<>();

        String ciUrl = String.format(url);

        HttpRequest request = new HttpRequest(HttpMethod.GET, ciUrl);
        HttpResponse response = HTTP_PIPELINE.send(request).block();
        System.out.println("response status code: " + response.getStatusCode());
        if (response.getStatusCode() != 200) {
            System.out.println("response body: " + response.getBodyAsString().block());
            response.close();
            return null;
        } else {
            String ciYml = response.getBodyAsString().block();
            Yaml yaml = new Yaml();
            Map<String, Object> ci = yaml.load(ciYml);
            if (ci.containsKey("parameters") && ci.get("parameters") instanceof List) {
                List<Object> parameters = (List<Object>) ci.get("parameters");
                for (Object parameterObj : parameters) {
                    if (parameterObj instanceof Map) {
                        Map<String, Object> parameter = (Map<String, Object>) parameterObj;
                        if (parameter.containsKey("name") && parameter.get("name") instanceof String) {
                            String parameterName = (String) parameter.get("name");
                            if (parameterName.startsWith("release_")) {
                                releaseTemplateParameters.add(parameterName);
                            }
                        }
                    }
                }
            }
            response.close();
        }

        return releaseTemplateParameters;
    }

    private static Pipeline findSdkPipeline(DevManager manager, String sdk, boolean publicPipeline) {
        List<Pipeline> pipelines = manager.pipelines().list(ORGANIZATION, publicPipeline ? PROJECT_PUBLIC : PROJECT_INTERNAL)
                .stream().collect(Collectors.toList());

        // find pipeline
        String pipelineName = "java - azure-resourcemanager-" + sdk + (publicPipeline ? " - ci" : "");
        final String pipelineName1 = pipelineName;
        Pipeline pipeline = pipelines.stream()
                .filter(p -> pipelineName1.equals(p.name())).findFirst().orElse(null);

        if (pipeline == null) {
            pipelineName = "java - " + sdk + (publicPipeline ? " - ci" : "");
            final String pipelineName2 = pipelineName;
            pipeline = pipelines.stream()
                    .filter(p -> pipelineName2.equals(p.name())).findFirst().orElse(null);
        }

        return pipeline;
    }

    private static final class Spec {
        private String service;
        private String suffix;
        private boolean exists;

        Spec(String service, String suffix, boolean exists) {
            this.service = service;
            this.suffix = suffix;
            this.exists = exists;
        }
    }
}
