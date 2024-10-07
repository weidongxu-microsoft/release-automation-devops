package io.weidongxu.util.releaseautomation.store;

public class NoOpTaskStore implements TaskStore {
    @Override
    public ReleaseTask create(ReleaseTask releaseTask) {
        return releaseTask;
    }

    @Override
    public void update(ReleaseTask task) {
        // NO-OP
    }

    @Override
    public void close() {
        // NO-OP
    }
}
