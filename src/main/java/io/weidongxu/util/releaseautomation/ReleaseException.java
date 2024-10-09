package io.weidongxu.util.releaseautomation;

import io.weidongxu.util.releaseautomation.store.LiteReleaseState;

import java.util.Objects;

public class ReleaseException extends RuntimeException {
    private final LiteReleaseState state;
    private Throwable cause;
    private String message;

    public ReleaseException(LiteReleaseState state) {
        Objects.requireNonNull(state);
        this.state = state;
    }

    public ReleaseException(LiteReleaseState state, Throwable cause) {
        Objects.requireNonNull(state);
        this.state = state;
        this.cause = cause;
    }

    public ReleaseException(LiteReleaseState state, String message) {
        Objects.requireNonNull(state);
        this.state = state;
        this.message = message;
    }

    public LiteReleaseState getState() {
        return state;
    }

    @Override
    public String getMessage() {
        if (message != null) {
            return message;
        } else if (cause != null) {
            return cause.getMessage();
        }
        return super.getMessage();
    }

    @Override
    public Throwable getCause() {
        return cause;
    }
}
