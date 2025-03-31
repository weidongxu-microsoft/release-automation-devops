package io.weidongxu.test;

import com.azure.core.http.HttpPipeline;
import com.azure.core.http.HttpPipelineBuilder;
import com.azure.core.util.Configuration;
import io.weidongxu.util.releaseautomation.TspConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

public class TspConfigTests {
    private final HttpPipeline httpPipeline = new HttpPipelineBuilder().build();
    private GHRepository repository;

    @BeforeEach
    public void setupTest() throws Exception {
        String githubToken = Configuration.getGlobalConfiguration().get("GITHUB_PAT");
        GitHub github = new GitHubBuilder()
            .withOAuthToken(githubToken).build();
        repository = github.getRepository("Azure/azure-rest-api-specs");
    }

    @Test
    public void parseTest() throws Exception {

        String urlWithMain = "https://github.com/Azure/azure-rest-api-specs/blob/main/specification/mongocluster/DocumentDB.MongoCluster.Management/tspconfig.yaml";
        String urlWithSha = "https://github.com/Azure/azure-rest-api-specs/blob/7605afe88e3201dc25ce0881c2e49fe1b6bbdd54/specification/mongocluster/DocumentDB.MongoCluster.Management/tspconfig.yaml";
        String invalidUrl = "https://github.com/Azure/azure-rest-api-specs/blob/mybranch/specification/mongocluster/DocumentDB.MongoCluster.Management/tspconfig.yaml";
        TspConfig mainConfig = TspConfig.parse(repository, httpPipeline, urlWithMain);
        Assertions.assertEquals("mongocluster", mainConfig.getService());
        Assertions.assertEquals("azure-resourcemanager-mongocluster", mainConfig.getPackageDir());
        System.out.println(mainConfig.getUrl());

        TspConfig shaConfig = TspConfig.parse(repository, httpPipeline, urlWithSha);
        Assertions.assertEquals("mongocluster", shaConfig.getService());

        Assertions.assertThrows(IllegalArgumentException.class, () -> TspConfig.parse(repository, httpPipeline, invalidUrl));
    }

    @Test
    public void parseServiceTest() throws Exception {
        String tspConfigUrl = "https://github.com/Azure/azure-rest-api-specs/blob/main/specification/liftrarize/ArizeAi.ObservabilityEval.Management/tspconfig.yaml";
        TspConfig tspConfig = TspConfig.parse(repository, httpPipeline, tspConfigUrl);
        Assertions.assertEquals("arizeaiobservabilityeval", tspConfig.getService());
    }
}
