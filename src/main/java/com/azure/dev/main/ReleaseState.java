package com.azure.dev.main;

import com.azure.dev.models.TimelineRecordState;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class ReleaseState {
    private String name;
    private TimelineRecordState state;
    private final List<UUID> approvalIds = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TimelineRecordState getState() {
        return state;
    }

    public void setState(TimelineRecordState state) {
        this.state = state;
    }

    public List<UUID> getApprovalIds() {
        return approvalIds;
    }

    @Override
    public String toString() {
        return "ReleaseState{" +
                "name='" + name + '\'' +
                ", state=" + state +
                ", approvalIds=" + approvalIds +
                '}';
    }
}
