package io.weidongxu.util.releaseautomation.store;

public interface TaskStore {
    ReleaseTask create(ReleaseTask releaseTask);

    void update(ReleaseTask task);

    void close();
}
