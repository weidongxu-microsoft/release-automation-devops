package io.weidongxu.util.releaseautomation;

import com.azure.core.credential.BasicAuthenticationCredential;
import com.azure.core.credential.TokenCredential;
import com.azure.core.http.policy.HttpLogDetailLevel;
import com.azure.core.http.policy.HttpLogOptions;
import com.azure.core.management.AzureEnvironment;
import com.azure.core.management.profile.AzureProfile;
import com.azure.core.util.Configuration;
import com.azure.dev.DevManager;
import com.azure.dev.models.Timeline;
import com.azure.dev.models.TimelineRecord;
import com.azure.dev.models.TimelineRecordState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PremiumRelease {

    private static final String USER = Configuration.getGlobalConfiguration().get("DEVOPS_USER");
    private static final String PASS = Configuration.getGlobalConfiguration().get("DEVOPS_PAT");
    private static final String ORGANIZATION = "azure-sdk";
    private static final String PROJECT = "internal";

    private static final int BUILD_ID = Integer.parseInt(Configuration.getGlobalConfiguration().get("BUILD_ID"));

    public static void main(String[] args) throws InterruptedException {
        TokenCredential tokenCredential = new BasicAuthenticationCredential(USER, PASS);

        DevManager manager = DevManager.configure()
                .withLogOptions(new HttpLogOptions().setLogLevel(HttpLogDetailLevel.BODY_AND_HEADERS))
                .withPolicy(new BasicAuthAuthenticationPolicy(tokenCredential))
                .authenticate(
                        new BasicAuthenticationCredential(USER, PASS),
                        new AzureProfile(AzureEnvironment.AZURE));

        long countPending = Integer.MAX_VALUE;

        while (countPending > 0) {
            Timeline timeline = manager.timelines().get(ORGANIZATION, PROJECT, BUILD_ID, null);

            List<ReleaseState> states = new ArrayList<>();
            for (TimelineRecord record : timeline.records()) {
                if (record.name().startsWith("Release: azure-resourcemanager")
                        && !record.name().contains("azure-resourcemanager-samples")) {
                    states.add(Utils.getReleaseState(record, timeline));
                }
            }

//            for (ReleaseState state : states) {
//                System.out.println(state.toString());
//            }

            countPending = states.stream()
                    .filter(s -> s.getState() == TimelineRecordState.PENDING)
                    .count();

            System.out.println("count of pending releases: " + countPending);

            System.out.println("pending releases: " + states.stream()
                    .filter(s -> s.getState() == TimelineRecordState.PENDING)
                    .map(s -> {
                        if ("azure-resourcemanager".equals(s.getName())) {
                            return "AZURE";
                        } else {
                            return s.getName().substring("azure-resourcemanager-".length());
                        }
                    }).collect(Collectors.toList()));

            long countInProgress = states.stream()
                    .filter(s -> s.getState() == TimelineRecordState.IN_PROGRESS)
                    .count();

            System.out.println("count of in progress releases: " + countInProgress);

            if (countInProgress <= 1) {
                List<ReleaseState> remains = states.stream()
                        .filter(s -> s.getState() == TimelineRecordState.PENDING)
                        .sorted(Comparator.comparingInt(o -> o.getName().length()))
                        .collect(Collectors.toList());
                Collections.reverse(remains);
                ReleaseState nextRelease = remains.iterator().next();

                // trigger new release
                System.out.println("prepare to release: " + nextRelease.getName());
                Utils.approve(nextRelease.getApprovalIds(), manager, ORGANIZATION, PROJECT);
                System.out.println("approved release: " + nextRelease.getName());
            }

            System.out.println("wait 5 minutes");
            Thread.sleep(5 * 60 * 1000);
        }

        System.exit(0);
    }
}
