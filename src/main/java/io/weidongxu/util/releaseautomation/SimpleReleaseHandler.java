package io.weidongxu.util.releaseautomation;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleReleaseHandler extends ReleaseHandler {
    private final ReleaseHelper releaseHelper;
    protected SimpleReleaseHandler() throws IOException {
        this.releaseHelper = new ReleaseHelper.Builder().withReleaseConfirmation().withTagSelectionConfirmation().withPrMergeConfirmation().build();
    }

    @Override
    public List<Configure> getTaskList() throws IOException {
        return Collections.singletonList(ConfigureHelper.getConfigure(Configure.class));
    }

    @Override
    public List<ReleaseResult> release(List<Configure> tasks) throws Exception {
        return tasks.stream().map(task -> {
            try {
                releaseHelper.doRelease(task);
                return ReleaseResult.success(task);
            } catch (Exception e) {
                return ReleaseResult.failure(task, e.getMessage());
            }
        }).collect(Collectors.toList());
    }
}
