package com.azure.dev.main;

import com.azure.core.credential.BasicAuthenticationCredential;
import com.azure.core.credential.TokenCredential;
import com.azure.core.http.policy.HttpLogDetailLevel;
import com.azure.core.http.policy.HttpLogOptions;
import com.azure.core.management.AzureEnvironment;
import com.azure.core.management.profile.AzureProfile;
import com.azure.core.util.Configuration;
import com.azure.dev.DevManager;
import com.azure.dev.models.Pipeline;
import com.azure.dev.models.Run;
import com.azure.dev.models.RunPipelineParameters;
import com.azure.dev.models.RunState;
import com.azure.dev.models.Timeline;
import com.azure.dev.models.Variable;
import com.spotify.github.v3.clients.GitHubClient;
import com.spotify.github.v3.clients.PullRequestClient;
import com.spotify.github.v3.clients.RepositoryClient;
import com.spotify.github.v3.prs.ImmutableMergeParameters;
import com.spotify.github.v3.prs.ImmutableReviewParameters;
import com.spotify.github.v3.prs.MergeMethod;
import com.spotify.github.v3.prs.PullRequest;
import com.spotify.github.v3.prs.PullRequestItem;
import com.spotify.github.v3.prs.Review;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

        String swagger = "cost-management";
        String sdk = "costmanagement";  // TODO read from yaml

        Map<String, Variable> variables = new HashMap<>();
        variables.put("README", new Variable().withValue(swagger));
        variables.put("TAG", new Variable().withValue("package-2019-11"));

        //runLiteCodegen(manager, variables);

        //mergeGithubPR(client, sdk);

        runRelease(manager, sdk);
    }

    private static void runLiteCodegen(DevManager manager, Map<String, Variable> variables) throws InterruptedException {
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

    private static void mergeGithubPR(RepositoryClient client, String sdk) throws ExecutionException, InterruptedException {
        PullRequestClient prClient = client.createPullRequestClient();
        List<PullRequestItem> prs = prClient.list().get();

        PullRequestItem prItem = prs.stream()
                .filter(p -> p.title().startsWith("[Automation]") && p.title().contains(sdk))
                .findFirst().orElse(null);

        if (prItem != null) {
            int prNumber = prItem.number();
            Review review = prClient.createReview(prItem.number(),
                    ImmutableReviewParameters.builder().event("APPROVE").build()).get();

            PullRequest pr = prClient.get(prNumber).get();
            while (!pr.mergeable().get()) {
                System.out.println("pr number " + prNumber + ", mergeable " + pr.mergeable());

                System.out.println("wait 1 minutes");
                Thread.sleep(60 * 1000);
            }

            prClient.merge(prNumber,
                    ImmutableMergeParameters.builder().sha(pr.head().sha()).mergeMethod(MergeMethod.squash).build()).get();
        } else {
            throw new IllegalStateException("github pull request not found");
        }
    }

    private static void runRelease(DevManager manager, String sdk) {
        String pipelineName = "java - " + sdk;
        List<Pipeline> pipelines = manager.pipelines().list(ORGANIZATION, PROJECT).stream().collect(Collectors.toList());
        Pipeline pipeline = pipelines.stream()
                .filter(p -> pipelineName.equals(p.name())).findFirst().orElse(null);

        if (pipeline != null) {
            Run run = manager.runs().runPipeline(ORGANIZATION, PROJECT, pipeline.id(), new RunPipelineParameters());
            int runId = run.id();

            Timeline timeline = manager.timelines().get(ORGANIZATION, PROJECT, runId, null);
        } else {
            throw new IllegalStateException("release pipeline not found: " + pipelineName);
        }
    }
}
