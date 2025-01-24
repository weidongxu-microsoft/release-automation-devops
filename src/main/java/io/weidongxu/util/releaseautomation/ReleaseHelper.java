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
import com.azure.dev.models.RepositoryResourceParameters;
import com.azure.dev.models.Run;
import com.azure.dev.models.RunPipelineParameters;
import com.azure.dev.models.RunResourcesParameters;
import com.azure.dev.models.RunState;
import com.azure.dev.models.Timeline;
import com.azure.dev.models.TimelineRecord;
import com.azure.dev.models.TimelineRecordState;
import com.azure.dev.models.Variable;
import com.azure.identity.AzureCliCredentialBuilder;
import io.weidongxu.util.releaseautomation.store.LiteReleaseState;
import io.weidongxu.util.releaseautomation.store.NoOpTaskStore;
import io.weidongxu.util.releaseautomation.store.ReleaseTask;
import io.weidongxu.util.releaseautomation.store.TaskStore;
import org.kohsuke.github.GHIssueState;
import org.kohsuke.github.GHPullRequest;
import org.kohsuke.github.GHPullRequestReview;
import org.kohsuke.github.GHPullRequestReviewEvent;
import org.kohsuke.github.GHPullRequestReviewState;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
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

public class ReleaseHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReleaseHelper.class);
    private static final String USER = Configuration.getGlobalConfiguration().get("DEVOPS_USER");
    private static final String SPECS_PROJECT = "azure-rest-api-specs";
    private static final String PASS = Configuration.getGlobalConfiguration().get("DEVOPS_PAT");
    private static final String GITHUB_TOKEN = Configuration.getGlobalConfiguration().get("GITHUB_PAT");
    private static final String ORGANIZATION = "azure-sdk";
    private static final String PROJECT_INTERNAL = "internal";
    private static final String PROJECT_PUBLIC = "public";

    private static final String GITHUB_ORGANIZATION = "Azure";
    private static final String GITHUB_PROJECT = "azure-sdk-for-java";

    private static final int LITE_CODEGEN_PIPELINE_ID = 2238;

    private static final String CI_CHECK_ENFORCER_NAME = "https://aka.ms/azsdk/checkenforcer";
    private static final String CI_PREPARE_PIPELINES_NAME = "prepare-pipelines";

    private static final String API_SPECS_YAML_PATH = "https://raw.githubusercontent.com/Azure/azure-sdk-for-java/main/eng/automation/api-specs.yaml";

    private static final String MAVEN_ARTIFACT_PATH_PREFIX = "https://central.sonatype.com/artifact/com.azure.resourcemanager/";

    private static final InputStream IN = System.in;
    private static final PrintStream OUT = System.out;

    private static final boolean PREFER_STABLE_TAG = false;

    private static final long POLL_SHORT_INTERVAL_MINUTE = 1;
    private static final long POLL_LONG_INTERVAL_MINUTE = 5;
    private static final long MILLISECOND_PER_MINUTE = 60 * 1000;
    private final GHRepository sdkRepository;
    private final GHRepository specsRepository;
    private final DevManager manager;
    private final GitHub github;

    private boolean tagConfirmation;
    private boolean mergePrConfirmation;
    private boolean releaseConfirmation;
    private TaskStore taskStore = new NoOpTaskStore();

    public void doRelease(Configure configure) throws Exception {
        String tspConfigUrl = configure.getTspConfig();
        String sdk;
        String prTitle = "[Automation] Generate Fluent Lite from ";
        Map<String, Variable> variables = new HashMap<>();
        Map<String, String> templateParameters = new HashMap<>();

        ReleaseTask task = taskStore.create(new ReleaseTask(configure));
        OUT.println("sdk-repo-branch: " + task.getSdkRepoBranch());

        try {
            if (!CoreUtils.isNullOrEmpty(tspConfigUrl)) { // generate from TypeSpec
                TspConfig tspConfig = null;
                tspConfig = TspConfig.parse(specsRepository, HTTP_PIPELINE, tspConfigUrl);
                sdk = tspConfig.getService();
                if (CoreUtils.isNullOrEmpty(sdk)) {
                    throw new ReleaseException(LiteReleaseState.VERIFICATION_FAILED, "\"service\" must not be null if Generated from TypeSpec.");
                }
                prTitle = String.format("%sTypeSpec %s", prTitle, sdk);
                OUT.println("Releasing from TypeSpec, tsp-config file with commitID: \n" + tspConfig.getUrl());
                OUT.println("sdk: " + sdk);
                OUT.println("package-dir: " + tspConfig.getPackageDir());

                variables.put("README", new Variable().withValue(sdk));
                variables.put("TSP_CONFIG", new Variable().withValue(tspConfig.getUrl()));
                if (!CoreUtils.isNullOrEmpty(configure.getVersion())) {
                    variables.put("VERSION", new Variable().withValue(configure.getVersion()));
                }
                templateParameters.put("RELEASE_TYPE", "TypeSpec");
            } else { // generate from Swagger
                String swagger = configure.getSwagger();
                sdk = getSdkName(swagger);
                if (!CoreUtils.isNullOrEmpty(configure.getService())) {
                    sdk = configure.getService();
                }

                OUT.println("swagger: " + swagger);
                OUT.println("sdk: " + sdk);

                String tag = configure.getTag();
                try {
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
                        if (tagConfirmation) {
                            OUT.println("choose tag: " + tag + ". Override?");
                            Scanner s = new Scanner(IN);
                            String input = s.nextLine();
                            if (!input.trim().isEmpty()) {
                                tag = input.trim();
                            }
                        }
                    }
                    OUT.println("tag: " + tag);
                    prTitle = String.format("%sSwagger %s#%s", prTitle, swagger, tag);

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
                            VersionConfigure.parseVersion(HTTP_PIPELINE, sdk).ifPresent(sdkVersion -> {
                                if (sdkVersion.isStableReleased()) {
                                    configure.setAutoVersioning(false);
                                    configure.setVersion(sdkVersion.getCurrentVersionAsStable());

                                    OUT.println("release for stable: " + configure.getVersion());
                                }
                            });
                        }
                    }
                } catch (MalformedURLException e) {
                    throw new ReleaseException(LiteReleaseState.VERIFICATION_FAILED, e);
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
                if (configure.getTests() == Boolean.TRUE) {
                    variables.put("AUTOREST_OPTIONS", new Variable().withValue("--generate-tests"));
                }

                templateParameters.put("RELEASE_TYPE", "Swagger");
            }

            runLiteCodegen(manager, variables, templateParameters, task);
            OUT.println("wait 1 minutes");
            Thread.sleep(POLL_SHORT_INTERVAL_MINUTE * MILLISECOND_PER_MINUTE);

            mergeGithubPR(sdkRepository, manager, prTitle, sdk, task);

            runLiteRelease(manager, sdk, task);

            mergeGithubVersionPR(sdkRepository, sdk, task);

            String sdkMavenUrl = MAVEN_ARTIFACT_PATH_PREFIX + "azure-resourcemanager-" + sdk + "/1.0.0-beta.1/versions";
            task.setTrackUrl(sdkMavenUrl);
            if (releaseConfirmation) {
                Utils.openUrl(sdkMavenUrl);
            }
        } catch (ReleaseException e) {
            LOGGER.error("release exception", e);
            task.setState(e.getState());
            task.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            LOGGER.error("lite release failed", e);
            task.setState(LiteReleaseState.UNKNOWN_EXCEPTION);
            task.setErrorMessage(e.getMessage());
        } finally {
            taskStore.update(task);
        }
    }


    private void mergeGithubPR(GHRepository repository, DevManager manager, String prTitle, String sdk, ReleaseTask task) throws InterruptedException, IOException {
        List<GHPullRequest> prs = repository.getPullRequests(GHIssueState.OPEN);

        GHPullRequest pr = prs.stream()
                .filter(p -> p.getTitle().equals(prTitle))
                .findFirst().orElse(null);

        if (pr != null) {
            int prNumber = pr.getNumber();

            String prUrl = "https://github.com/Azure/azure-sdk-for-java/pull/" + prNumber + "/files";
            OUT.println("GitHub pull request: " + prUrl);
            if (mergePrConfirmation) {
                Utils.openUrl(prUrl);
            }

            // wait for CI
            task.setState(LiteReleaseState.CODE_GEN_PR_DRAFT);
            task.setTrackUrl(prUrl);
            taskStore.update(task);

            waitForChecks(pr, manager, prNumber, sdk);

            task.setState(LiteReleaseState.CODE_GEN_PR_CI_SUCCEEDED);
            taskStore.update(task);

            if (mergePrConfirmation) {
                Utils.promptMessageAndWait(IN, OUT,
                        "'Yes' to approve and merge GitHub pull request: https://github.com/Azure/azure-sdk-for-java/pull/" + prNumber);

                // make PR ready
                Utils.prReady(HTTP_PIPELINE, GITHUB_TOKEN, prNumber);
                Thread.sleep(POLL_SHORT_INTERVAL_MINUTE * MILLISECOND_PER_MINUTE);
                task.setState(LiteReleaseState.CODE_GEN_PR_READY);
                taskStore.update(task);

                // approve PR
                GHPullRequestReview review = pr.createReview().event(GHPullRequestReviewEvent.APPROVE).create();
                task.setState(LiteReleaseState.CODE_GEN_PR_APPROVED);
                taskStore.update(task);
            } else {
                waitForSelfApproval(pr, task);
                if (!pr.isMerged()) {
                    task.setState(LiteReleaseState.CODE_GEN_PR_APPROVED);
                    taskStore.update(task);

                    // make PR ready
                    Utils.prReady(HTTP_PIPELINE, GITHUB_TOKEN, prNumber);
                    Thread.sleep(POLL_SHORT_INTERVAL_MINUTE * MILLISECOND_PER_MINUTE);
                    task.setState(LiteReleaseState.CODE_GEN_PR_READY);
                    taskStore.update(task);
                }
            }

            mergePR(pr, LiteReleaseState.CODE_GEN_PR_MERGE_FAILED);
            task.setState(LiteReleaseState.CODE_GEN_PR_MERGED);
            taskStore.update(task);
        } else {
            throw new ReleaseException(LiteReleaseState.CODE_GEN_FAILED, "github pull request not found");
        }
    }

    private void mergePR(GHPullRequest pr, LiteReleaseState failState) throws IOException {
        pr.refresh();
        boolean prMerged = pr.isMerged();
        if (!prMerged) {
            try {
                // merge PR
                // synchronize to sync base branch change for the PR, since multiple PRs may experience merging at the same time
                while (!prMerged) {
                    try {
                        synchronized (this) {
                            pr = sdkRepository.getPullRequest(pr.getNumber());
                            pr.merge("", pr.getHead().getSha(), GHPullRequest.MergeMethod.SQUASH);
                        }
                        prMerged = true;
                    } catch (Exception e) {
                        if (e.getMessage() != null && e.getMessage().contains("\"405\"")) {
                            System.out.printf("PR[%d] merge 405\n", pr.getNumber());
                            Thread.sleep(1000);
                        } else {
                            throw e;
                        }
                    }

                }
            } catch (Exception e) {
                throw new ReleaseException(failState, e);
            }
            OUT.println("Pull request merged: " + pr.getNumber());
        }
    }

    private void waitForSelfApproval(GHPullRequest pr, ReleaseTask task) throws IOException, InterruptedException {
        GHUser self = github.getMyself();
        while (true) {
            try {
                pr.refresh();
                if (pr.listReviews().toList().stream().anyMatch(review ->
                {
                    try {
                        return review.getState() == GHPullRequestReviewState.APPROVED
                                && self.equals(review.getUser());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })) {
                    break;
                } else if (pr.getState() == GHIssueState.CLOSED && !pr.isMerged()) {
                    task.setState(LiteReleaseState.CODE_GEN_PR_CLOSED);
                    taskStore.update(task);
                    throw new IllegalStateException(String.format("PR[%d] is closed, cancel release", pr.getNumber()));
                } else if (pr.isMerged()) {
                    task.setState(LiteReleaseState.CODE_GEN_PR_MERGED);
                    taskStore.update(task);
                    break;
                } else {
                    LOGGER.info("PR[{}] waiting for self approval", pr.getNumber());
                }
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
            Thread.sleep(POLL_SHORT_INTERVAL_MINUTE * MILLISECOND_PER_MINUTE);
        }
    }

    private void runLiteRelease(DevManager manager, String sdk, ReleaseTask task) throws InterruptedException {
        List<String> releaseTemplateParameters = getReleaseTemplateParameters(manager, GITHUB_ORGANIZATION, GITHUB_PROJECT, sdk);
        if (releaseTemplateParameters.isEmpty()) {
            LOGGER.warn("release parameters not found in ci.yml");
        }

        // find pipeline
        Pipeline pipeline = findSdkPipeline(manager, sdk, false);

        if (pipeline != null) {
            Map<String, Object> buildDefinition = Utils.getDefinition(manager, ORGANIZATION, PROJECT_INTERNAL, pipeline.id());
            if (!Utils.isDefinitionEnabled(buildDefinition)) {
                OUT.println("enable pipeline");
                Utils.enableDefinition(manager, ORGANIZATION, PROJECT_INTERNAL, pipeline.id(), buildDefinition);
            }

            Map<String, String> templateParameters = new HashMap<>();
            for (String releaseTemplateParameter : releaseTemplateParameters) {
                templateParameters.put(releaseTemplateParameter,
                        String.valueOf(releaseTemplateParameter.startsWith("release_azureresourcemanager")));
            }

            // run pipeline
            Run run = manager.runs().runPipeline(ORGANIZATION, PROJECT_INTERNAL, pipeline.id(),
                    new RunPipelineParameters().withTemplateParameters(templateParameters));
            int buildId = run.id();

            String buildUrl = getBuildUrl(buildId);
            OUT.println("DevOps build: " + buildUrl);
            Utils.openUrl(buildUrl);

            task.setState(LiteReleaseState.RELEASE_CI);
            task.setTrackUrl(buildUrl);
            taskStore.update(task);

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

            if (releaseConfirmation) {
                Utils.promptMessageAndWait(IN, OUT,
                        "'Yes' to approve release: " + state.getName());
            }

            // approve new release
            OUT.println("prepare to release: " + state.getName());
            Utils.approve(state.getApprovalIds(), manager, ORGANIZATION, PROJECT_INTERNAL);
            OUT.println("approved release: " + state.getName());

            task.setState(LiteReleaseState.RELEASE_APPROVED);
            taskStore.update(task);

            // poll until release completion
            while (state.getState() != TimelineRecordState.COMPLETED) {
                OUT.println("wait 5 minutes");
                Thread.sleep(POLL_LONG_INTERVAL_MINUTE * MILLISECOND_PER_MINUTE);

                Timeline timeline = manager.timelines().get(ORGANIZATION, PROJECT_INTERNAL, buildId, null);
                state = getReleaseState(timeline);
            }

            task.setState(LiteReleaseState.RELEASE_SUCCEEDED);
            taskStore.update(task);

        } else {
            throw new ReleaseException(LiteReleaseState.RELEASE_CI_NOT_FOUND, "release ci not found");
        }
    }

    private static String getBuildUrl(int buildId) {
        return "https://dev.azure.com/azure-sdk/internal/_build/results?buildId=" + buildId;
    }

    private void mergeGithubVersionPR(GHRepository repository, String sdk, ReleaseTask task) throws InterruptedException, IOException {
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
            if (releaseConfirmation) {
                Utils.openUrl(prUrl);
            }

            task.setState(LiteReleaseState.VERSION_PR_APPROVED);
            task.setTrackUrl(prUrl);
            taskStore.update(task);

            // wait for check enforcer
            waitForCommitSuccess(pr, prNumber);

            pr.refresh();
            if (Boolean.TRUE.equals(pr.isMerged())) {
                OUT.println("Pull request auto merged: " + prNumber);
            } else {
                mergePR(pr, LiteReleaseState.VERSION_PR_MERGE_FAILED);
            }

            task.setState(LiteReleaseState.SUCCEEDED);
            taskStore.update(task);
        } else {
            throw new ReleaseException(LiteReleaseState.VERSION_PR_NOT_FOUND);
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
            throw new ReleaseException(LiteReleaseState.MULTIPLE_RELEASE_ARTIFACTS);
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
        try {
            return Arrays.stream(Utils.getCommitStatuses(HTTP_PIPELINE, GITHUB_TOKEN, sha))
                    .filter(s -> CI_CHECK_ENFORCER_NAME.equals(s.getContext()))
                    .findFirst().orElse(null);
        } catch (HttpResponseException e) {
            return new CommitStatus();
        }
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
        String commitSHA = fixedCommitSHA;
        if (commitSHA == null) {
            // refresh head commit
            pr.refresh();
            commitSHA = pr.getHead().getSha();
        }
        CheckRunListResult checkRunResult = Utils.getCheckRuns(HTTP_PIPELINE, GITHUB_TOKEN, commitSHA);
        CheckRun check = getCheck(checkRunResult.getCheckRuns(), checkName);
        while (check == null || !"success".equals(check.getConclusion())) {
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
        }
    }

    private static String getSdkName(String swaggerName) {
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

            String sdkName = swaggerName;
            if (config.containsKey(swaggerName)) {
                Map<String, String> detail = (Map<String, String>) config.get(swaggerName);
                if (detail.containsKey("service")) {
                    sdkName = detail.get("service");
                }
            }

            sdkName = sdkName.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9_]", "");

            return sdkName;
        } else {
            response.close();
            throw new HttpResponseException(response);
        }
    }

    private void runLiteCodegen(DevManager manager, Map<String, Variable> variables, Map<String, String> templateParameters, ReleaseTask task) throws InterruptedException {
        // run pipeline
        Run run;
        try {
            run = manager.runs().runPipeline(ORGANIZATION, PROJECT_INTERNAL, LITE_CODEGEN_PIPELINE_ID,
                    new RunPipelineParameters().withVariables(variables).withTemplateParameters(templateParameters)
                            .withResources(new RunResourcesParameters().withRepositories(Map.of("self", new RepositoryResourceParameters().withRefName(String.format("refs/heads/%s", task.getSdkRepoBranch()))))));
        } catch (Exception e) {
            throw new ReleaseException(LiteReleaseState.CODE_GEN_FAILED, e);
        }
        int buildId = run.id();

        task.setState(LiteReleaseState.CODE_GEN_PIPELINE);
        task.setTrackUrl(getBuildUrl(buildId));
        taskStore.update(task);

        // wait for complete
        while (run.state() != RunState.COMPLETED && run.state() != RunState.CANCELING) {
            OUT.println("build id: " + buildId + ", state: " + run.state());

            try {
                run = manager.runs().get(ORGANIZATION, PROJECT_INTERNAL, LITE_CODEGEN_PIPELINE_ID, buildId);
            } catch (Exception e) {
                LOGGER.warn("error getting codegen run state, retrying...");
                Thread.sleep(POLL_SHORT_INTERVAL_MINUTE * MILLISECOND_PER_MINUTE);
                continue;
            }

            OUT.println("wait 1 minutes");
            Thread.sleep(POLL_SHORT_INTERVAL_MINUTE * MILLISECOND_PER_MINUTE);
        }

        if (run.state() != RunState.COMPLETED) {
            throw new ReleaseException(LiteReleaseState.CODE_GEN_FAILED, "lite gen failed for run: " + run.url());
        }

        task.setState(LiteReleaseState.CODE_GEN_SUCCEEDED);
        taskStore.update(task);
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

    public static class Builder {
        private final ReleaseHelper releaseHelper = new ReleaseHelper();

        public Builder() throws IOException {
        }

        public Builder withTagSelectionConfirmation() {
            releaseHelper.tagConfirmation = true;
            return this;
        }

        public Builder withPrMergeConfirmation() {
            releaseHelper.mergePrConfirmation = true;
            return this;
        }

        public Builder withReleaseConfirmation() {
            releaseHelper.releaseConfirmation = true;
            return this;
        }

        public Builder withTaskStore(TaskStore taskStore) {
            Objects.requireNonNull(taskStore);
            releaseHelper.taskStore = taskStore;
            return this;
        }

        public ReleaseHelper build() {
            return releaseHelper;
        }
    }

    private ReleaseHelper() throws IOException {
        TokenCredential tokenCredential;
        if (CoreUtils.isNullOrEmpty(PASS)) {
            LOGGER.info("No AzureDevOps PAT provided, using Azure DevOps CLI authentication.");
            tokenCredential = new AzureCliCredentialBuilder().build();
        } else {
            LOGGER.info("AzureDevOps PAT found, using Basic authentication.");
            tokenCredential = new BasicAuthenticationCredential(USER, PASS);
        }
        manager = DevManager.configure()
                .withLogOptions(new HttpLogOptions().setLogLevel(HttpLogDetailLevel.NONE))
                .authenticate(
                        tokenCredential,
                        new AzureProfile(AzureEnvironment.AZURE));

        github = new GitHubBuilder()
                .withOAuthToken(GITHUB_TOKEN).build();

        sdkRepository = github.getRepository(GITHUB_ORGANIZATION + "/" + GITHUB_PROJECT);
        specsRepository = github.getRepository(GITHUB_ORGANIZATION + "/" + SPECS_PROJECT);
    }
}
