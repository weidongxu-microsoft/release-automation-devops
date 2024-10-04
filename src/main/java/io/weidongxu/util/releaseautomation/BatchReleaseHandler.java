package io.weidongxu.util.releaseautomation;

import io.netty.util.concurrent.DefaultThreadFactory;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BatchReleaseHandler extends ReleaseHandler {
    private final ExecutorService executor;
    private final ReleaseHelper releaseHelper;
    protected BatchReleaseHandler(Options options) throws IOException {
        this.executor = Executors.newFixedThreadPool(options.getParallelism(), new DefaultThreadFactory("batch-release-handler"));
        this.releaseHelper = new ReleaseHelper.Builder().build();
    }

    @Override
    public void submit(Configure configure, Runnable onSuccess, Runnable onFailure) {
        executor.submit(() -> {
            try {
                releaseHelper.doRelease(configure);
                onSuccess.run();
            } catch (Exception e) {
                onFailure.run();
            }
            return null;
        });
    }
}
