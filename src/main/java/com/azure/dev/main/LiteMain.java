package com.azure.dev.main;

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
import com.azure.core.util.serializer.JacksonAdapter;
import com.azure.core.util.serializer.SerializerEncoding;
import com.azure.dev.DevManager;
import com.azure.dev.models.Pipeline;
import com.azure.dev.models.Run;
import com.azure.dev.models.RunPipelineParameters;
import com.azure.dev.models.RunState;
import com.azure.dev.models.Timeline;
import com.azure.dev.models.TimelineRecord;
import com.azure.dev.models.Variable;
import com.spotify.github.v3.clients.GitHubClient;
import com.spotify.github.v3.clients.PullRequestClient;
import com.spotify.github.v3.clients.RepositoryClient;
import com.spotify.github.v3.comment.Comment;
import com.spotify.github.v3.prs.ImmutableMergeParameters;
import com.spotify.github.v3.prs.ImmutableReviewParameters;
import com.spotify.github.v3.prs.MergeMethod;
import com.spotify.github.v3.prs.PullRequestItem;
import com.spotify.github.v3.prs.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class LiteMain {

    private static Logger LOGGER = LoggerFactory.getLogger(LiteMain.class);

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

    private static final InputStream IN = System.in;
    private static final PrintStream OUT = System.out;

    public static void main(String[] args) throws Exception {
        TokenCredential tokenCredential = new BasicAuthenticationCredential(USER, PASS);

        DevManager manager = DevManager.configure()
                .withLogOptions(new HttpLogOptions().setLogLevel(HttpLogDetailLevel.BODY_AND_HEADERS))
                .withPolicy(new BasicAuthAuthenticationPolicy(tokenCredential))
                .authenticate(
                        new BasicAuthenticationCredential(USER, PASS),
                        new AzureProfile(AzureEnvironment.AZURE));

        GitHubClient github = GitHubClient.create(new URI("https://api.github.com/"), GITHUB_TOKEN);
        RepositoryClient client = github.createRepositoryClient(GITHUB_ORGANIZATION, GITHUB_PROJECT);

        String swagger = "resourcehealth";
        String sdk = swagger;  // TODO read from yaml

        Map<String, Variable> variables = new HashMap<>();
        variables.put("README", new Variable().withValue(swagger));
        //variables.put("VERSION", new Variable().withValue("1.0.0"));
        //variables.put("TAG", new Variable().withValue("package-2019-11"));

        runLiteCodegen(manager, variables);

        mergeGithubPR(client, sdk);

        runLiteRelease(manager, sdk);
    }

    private static void runLiteCodegen(DevManager manager, Map<String, Variable> variables) throws InterruptedException {
        // run pipeline
        Run run = manager.runs().runPipeline(ORGANIZATION, PROJECT, LITE_CODEGEN_PIPELINE_ID,
                new RunPipelineParameters().withVariables(variables));
        int runId = run.id();

        // wait for complete
        while (run.state() != RunState.COMPLETED && run.state() != RunState.CANCELING) {
            OUT.println("run id " + runId + ", state " + run.state());

            OUT.println("wait 1 minutes");
            Thread.sleep(60 * 1000);

            run = manager.runs().get(ORGANIZATION, PROJECT, LITE_CODEGEN_PIPELINE_ID, runId);
        }
    }

    private static void mergeGithubPR(RepositoryClient client, String sdk) throws InterruptedException, ExecutionException {
        PullRequestClient prClient = client.createPullRequestClient();
        List<PullRequestItem> prs = prClient.list().get();

        PullRequestItem pr = prs.stream()
                .filter(p -> p.title().startsWith("[Automation]") && p.title().contains(sdk))
                .findFirst().orElse(null);

        if (pr != null) {
            int prNumber = pr.number();

            String prUrl = "https://github.com/Azure/azure-sdk-for-java/pull/" + prNumber + "/files";
            OUT.println("GitHub pull request: " + prUrl);
            Utils.openUrl(prUrl);

            // wait for CI
            waitForChecks(client, pr, sdk);

            Utils.promptMessageAndWait(IN, OUT,
                    "'Yes' to approve and merge GitHub pull request: https://github.com/Azure/azure-sdk-for-java/pull/" + prNumber);

            // approve PR
            Review review = prClient.createReview(pr.number(),
                    ImmutableReviewParameters.builder().event("APPROVE").build()).get();

            // merge PR
            prClient.merge(prNumber,
                    ImmutableMergeParameters.builder().sha(pr.head().sha()).mergeMethod(MergeMethod.squash).build()).get();
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

            // poll status
            Timeline timeline = manager.timelines().get(ORGANIZATION, PROJECT, buildId, null);
            ReleaseState state = getReleaseState(timeline);
            while (state.getApprovalIds().isEmpty()) {
                OUT.println("wait 5 minutes");
                Thread.sleep(5 * 60 * 1000);

                timeline = manager.timelines().get(ORGANIZATION, PROJECT, buildId, null);
                state = getReleaseState(timeline);
            }

            Utils.promptMessageAndWait(IN, OUT,
                    "'Yes' to approve release: " + state.getName());

            // approve new release
            OUT.println("prepare to release: " + state.getName());
            Utils.approve(state.getApprovalIds(), manager, ORGANIZATION, PROJECT);
            OUT.println("approved release: " + state.getName());
        } else {
            throw new IllegalStateException("release pipeline not found: " + pipelineName);
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
            return states.iterator().next();
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
            }
        } else {
            LOGGER.error("error in response code {}, body {}", response.getStatusCode(), response.getBodyAsString().block());
            throw new HttpResponseException(response);
        }
    }

    private static CheckRun getCheck(List<CheckRun> checkRuns, String name) {
        return checkRuns.stream()
                .filter(p -> p.getName().equals(name))
                .findAny().orElse(null);
    }

    private static void waitForChecks(RepositoryClient client, PullRequestItem pr, String sdk) throws InterruptedException, ExecutionException {
        String javaSdkCheckName = "java - " + sdk + " - ci";
        CheckRunListResult checkRunResult = getCheckRuns(pr.head().sha());
        CheckRun check = getCheck(checkRunResult.getCheckRuns(), javaSdkCheckName);
        if (check == null) {
            // comment to create sdk CI
            Comment comment = client.createIssueClient().createComment(pr.number(), "/azp run prepare-pipelines").get();

            // wait for prepare pipelines
            waitForCheckSuccess(pr, CI_PREPARE_PIPELINES_NAME);

            // comment to run the newly created sdk CI
            comment = client.createIssueClient().createComment(pr.number(), javaSdkCheckName).get();

            waitForCheckSuccess(pr, javaSdkCheckName);
        }

        // wait for check enforcer
        waitForCheckSuccess(pr, CI_CHECK_ENFORCER_NAME);
    }

    private static void waitForCheckSuccess(PullRequestItem pr, String checkName) throws InterruptedException {
        CheckRunListResult checkRunResult = getCheckRuns(pr.head().sha());
        CheckRun check = getCheck(checkRunResult.getCheckRuns(), checkName);
        while (check == null || !"success".equals(check.getConclusion())) {
            if (check == null) {
                OUT.println("pr number " + pr.number() + ", wait for " + checkName);
            } else {
                OUT.println("pr number " + pr.number() + ", " + checkName + " status " + check.getStatus() + ", conclusion " + check.getConclusion());
            }

            OUT.println("wait 1 minutes");
            Thread.sleep(60 * 1000);

            checkRunResult = getCheckRuns(pr.head().sha());
            check = getCheck(checkRunResult.getCheckRuns(), checkName);
        }
    }
}
