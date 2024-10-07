package io.weidongxu.util.releaseautomation.store;

import io.weidongxu.util.releaseautomation.Configure;

import java.util.Objects;
import java.util.UUID;

public class ReleaseTask {
    private String id;
    private String details;
    private LiteReleaseState state;
    private String trackUrl;

    public ReleaseTask(Configure configure) {
        Objects.requireNonNull(configure);
        this.details = configure.toString();
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public String getDetails() {
        return details;
    }

    public LiteReleaseState getState() {
        return state;
    }

    public void setState(LiteReleaseState state) {
        this.state = state;
    }

    public String getTrackUrl() {
        return trackUrl;
    }

    public void setTrackUrl(String trackUrl) {
        this.trackUrl = trackUrl;
    }

    @Override
    public String toString() {
        return "ReleaseTask{" +
                "details='" + details + '\'' +
                ", state=" + state +
                ", trackUrl='" + trackUrl + '\'' +
                '}';
    }
}
