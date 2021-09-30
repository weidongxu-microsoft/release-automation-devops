package io.weidongxu.util.releaseautomation;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ListPipeline {

    private static final Logger LOGGER = LoggerFactory.getLogger(ListPipeline.class);

    private static final String USER = Configuration.getGlobalConfiguration().get("DEVOPS_USER");
    private static final String PASS = Configuration.getGlobalConfiguration().get("DEVOPS_PAT");
    private static final String ORGANIZATION = "azure-sdk";
    private static final String PROJECT_INTERNAL = "internal";
    private static final String PROJECT_PUBLIC = "public";

    public static void main(String[] args) throws Exception {
        TokenCredential tokenCredential = new BasicAuthenticationCredential(USER, PASS);

        DevManager manager = DevManager.configure()
                .withLogOptions(new HttpLogOptions().setLogLevel(HttpLogDetailLevel.BODY_AND_HEADERS))
                .withPolicy(new BasicAuthAuthenticationPolicy(tokenCredential))
                .authenticate(
                        new BasicAuthenticationCredential(USER, PASS),
                        new AzureProfile(AzureEnvironment.AZURE));

        final OffsetDateTime cutOffDate = OffsetDateTime.parse("2021-09-01T00:00:00Z");

        List<Pipeline> pipelines = manager.pipelines().list(ORGANIZATION, PROJECT_INTERNAL).stream()
                .filter(p -> p.name().startsWith("java - ") && p.name().endsWith("- mgmt") && !p.name().contains("lite"))
                .collect(Collectors.toList());

        for (Pipeline pipeline : pipelines) {
            Run lastRun = manager.runs().list(ORGANIZATION, PROJECT_INTERNAL, pipeline.id())
                    .stream().findFirst().orElse(null);
            if (lastRun != null) {
                OffsetDateTime runDate = lastRun.createdDate();
                LOGGER.info("{} ran at {}", pipeline.name(), runDate);

                if (runDate.isAfter(cutOffDate)) {
                    throw new RuntimeException();
                }
            } else {
                LOGGER.info("{} never run", pipeline.name());
            }

//            manager.definitions().delete(ORGANIZATION, PROJECT_INTERNAL, pipeline.id());
        }

        pipelines = manager.pipelines().list(ORGANIZATION, PROJECT_PUBLIC).stream()
                .filter(p -> p.name().startsWith("java - ") && p.name().endsWith("- ci.mgmt"))
                .collect(Collectors.toList());

        for (Pipeline pipeline : pipelines) {
            Run lastRun = manager.runs().list(ORGANIZATION, PROJECT_PUBLIC, pipeline.id())
                    .stream().findFirst().orElse(null);
            if (lastRun != null) {
                OffsetDateTime runDate = lastRun.createdDate();
                LOGGER.info("{} ran at {}", pipeline.name(), runDate);

                if (runDate.isAfter(cutOffDate)) {
                    throw new RuntimeException();
                }
            } else {
                LOGGER.info("{} never run", pipeline.name());
            }

//            manager.definitions().delete(ORGANIZATION, PROJECT_PUBLIC, pipeline.id());
        }
    }
}
