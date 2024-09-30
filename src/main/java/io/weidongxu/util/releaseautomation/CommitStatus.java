package io.weidongxu.util.releaseautomation;

public class CommitStatus {

    private Long id;

    private String state = "HttpFailure";

    private String context;

    public Long getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public String getContext() {
        return context;
    }
}
