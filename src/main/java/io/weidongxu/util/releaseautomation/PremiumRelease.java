package io.weidongxu.util.releaseautomation;

import com.azure.core.credential.BasicAuthenticationCredential;
import com.azure.core.credential.TokenCredential;
import com.azure.core.http.policy.HttpLogDetailLevel;
import com.azure.core.http.policy.HttpLogOptions;
import com.azure.core.management.AzureEnvironment;
import com.azure.core.management.profile.AzureProfile;
import com.azure.core.util.Configuration;
import com.azure.dev.DevManager;
import com.azure.dev.models.StageUpdateType;
import com.azure.dev.models.TaskResult;
import com.azure.dev.models.Timeline;
import com.azure.dev.models.TimelineRecord;
import com.azure.dev.models.TimelineRecordState;
import com.azure.dev.models.UpdateStageParameters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PremiumRelease {

    private static final String USER = Configuration.getGlobalConfiguration().get("DEVOPS_USER");
    private static final String PASS = Configuration.getGlobalConfiguration().get("DEVOPS_PAT");
    private static final String ORGANIZATION = "azure-sdk";
    private static final String PROJECT = "internal";

    private static final int BUILD_ID = Integer.parseInt(Configuration.getGlobalConfiguration().get("BUILD_ID"));

    private static final List<String> RELEASE_ORDER = Arrays.asList(
            "azure-resourcemanager-resources",
            "azure-resourcemanager-storage",
            "azure-resourcemanager-authorization",
            "azure-resourcemanager-keyvault",
            "azure-resourcemanager-msi",
            "azure-resourcemanager-network",
            "azure-resourcemanager-compute",
            "azure-resourcemanager-sql",
            "azure-resourcemanager-dns",
            "azure-resourcemanager-appservice",
            "azure-resourcemanager-cosmos",
            "azure-resourcemanager-containerservice",
            "azure-resourcemanager-eventhubs",
            "azure-resourcemanager-monitor",
            "azure-resourcemanager-containerregistry",
            "azure-resourcemanager-appplatform",
            "azure-resourcemanager-containerinstance",
            "azure-resourcemanager-privatedns",
            "azure-resourcemanager-redis",
            "azure-resourcemanager-trafficmanager",
            "azure-resourcemanager-servicebus",
            "azure-resourcemanager-cdn",
            "azure-resourcemanager-search",
            "azure-resourcemanager"
    );

    private static final Set<String> DEPENDENT_RELEASES = new HashSet<>(Arrays.asList(
            "azure-resourcemanager-resources",
            "azure-resourcemanager-authorization",
            "azure-resourcemanager-network"
    ));

    private static final int RELEASE_CONCURRENCY = 2;

    public static void main(String[] args) throws InterruptedException {
        DevManager manager = buildDevManager();

        do {
            resetFailedReleaseStates(manager);

            List<ReleaseState> states = fetchCurrentReleases(manager);

            printPendingReleases(states);

            triggerNextReleaseIfPossible(manager, states);

            wait5Minutes();
        } while (hasUnfinishedReleases(manager));

        System.exit(0);
    }

    private static DevManager buildDevManager() {
        TokenCredential tokenCredential = new BasicAuthenticationCredential(USER, PASS);

        return DevManager.configure()
                .withLogOptions(new HttpLogOptions().setLogLevel(HttpLogDetailLevel.BODY_AND_HEADERS))
                .withPolicy(new BasicAuthAuthenticationPolicy(tokenCredential))
                .authenticate(
                        new BasicAuthenticationCredential(USER, PASS),
                        new AzureProfile(AzureEnvironment.AZURE));
    }

    private static void wait5Minutes() throws InterruptedException {
        System.out.println("wait 5 minutes");
        Thread.sleep(5 * 60 * 1000);
    }

    private static void triggerNextReleaseIfPossible(DevManager manager, List<ReleaseState> states) {
        if (canTriggerNextRelease(states)) {
            triggerNextRelease(manager, states);
        }
    }

    private static boolean hasUnfinishedReleases(DevManager manager) {
        return fetchCurrentReleases(manager)
                .stream()
                .anyMatch(s -> s.getState() == TimelineRecordState.PENDING
                                || s.getState() == TimelineRecordState.IN_PROGRESS);
    }

    private static void triggerNextRelease(DevManager manager, List<ReleaseState> states) {
        List<ReleaseState> remains = states.stream()
                .filter(s -> s.getState() == TimelineRecordState.PENDING)
                .sorted(Comparator.comparingInt(o -> {
                    int index = RELEASE_ORDER.indexOf(o.getName());
                    return index == -1 ? 100 : index;
                }))
                .collect(Collectors.toList());
        if (!remains.isEmpty()) {
            ReleaseState nextRelease = remains.iterator().next();

            // trigger new release
            System.out.println("prepare to release: " + nextRelease.getName());
            Utils.approve(nextRelease.getApprovalIds(), manager, ORGANIZATION, PROJECT);
            System.out.println("approved release: " + nextRelease.getName());
        }
    }

    private static boolean canTriggerNextRelease(List<ReleaseState> states) {
        Set<String> inProgress = states.stream()
                .filter(s -> s.getState() == TimelineRecordState.IN_PROGRESS)
                .map(ReleaseState::getName)
                .collect(Collectors.toSet());
        long countInProgress = inProgress.size();

        System.out.println("count of in progress releases: " + countInProgress);

        int release_concurrency = RELEASE_CONCURRENCY;
        if (!Collections.disjoint(DEPENDENT_RELEASES, inProgress)) {
            release_concurrency = 1;
        }
        return countInProgress < release_concurrency;
    }

    private static void printPendingReleases(List<ReleaseState> states) {
        long countPending = states.stream()
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
    }

    private static void resetFailedReleaseStates(DevManager manager) throws InterruptedException {
        List<ReleaseState> states = fetchCurrentReleases(manager);

        List<ReleaseState> failedReleases = states.stream()
                .filter(s -> s.getState() == TimelineRecordState.COMPLETED)
                .filter(s -> s.getResult() == TaskResult.FAILED)
                .collect(Collectors.toList());
        if (!failedReleases.isEmpty()) {
            for (ReleaseState state : failedReleases) {
                manager.stages().update(ORGANIZATION, BUILD_ID, state.getIdentifier(), PROJECT,
                        new UpdateStageParameters().withState(StageUpdateType.RETRY));
                System.out.println("retry failed release: " + state.getName().substring("azure-resourcemanager-".length()));
            }
            Thread.sleep(60 * 1000);
        }
    }

    private static List<ReleaseState> fetchCurrentReleases(DevManager manager) {
        Timeline timeline = manager.timelines().get(ORGANIZATION, PROJECT, BUILD_ID, null);

        List<ReleaseState> states = new ArrayList<>();
        for (TimelineRecord record : timeline.records()) {
            if (record.name().startsWith("Release: azure-resourcemanager")
                    && !record.name().contains("azure-resourcemanager-samples")) {
                states.add(Utils.getReleaseState(record, timeline));
            }
        }
        return states;
    }
}
