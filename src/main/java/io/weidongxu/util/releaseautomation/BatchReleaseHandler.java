package io.weidongxu.util.releaseautomation;

import io.netty.util.concurrent.DefaultThreadFactory;
import io.weidongxu.util.releaseautomation.store.FileTaskStore;
import io.weidongxu.util.releaseautomation.store.TaskStore;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BatchReleaseHandler extends ReleaseHandler {
    private final ExecutorService executor;
    private final ReleaseHelper releaseHelper;
    private final TaskStore taskStore;

    protected BatchReleaseHandler(Options options) throws IOException {
        this.executor = Executors.newFixedThreadPool(options.getParallelism(), new DefaultThreadFactory("batch-release-handler"));
        this.taskStore = new FileTaskStore();
        this.releaseHelper = new ReleaseHelper.Builder().withTaskStore(taskStore).build();
    }

    @Override
    public List<Configure> getTaskList() throws IOException {
        Config config = ConfigureHelper.getConfigure(Config.class);
        List<Configure> configs = config.getConfigs();
        configs.forEach(configure -> {
            // configuration validation for Swagger batch release
            if (configure.getSwagger() != null) {
                Objects.requireNonNull(configure.getTag(), "[Configuration Validation] 'tag' can't be null! Swagger: " + configure.getSwagger());
            }
        });
        return configs;
    }

    @Override
    public List<ReleaseResult> release(List<Configure> tasks) throws Exception {
        CountDownLatch cdl = new CountDownLatch(tasks.size());
        List<ReleaseResult> resultList = new CopyOnWriteArrayList<>();
        tasks.forEach(task -> executor.submit(() -> {
            try {
                releaseHelper.doRelease(task);
                resultList.add(ReleaseResult.success(task));
            } catch (Exception e) {
                resultList.add(ReleaseResult.failure(task, e.getMessage()));
            } finally {
                cdl.countDown();
            }
        }));
        cdl.await();
        taskStore.close();
        return resultList;
    }

    public static class Config {
        private List<Configure> configs;

        public List<Configure> getConfigs() {
            return configs;
        }

        public void setConfigs(List<Configure> configs) {
            this.configs = configs;
        }
    }
}
