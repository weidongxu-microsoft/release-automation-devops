package io.weidongxu.util.releaseautomation;

import com.azure.core.http.HttpMethod;
import com.azure.core.http.HttpPipeline;
import com.azure.core.http.HttpRequest;
import com.azure.core.http.HttpResponse;
import com.azure.dev.DevManager;
import com.azure.dev.models.Timeline;
import com.azure.dev.models.TimelineRecord;
import com.azure.dev.models.TimelineRecordState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Collectors;

public class Utils {

    private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);

    public static ReleaseState getReleaseState(TimelineRecord record, Timeline timeline) {
        ReleaseState state = new ReleaseState();
        state.setName(record.name().substring("Release: ".length()));
        state.setIdentifier(record.identifier());
        state.setState(record.state());
        state.setResult(record.result());

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
            request.setBody(String.format("[{\"approvalId\": \"%s\", \"status\": 4, \"comment\": \"\"}]", approvalId));
            request.setHeader("content-type", "application/json");
            HttpResponse response = manager.serviceClient().getHttpPipeline().send(request).block();
            System.out.println("response status code: " + response.getStatusCode());
            if (response.getStatusCode() != 200) {
                System.out.println("response body: " + response.getBodyAsString().block());
                response.close();

                throw new IllegalStateException("failed to approve: " + approvalId);
            } else {
                response.close();
            }
        }
    }

    public static void promptMessageAndWait(InputStream in, PrintStream out, String message) {
        out.println(message);
        Scanner s = new Scanner(in);
        while (true) {
            String input = s.nextLine();
            if ("y".equals(input.toLowerCase(Locale.ROOT)) || "yes".equals(input.toLowerCase(Locale.ROOT))) {
                break;
            }
        }
    }

    public static void openUrl(String url) {
        Runtime rt = Runtime.getRuntime();

        try {
            String os = System.getProperty("os.name").toLowerCase(Locale.ROOT);
            if (os.contains("win")) {
                rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
            } else if (os.contains("mac")) {
                rt.exec("open " + url);
            } else if (os.contains("nix") || os.contains("nux")) {
                rt.exec("xdg-open " + url);
            } else {
                LOGGER.error("Browser could not be opened - please open {} in a browser on this device.", url);
            }
        } catch (IOException e) {
            LOGGER.error("Browser could not be opened - please open {} in a browser on this device.", url);
        }
    }

    private static final String SPEC_REPO_PATH_PREFIX = "https://raw.githubusercontent.com/Azure/azure-rest-api-specs/main/";
    private static final String SPEC_REPO_SPEC_PATH_PREFIX = "https://raw.githubusercontent.com/Azure/azure-rest-api-specs/main/specification/";

    public static ReadmeConfigure getReadmeConfigure(HttpPipeline httpPipeline, String swagger) throws MalformedURLException {
        String readmeUrl = swagger.contains("/")
                ? SPEC_REPO_PATH_PREFIX + swagger
                : SPEC_REPO_SPEC_PATH_PREFIX + swagger + "/resource-manager/readme.md";
        return ReadmeConfigure.parseReadme(httpPipeline, new URL(readmeUrl));
    }
}
