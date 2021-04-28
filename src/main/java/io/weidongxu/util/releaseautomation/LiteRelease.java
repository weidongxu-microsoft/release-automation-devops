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
import com.azure.core.util.serializer.JacksonAdapter;
import com.azure.core.util.serializer.SerializerEncoding;
import com.azure.dev.DevManager;
import com.azure.dev.models.Pipeline;
import com.azure.dev.models.Run;
import com.azure.dev.models.RunPipelineParameters;
import com.azure.dev.models.RunState;
import com.azure.dev.models.Timeline;
import com.azure.dev.models.TimelineRecord;
import com.azure.dev.models.TimelineRecordState;
import com.azure.dev.models.Variable;
import com.spotify.github.v3.clients.GitHubClient;
import com.spotify.github.v3.clients.IssueClient;
import com.spotify.github.v3.clients.PullRequestClient;
import com.spotify.github.v3.clients.RepositoryClient;
import com.spotify.github.v3.comment.Comment;
import com.spotify.github.v3.prs.ImmutableMergeParameters;
import com.spotify.github.v3.prs.ImmutableReviewParameters;
import com.spotify.github.v3.prs.MergeMethod;
import com.spotify.github.v3.prs.PullRequest;
import com.spotify.github.v3.prs.PullRequestItem;
import com.spotify.github.v3.prs.Review;
import com.spotify.github.v3.prs.requests.ImmutablePullRequestParameters;
import com.spotify.github.v3.prs.requests.PullRequestParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class LiteRelease {

    private static final Logger LOGGER = LoggerFactory.getLogger(LiteRelease.class);

    private static final String USER = Configuration.getGlobalConfiguration().get("DEVOPS_USER");
    private static final String PASS = Configuration.getGlobalConfiguration().get("DEVOPS_PAT");
    private static final String ORGANIZATION = "azure-sdk";
    private static final String PROJECT = "internal";

    private static final String GITHUB_TOKEN = Configuration.getGlobalConfiguration().get("GITHUB_PAT");
    private static final String GITHUB_ORGANIZATION = "Azure";
    private static final String GITHUB_PROJECT = "azure-sdk-for-java";

    private static final int LITE_CODEGEN_PIPELINE_ID = 2238;

    private static final String CI_CHECK_ENFORCER_NAME = "check-enforcer";
    private static final String CI_PREPARE_PIPELINES_NAME = "prepare-pipelines";

    private static final String API_SPECS_YAML_PATH = "https://raw.githubusercontent.com/Azure/azure-sdk-for-java/master/eng/mgmt/automation/api-specs.yaml";

    private static final String SPEC_README_PATH_PREFIX = "https://raw.githubusercontent.com/Azure/azure-rest-api-specs/master/specification/";

    private static final String MAVEN_ARTIFACT_PATH_PREFIX = "https://search.maven.org/artifact/com.azure.resourcemanager/";

    private static final InputStream IN = System.in;
    private static final PrintStream OUT = System.out;

    private static final boolean PROMPT_CONFIRMATION = true;

    private static final PullRequestParameters PR_LIST_PARAMS = ImmutablePullRequestParameters.builder()
            .state("open").page(1).per_page(10).build();
    private static final long POLL_SHORT_INTERVAL_MINUTE = 1;
    private static final long POLL_LONG_INTERVAL_MINUTE = 5;
    private static final long MILLISECOND_PER_MINUTE = 60 * 1000;

    public static void main(String[] args) throws Exception {
        TokenCredential tokenCredential = new BasicAuthenticationCredential(USER, PASS);

        Configure configure = getConfigure();
        String swagger = configure.getSwagger();
        boolean isPreview = configure.isPreview();
        String sdk = getSdkName(swagger);

        OUT.println("swagger: " + swagger);
        OUT.println("sdk: " + sdk);

        String tag = configure.getTag();
        if (CoreUtils.isNullOrEmpty(tag)) {
            ReadmeConfigure readmeConfigure = ReadmeConfigure.parseReadme(HTTP_PIPELINE,
                    new URL(SPEC_README_PATH_PREFIX + swagger + "/resource-manager/readme.md"));
            readmeConfigure.print(OUT, 3);

            tag = readmeConfigure.getDefaultTag();
            if (tag == null) {
                tag = readmeConfigure.getTagConfigures().iterator().next().getTagName();
            }
            if (tag.endsWith("-preview")) {
                Optional<String> stableTag = readmeConfigure.getTagConfigures().stream()
                        .map(ReadmeConfigure.TagConfigure::getTagName)
                        .filter(name -> !name.endsWith("-preview"))
                        .findFirst();
                if (stableTag.isPresent()) {
                    tag = stableTag.get();
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

        DevManager manager = DevManager.configure()
                .withLogOptions(new HttpLogOptions().setLogLevel(HttpLogDetailLevel.BODY_AND_HEADERS))
                .withPolicy(new BasicAuthAuthenticationPolicy(tokenCredential))
                .authenticate(
                        new BasicAuthenticationCredential(USER, PASS),
                        new AzureProfile(AzureEnvironment.AZURE));

        GitHubClient github = GitHubClient.create(new URI("https://api.github.com/"), GITHUB_TOKEN);
        RepositoryClient client = github.createRepositoryClient(GITHUB_ORGANIZATION, GITHUB_PROJECT);

        Map<String, Variable> variables = new HashMap<>();
        variables.put("README", new Variable().withValue(swagger));
        variables.put("TAG", new Variable().withValue(tag));
        if (!isPreview) {
            variables.put("VERSION", new Variable().withValue("1.0.0"));
        }
        if (!CoreUtils.isNullOrEmpty(configure.getService())) {
            variables.put("SERVICE", new Variable().withValue(configure.getService()));
        }

        runLiteCodegen(manager, variables);
        OUT.println("wait 1 minutes");
        Thread.sleep(POLL_SHORT_INTERVAL_MINUTE * MILLISECOND_PER_MINUTE);

        mergeGithubPR(client, swagger, sdk);

        runLiteRelease(manager, sdk);

        mergeGithubVersionPR(client, sdk);

        String sdkMavenUrl = MAVEN_ARTIFACT_PATH_PREFIX + "azure-resourcemanager-" + sdk;
        Utils.openUrl(sdkMavenUrl);

        System.exit(0);
    }

    private static void runLiteCodegen(DevManager manager, Map<String, Variable> variables) throws InterruptedException {
        // run pipeline
        Run run = manager.runs().runPipeline(ORGANIZATION, PROJECT, LITE_CODEGEN_PIPELINE_ID,
                new RunPipelineParameters().withVariables(variables));
        int buildId = run.id();

        // wait for complete
        while (run.state() != RunState.COMPLETED && run.state() != RunState.CANCELING) {
            OUT.println("build id: " + buildId + ", state: " + run.state());

            OUT.println("wait 1 minutes");
            Thread.sleep(POLL_SHORT_INTERVAL_MINUTE * MILLISECOND_PER_MINUTE);

            run = manager.runs().get(ORGANIZATION, PROJECT, LITE_CODEGEN_PIPELINE_ID, buildId);
        }
    }

    private static void mergeGithubPR(RepositoryClient client, String swagger, String sdk) throws InterruptedException, ExecutionException {
        PullRequestClient prClient = client.createPullRequestClient();
        List<PullRequestItem> prs = prClient.list(PR_LIST_PARAMS).get();

        PullRequestItem pr = prs.stream()
                .filter(p -> p.title().startsWith("[Automation] Generate Fluent Lite from") && p.title().contains(swagger))
                .findFirst().orElse(null);

        if (pr != null) {
            int prNumber = pr.number();

            String prUrl = "https://github.com/Azure/azure-sdk-for-java/pull/" + prNumber + "/files";
            OUT.println("GitHub pull request: " + prUrl);
            Utils.openUrl(prUrl);

            // wait for CI
            waitForChecks(client, prClient, prNumber, sdk);

            if (PROMPT_CONFIRMATION) {
                Utils.promptMessageAndWait(IN, OUT,
                        "'Yes' to approve and merge GitHub pull request: https://github.com/Azure/azure-sdk-for-java/pull/" + prNumber);
            }

            // approve PR
            Review review = prClient.createReview(pr.number(),
                    ImmutableReviewParameters.builder().event("APPROVE").build()).get();

            // merge PR
            PullRequest prRefreshed = prClient.get(prNumber).get();
            prClient.merge(prNumber,
                    ImmutableMergeParameters.builder().sha(prRefreshed.head().sha()).mergeMethod(MergeMethod.squash).build()).get();
            OUT.println("Pull request merged: " + prNumber);
        } else {
            throw new IllegalStateException("github pull request not found");
        }
    }

    private static void runLiteRelease(DevManager manager, String sdk) throws InterruptedException {
        // find pipeline
        String pipelineName = "java - " + sdk;
        List<Pipeline> pipelines = manager.pipelines().list(ORGANIZATION, PROJECT).stream().collect(Collectors.toList());
        Pipeline pipeline = pipelines.stream()
                .filter(p -> pipelineName.equals(p.name())).findFirst().orElse(null);

        if (pipeline != null) {
            // run pipeline
            Run run = manager.runs().runPipeline(ORGANIZATION, PROJECT, pipeline.id(), new RunPipelineParameters());
            int buildId = run.id();

            String buildUrl = "https://dev.azure.com/azure-sdk/internal/_build/results?buildId=" + buildId;
            OUT.println("DevOps build: " + buildUrl);
            Utils.openUrl(buildUrl);

            // poll until approval is available
            Timeline timeline = manager.timelines().get(ORGANIZATION, PROJECT, buildId, null);
            ReleaseState state = getReleaseState(timeline);
            while (state.getApprovalIds().isEmpty()) {
                OUT.println("wait 5 minutes");
                Thread.sleep(POLL_LONG_INTERVAL_MINUTE * MILLISECOND_PER_MINUTE);

                timeline = manager.timelines().get(ORGANIZATION, PROJECT, buildId, null);
                state = getReleaseState(timeline);
            }

            if (PROMPT_CONFIRMATION) {
                Utils.promptMessageAndWait(IN, OUT,
                        "'Yes' to approve release: " + state.getName());
            }

            // approve new release
            OUT.println("prepare to release: " + state.getName());
            Utils.approve(state.getApprovalIds(), manager, ORGANIZATION, PROJECT);
            OUT.println("approved release: " + state.getName());

            // poll until release completion
            while (state.getState() != TimelineRecordState.COMPLETED) {
                OUT.println("wait 5 minutes");
                Thread.sleep(POLL_LONG_INTERVAL_MINUTE * MILLISECOND_PER_MINUTE);

                timeline = manager.timelines().get(ORGANIZATION, PROJECT, buildId, null);
                state = getReleaseState(timeline);
            }
        } else {
            throw new IllegalStateException("release pipeline not found: " + pipelineName);
        }
    }

    private static void mergeGithubVersionPR(RepositoryClient client, String sdk) throws InterruptedException, ExecutionException {
        PullRequestClient prClient = client.createPullRequestClient();
        List<PullRequestItem> prs = prClient.list(PR_LIST_PARAMS).get();

        PullRequestItem pr = prs.stream()
                .filter(p -> p.title().equals("Increment version for " + sdk + " releases"))
                .findFirst().orElse(null);

        if (pr != null) {
            int prNumber = pr.number();

            // approve PR
            Review review = prClient.createReview(pr.number(),
                    ImmutableReviewParameters.builder().event("APPROVE").build()).get();

            String prUrl = "https://github.com/Azure/azure-sdk-for-java/pull/" + prNumber;
            OUT.println("GitHub pull request: " + prUrl);
            Utils.openUrl(prUrl);

            // wait for check enforcer
            waitForCheckSuccess(prClient, prNumber, CI_CHECK_ENFORCER_NAME);

            // merge PR
            PullRequest prRefreshed = prClient.get(prNumber).get();
            prClient.merge(prNumber,
                    ImmutableMergeParameters.builder().sha(prRefreshed.head().sha()).mergeMethod(MergeMethod.squash).build()).get();
            OUT.println("Pull request merged: " + prNumber);
        } else {
            throw new IllegalStateException("github pull request not found");
        }
    }

    private static ReleaseState getReleaseState(Timeline timeline) {
        List<ReleaseState> states = new ArrayList<>();
        for (TimelineRecord record : timeline.records()) {
            if (record.name().startsWith("Release: azure-resourcemanager-")) {
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

    private static CheckRunListResult getCheckRuns(String sha) {
        HttpRequest request = new HttpRequest(HttpMethod.GET,
                "https://api.github.com/repos/Azure/azure-sdk-for-java/commits/" + sha + "/check-runs");
        request.setHeader("Authorization", "token " + GITHUB_TOKEN)
                .setHeader("Accept", "application/json")
                .setHeader("Content-Type", "application/json");

        HttpResponse response = HTTP_PIPELINE.send(request).block();

        if (response.getStatusCode() == 200) {
            String body = response.getBodyAsString().block();
            try {
                return JacksonAdapter.createDefaultSerializerAdapter().deserialize(body, CheckRunListResult.class, SerializerEncoding.JSON);
            } catch (IOException e) {
                LOGGER.error("error in response body {}", body);
                throw new HttpResponseException(response);
            } finally {
                response.close();
            }
        } else {
            LOGGER.error("error in response code {}, body {}", response.getStatusCode(), response.getBodyAsString().block());
            response.close();
            throw new HttpResponseException(response);
        }
    }

    private static CheckRun getCheck(List<CheckRun> checkRuns, String name) {
        return checkRuns.stream()
                .filter(p -> p.getName().equals(name))
                .findAny().orElse(null);
    }

    private static void waitForChecks(RepositoryClient client, PullRequestClient prClient,
                                      int prNumber, String sdk) throws InterruptedException, ExecutionException {
        // wait a few minutes for PR to init all CIs
        OUT.println("wait 5 minutes");
        Thread.sleep(POLL_LONG_INTERVAL_MINUTE * MILLISECOND_PER_MINUTE);

        String javaSdkCheckName = "java - " + sdk + " - ci";

        PullRequest pr = prClient.get(prNumber).get();
        CheckRunListResult checkRunResult = getCheckRuns(pr.head().sha());
        CheckRun check = getCheck(checkRunResult.getCheckRuns(), javaSdkCheckName);
        if (check == null) {
            IssueClient issueClient = client.createIssueClient();

            // comment to create sdk CI
            Comment comment = issueClient.createComment(prNumber, "/azp run prepare-pipelines").get();

            // wait for prepare pipelines
            pr = prClient.get(prNumber).get();
            waitForCheckSuccess(prClient, prNumber, CI_PREPARE_PIPELINES_NAME, pr.head().sha());

            // comment to run the newly created sdk CI
            comment = issueClient.createComment(prNumber, "/azp run " + javaSdkCheckName).get();

            // wait for sdk CI
            waitForCheckSuccess(prClient, prNumber, javaSdkCheckName);
        }

        // wait for check enforcer
        waitForCheckSuccess(prClient, prNumber, CI_CHECK_ENFORCER_NAME);
    }

    private static void waitForCheckSuccess(PullRequestClient prClient,
                                            int prNumber, String checkName) throws InterruptedException, ExecutionException {
        waitForCheckSuccess(prClient, prNumber, checkName, null);
    }

    private static void waitForCheckSuccess(PullRequestClient prClient,
                                            int prNumber, String checkName, String fixedCommitSHA) throws InterruptedException, ExecutionException {
        String commitSHA = fixedCommitSHA;
        if (commitSHA == null) {
            // refresh head commit
            PullRequest pr = prClient.get(prNumber).get();
            commitSHA = pr.head().sha();
        }
        CheckRunListResult checkRunResult = getCheckRuns(commitSHA);
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
                PullRequest pr = prClient.get(prNumber).get();
                commitSHA = pr.head().sha();
            }
            checkRunResult = getCheckRuns(commitSHA);
            check = getCheck(checkRunResult.getCheckRuns(), checkName);
        }
    }

    private static String getSdkName(String swaggerName) {
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
            return sdkName;
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
}
