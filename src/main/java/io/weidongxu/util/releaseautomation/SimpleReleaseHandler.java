package io.weidongxu.util.releaseautomation;

import java.io.IOException;

public class SimpleReleaseHandler extends ReleaseHandler {
    private final ReleaseHelper releaseHelper;
    protected SimpleReleaseHandler() throws IOException {
        this.releaseHelper = new ReleaseHelper.Builder().withReleaseConfirmation().withTagSelectionConfirmation().withPrMergeConfirmation().build();
    }

    @Override
    public void submit(Configure configure, Runnable onSuccess, Runnable onFailure) {
        try {
            releaseHelper.doRelease(configure);
            onSuccess.run();
        } catch (Exception e) {
            onFailure.run();
        }
    }
}
