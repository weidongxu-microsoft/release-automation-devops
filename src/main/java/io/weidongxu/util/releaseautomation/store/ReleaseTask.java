package io.weidongxu.util.releaseautomation.store;

import io.weidongxu.util.releaseautomation.Configure;

import java.util.Objects;
import java.util.UUID;

public class ReleaseTask {
    private String id;
    private String details;
    private LiteReleaseState prevState;
    private LiteReleaseState state;
    private String trackUrl;
    private String errorMessage;
    private String sdkRepoBranch;

    public ReleaseTask(Configure configure) {
        Objects.requireNonNull(configure);
        this.details = configure.toString();
        this.id = UUID.randomUUID().toString();
        this.sdkRepoBranch = configure.getSdkRepoBranch();
    }

    public String getId() {
        return id;
    }

    public String getDetails() {
        return details;
    }

    public String getSdkRepoBranch() {
        return sdkRepoBranch;
    }

    public LiteReleaseState getState() {
        return state;
    }

    public void setState(LiteReleaseState state) {
        this.prevState = this.state;
        this.state = state;
    }

    public String getTrackUrl() {
        return trackUrl;
    }

    public void setTrackUrl(String trackUrl) {
        this.trackUrl = trackUrl;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "ReleaseTask{" +
                "details='" + details + '\'' +
                ", state=" + state +
                ", trackUrl='" + trackUrl + '\'' +
                ", prevState=" + prevState +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
