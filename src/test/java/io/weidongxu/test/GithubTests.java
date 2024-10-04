package io.weidongxu.test;

import com.azure.core.util.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kohsuke.github.GHPullRequestReviewState;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import java.io.IOException;

public class GithubTests {
    @Test
    public void testGithubReview() throws IOException {
        String githubToken = Configuration.getGlobalConfiguration().get("GITHUB_PAT");

        GitHub github = new GitHubBuilder()
                .withOAuthToken(githubToken).build();

        GHRepository repository = github.getRepository("Azure/azure-sdk-for-java");
        GHUser self = github.getMyself();
        Assertions.assertTrue(repository.getPullRequest(42132).listReviews().toList().stream().anyMatch(review -> {
            try {
                return review.getState() == GHPullRequestReviewState.APPROVED
                        && self.equals(review.getUser());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
    }
}
