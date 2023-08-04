package io.weidongxu.util.releaseautomation;

import com.azure.dev.models.TaskResult;
import com.azure.dev.models.TimelineRecordState;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReleaseState {
    private String name;
    private String identifier;
    private TimelineRecordState state;
    private TaskResult result;
    private final List<UUID> approvalIds = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public TimelineRecordState getState() {
        return state;
    }

    public void setState(TimelineRecordState state) {
        this.state = state;
    }

    public TaskResult getResult() {
        return result;
    }

    public void setResult(TaskResult result) {
        this.result = result;
    }

    public List<UUID> getApprovalIds() {
        return approvalIds;
    }

    @Override
    public String toString() {
        return "ReleaseState{" +
                "name='" + name + '\'' +
                ", identifier='" + identifier + '\'' +
                ", state=" + state +
                ", result=" + result +
                ", approvalIds=" + approvalIds +
                '}';
    }
}
