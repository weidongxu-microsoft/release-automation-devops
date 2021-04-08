package com.azure.dev.main;

import com.azure.core.http.HttpMethod;
import com.azure.core.http.HttpRequest;
import com.azure.core.http.HttpResponse;
import com.azure.dev.DevManager;
import com.azure.dev.models.Timeline;
import com.azure.dev.models.TimelineRecord;
import com.azure.dev.models.TimelineRecordState;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Utils {

    public static ReleaseState getReleaseState(TimelineRecord record, Timeline timeline) {
        ReleaseState state = new ReleaseState();
        state.setName(record.name().substring("Release: ".length()));
        state.setState(record.state());

        if (state.getState() == TimelineRecordState.PENDING) {
            UUID id = record.id();

            TimelineRecord checkpointRecord = timeline.records().stream()
                    .filter(r -> id.equals(r.parentId()) && r.name().equals("Checkpoint") && r.type().equals("Checkpoint"))
                    .findAny().orElse(null);
            if (checkpointRecord != null) {
                UUID checkpointId = checkpointRecord.id();
                List<TimelineRecord> checkpointApprovalRecords = timeline.records().stream()
                        .filter(r -> checkpointId.equals(r.parentId()) && r.name().equals("Checkpoint.Approval") && r.type().equals("Checkpoint.Approval"))
                        .filter(r -> r.state() == TimelineRecordState.IN_PROGRESS)
                        .collect(Collectors.toList());

                state.getApprovalIds().addAll(checkpointApprovalRecords.stream().map(TimelineRecord::id).collect(Collectors.toList()));
            }
        }

        return state;
    }

    public static void approve(List<UUID> approvalIds, DevManager manager, String organization, String project) {
        for (UUID approvalId : approvalIds) {
            HttpRequest request = new HttpRequest(
                    HttpMethod.PATCH,
                    String.format("https://dev.azure.com/%s/%s/_apis/pipelines/approvals/%s?api-version=6.0-preview", organization, project, approvalId.toString()));
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

    public static void promptMessageAndWait(InputStream in, PrintStream out, String message) {
        out.println(message);
        try {
            in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
