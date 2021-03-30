package com.azure.dev;

import com.azure.core.credential.BasicAuthenticationCredential;
import com.azure.core.credential.TokenCredential;
import com.azure.core.credential.TokenRequestContext;
import com.azure.core.http.HttpMethod;
import com.azure.core.http.HttpPipelineCallContext;
import com.azure.core.http.HttpPipelineNextPolicy;
import com.azure.core.http.HttpRequest;
import com.azure.core.http.HttpResponse;
import com.azure.core.http.policy.HttpLogDetailLevel;
import com.azure.core.http.policy.HttpLogOptions;
import com.azure.core.http.policy.HttpPipelinePolicy;
import com.azure.core.management.AzureEnvironment;
import com.azure.core.management.profile.AzureProfile;
import com.azure.core.util.Configuration;
import com.azure.dev.models.Timeline;
import com.azure.dev.models.TimelineRecord;
import com.azure.dev.models.TimelineRecordState;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Main {

    private static final String USER = Configuration.getGlobalConfiguration().get("USER");
    private static final String PASS = Configuration.getGlobalConfiguration().get("PAT");
    private static final String ORGANIZATION = "azure-sdk";
    private static final String PROJECT = "internal";

    private static final int BUILD_ID = 812133;

    private static class BasicAuthAuthenticationPolicy implements HttpPipelinePolicy {
        private static final String AUTHORIZATION_HEADER = "Authorization";
        private static final String BASIC = "Basic";
        private final String token;

        private BasicAuthAuthenticationPolicy(TokenCredential tokenCredential) {
            token = tokenCredential.getToken(new TokenRequestContext()).block().getToken();
        }

        @Override
        public Mono<HttpResponse> process(HttpPipelineCallContext context, HttpPipelineNextPolicy next) {
            if ("http".equals(context.getHttpRequest().getUrl().getProtocol())) {
                return Mono.error(new RuntimeException("token credentials require a URL using the HTTPS protocol scheme"));
            }
            context.getHttpRequest().getHeaders().set(AUTHORIZATION_HEADER, BASIC + " " + token);
            return next.process();
        }
    }

    private static class ReleaseState {
        private String name;
        private TimelineRecordState state;
        private final List<UUID> approvalIds = new ArrayList<>();

        @Override
        public String toString() {
            return "ReleaseState{" +
                    "name='" + name + '\'' +
                    ", state=" + state +
                    ", approvalIds=" + approvalIds +
                    '}';
        }
    }

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
                    states.add(getReleaseState(record, timeline));
                }
            }

//            for (ReleaseState state : states) {
//                System.out.println(state.toString());
//            }

            countPending = states.stream()
                    .filter(s -> s.state == TimelineRecordState.PENDING)
                    .count();

            System.out.println("count of pending release: " + countPending);

            long countInProgress = states.stream()
                    .filter(s -> s.state == TimelineRecordState.IN_PROGRESS)
                    .count();

            System.out.println("count of in progress release: " + countInProgress);

            if (countInProgress <= 2) {
                List<ReleaseState> remains = states.stream()
                        .filter(s -> s.state == TimelineRecordState.PENDING)
                        .sorted(Comparator.comparingInt(o -> o.name.length()))
                        .collect(Collectors.toList());
                Collections.reverse(remains);
                ReleaseState nextRelease = remains.iterator().next();

                // trigger new release
                System.out.println("prepare to release: " + nextRelease.name);
                approve(nextRelease.approvalIds, manager);
                System.out.println("approved release: " + nextRelease.name);
            }

            System.out.println("wait 5 minutes");
            Thread.sleep(5 * 60 * 1000);
        }
    }

    private static ReleaseState getReleaseState(TimelineRecord record, Timeline timeline) {
        ReleaseState state = new ReleaseState();
        state.name = record.name().substring("Release: ".length());
        state.state = record.state();

        if (state.state == TimelineRecordState.PENDING) {
            UUID id = record.id();

            TimelineRecord checkpointRecord = timeline.records().stream()
                    .filter(r -> id.equals(r.parentId()) && r.name().equals("Checkpoint") && r.type().equals("Checkpoint"))
                    .findAny().orElse(null);
            if (checkpointRecord != null) {
                UUID checkpointId = checkpointRecord.id();
                List<TimelineRecord> checkpointApprovalRecords = timeline.records().stream()
                        .filter(r -> checkpointId.equals(r.parentId()) && r.name().equals("Checkpoint.Approval") && r.type().equals("Checkpoint.Approval"))
                        .collect(Collectors.toList());

                state.approvalIds.addAll(checkpointApprovalRecords.stream().map(TimelineRecord::id).collect(Collectors.toList()));
            }
        }

        return state;
    }

    private static void approve(List<UUID> approvalIds, DevManager manager) {
        for (UUID approvalId : approvalIds) {
            HttpRequest request = new HttpRequest(
                    HttpMethod.PATCH,
                    String.format("https://dev.azure.com/%s/%s/_apis/pipelines/approvals/%s?api-version=6.0-preview", ORGANIZATION, PROJECT, approvalId.toString()));
            request.setBody(String.format("[{\"approvalId\": \"%s\", \"status\": 4, \"comment\": \"\"}]", approvalId.toString()));
            request.setHeader("content-type", "application/json");
            HttpResponse response = manager.serviceClient().getHttpPipeline().send(request).block();
            System.out.println("response status code: " + response.getStatusCode());
            if (response.getStatusCode() != 200) {
                System.out.println("response body: " + response.getBodyAsString().block());

                throw new IllegalStateException("failed to approve: " + approvalId);
            }
        }
    }
}
