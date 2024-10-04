package io.weidongxu.util.releaseautomation;

import java.io.IOException;
import java.util.Objects;

public abstract class ReleaseHandler {

    public static ReleaseHandler getReleaseHandler(Options options) throws IOException {
        Objects.requireNonNull(options);
        if (options.isBatch()) {
            return new BatchReleaseHandler(options);
        } else {
            return new SimpleReleaseHandler();
        }
    }

    public abstract void submit(Configure configure, Runnable onSuccess, Runnable onFailure);

    public static class Options {
        private boolean batch;
        private int parallelism = 2 * Runtime.getRuntime().availableProcessors() + 1;

        public boolean isBatch() {
            return batch;
        }

        public void setBatch(boolean batch) {
            this.batch = batch;
        }

        public int getParallelism() {
            return parallelism;
        }

        public void setParallelism(int parallelism) {
            this.parallelism = parallelism;
        }
    }
}
