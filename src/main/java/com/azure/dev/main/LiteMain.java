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
import com.spotify.github.v3.prs.ImmutableMergeParameters;
import com.spotify.github.v3.prs.ImmutableReviewParameters;
import com.spotify.github.v3.prs.MergeMethod;
import com.spotify.github.v3.prs.PullRequestItem;
import com.spotify.github.v3.prs.Review;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class LiteMain {

    private static final String USER = Configuration.getGlobalConfiguration().get("DEVOPS_USER");
    private static final String PASS = Configuration.getGlobalConfiguration().get("DEVOPS_PAT");
    private static final String ORGANIZATION = "azure-sdk";
    private static final String PROJECT = "internal";

    private static final String GITHUB_TOKEN = Configuration.getGlobalConfiguration().get("GITHUB_PAT");
    private static final String GITHUB_ORGANIZATION = "Azure";
    private static final String GITHUB_PROJECT = "azure-sdk-for-java";

    private static final int LITE_CODEGEN_PIPELINE_ID = 2238;

    private static final String API_SPECS_YAML_PATH = "https://raw.githubusercontent.com/Azure/azure-sdk-for-java/master/eng/mgmt/automation/api-specs.yaml";

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

        String swagger = "synapse";
        String sdk = "synapse";  // TODO read from yaml

        Map<String, Variable> variables = new HashMap<>();
        variables.put("README", new Variable().withValue(swagger));
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
            System.out.println("run id " + runId + ", state " + run.state());

            System.out.println("wait 1 minutes");
            Thread.sleep(60 * 1000);

            run = manager.runs().get(ORGANIZATION, PROJECT, LITE_CODEGEN_PIPELINE_ID, runId);
        }
    }

    private static void mergeGithubPR(RepositoryClient client, String sdk) throws ExecutionException, InterruptedException, IOException {
        PullRequestClient prClient = client.createPullRequestClient();
        List<PullRequestItem> prs = prClient.list().get();

        PullRequestItem pr = prs.stream()
                .filter(p -> p.title().startsWith("[Automation]") && p.title().contains(sdk))
                .findFirst().orElse(null);

        if (pr != null) {
            int prNumber = pr.number();

            // approve PR
            Review review = prClient.createReview(pr.number(),
                    ImmutableReviewParameters.builder().event("APPROVE").build()).get();

            // wait for CI
            CheckRunListResult checkRunResult = getCheckRuns(pr.head().ref());
            boolean pass = isCheckEnforcerPass(checkRunResult.getCheckRuns());
            while (!pass) {
                System.out.println("pr number " + prNumber + ", ci pass " + pass);

                System.out.println("wait 1 minutes");
                Thread.sleep(60 * 1000);

                checkRunResult = getCheckRuns(pr.head().sha());
                pass = isCheckEnforcerPass(checkRunResult.getCheckRuns());
            }

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
            int runId = run.id();

            // poll status
            Timeline timeline = manager.timelines().get(ORGANIZATION, PROJECT, runId, null);
            ReleaseState state = getReleaseState(timeline);
            while (state.getApprovalIds().isEmpty()) {
                System.out.println("wait 5 minutes");
                Thread.sleep(5 * 60 * 1000);

                timeline = manager.timelines().get(ORGANIZATION, PROJECT, runId, null);
                state = getReleaseState(timeline);
            }

            // approve new release
            System.out.println("prepare to release: " + state.getName());
            Utils.approve(state.getApprovalIds(), manager, ORGANIZATION, PROJECT);
            System.out.println("approved release: " + state.getName());
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

    private static CheckRunListResult getCheckRuns(String ref) throws IOException {
        HttpRequest request = new HttpRequest(HttpMethod.GET,
                "https://api.github.com/repos/Azure/azure-sdk-for-java/commits/" + ref + "/check-runs");
        request.setHeader("Authorization", "token " + GITHUB_TOKEN)
                .setHeader("Accept", "application/json")
                .setHeader("Content-Type", "application/json");

        HttpResponse response = HTTP_PIPELINE.send(request).block();

        if (response.getStatusCode() == 200) {
            String body = response.getBodyAsString().block();
            return JacksonAdapter.createDefaultSerializerAdapter().deserialize(body, CheckRunListResult.class, SerializerEncoding.JSON);
        } else {
            throw new HttpResponseException(response);
        }
    }

    private static boolean isCheckEnforcerPass(List<CheckRun> checkRuns) {
        Optional<CheckRun> checkEnforcer = checkRuns.stream()
                .filter(p -> p.getName().equals("check-enforcer")).findAny();

        return checkEnforcer.map(checkRun -> checkRun.getConclusion().equals("success")).orElse(false);
    }
}
