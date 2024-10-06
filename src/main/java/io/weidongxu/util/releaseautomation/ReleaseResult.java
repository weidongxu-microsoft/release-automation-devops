package io.weidongxu.util.releaseautomation;

public class ReleaseResult {
    Configure configure;
    boolean succeeded;
    String message;

    public static ReleaseResult success(Configure configure) {
        ReleaseResult result = new ReleaseResult();
        result.configure = configure;
        result.succeeded = true;
        return result;
    }

    public static ReleaseResult failure(Configure configure, String message) {
        ReleaseResult result = new ReleaseResult();
        result.configure = configure;
        result.succeeded = false;
        result.message = message;
        return result;
    }

    @Override
    public String toString() {
        return "ReleaseResult{" +
                "configure=" + configure +
                ", succeeded=" + succeeded +
                ", message='" + message + '\'' +
                '}';
    }
}
