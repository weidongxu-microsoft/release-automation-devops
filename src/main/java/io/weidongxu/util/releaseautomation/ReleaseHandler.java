package io.weidongxu.util.releaseautomation;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public abstract class ReleaseHandler {

    public static ReleaseHandler getReleaseHandler() throws IOException {
        Options options = ConfigureHelper.getConfigure(Options.class);
        if (options.isBatch()) {
            System.out.println("mode: batch");
            return new BatchReleaseHandler(options);
        } else {
            System.out.println("mode: simple");
            return new SimpleReleaseHandler();
        }
    }

    public abstract List<Configure> getTaskList() throws IOException;

    public abstract List<ReleaseResult> release(List<Configure> tasks) throws Exception;

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
