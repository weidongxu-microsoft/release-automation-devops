package io.weidongxu.util.releaseautomation;

import com.azure.core.util.Configuration;
import com.spotify.github.v3.clients.GitHubClient;
import com.spotify.github.v3.clients.PullRequestClient;
import com.spotify.github.v3.clients.RepositoryClient;
import com.spotify.github.v3.prs.ImmutableMergeParameters;
import com.spotify.github.v3.prs.MergeMethod;
import com.spotify.github.v3.prs.PullRequestItem;
import com.spotify.github.v3.prs.requests.ImmutablePullRequestParameters;
import com.spotify.github.v3.prs.requests.PullRequestParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.List;

public class ExampleAutoMerge {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleAutoMerge.class);

    private static final String GITHUB_TOKEN = Configuration.getGlobalConfiguration().get("GITHUB_PAT");
    private static final String GITHUB_ORGANIZATION = "Azure";
    private static final String GITHUB_PROJECT = "azure-rest-api-specs-examples";

    private static final PullRequestParameters PR_LIST_PARAMS = ImmutablePullRequestParameters.builder()
            .page(1).per_page(50).state("open").build();

    public static void main(String[] args) throws Exception {
        GitHubClient github = GitHubClient.create(new URI("https://api.github.com/"), GITHUB_TOKEN);
        RepositoryClient client = github.createRepositoryClient(GITHUB_ORGANIZATION, GITHUB_PROJECT);

        PullRequestClient prClient = client.createPullRequestClient();
        List<PullRequestItem> prs = prClient.list(PR_LIST_PARAMS).get();

        for (PullRequestItem pr : prs) {
            prClient.merge(pr.number(),
                    ImmutableMergeParameters.builder().sha(pr.head().sha()).mergeMethod(MergeMethod.squash).build()).get();
        }

        System.exit(0);
    }
}
