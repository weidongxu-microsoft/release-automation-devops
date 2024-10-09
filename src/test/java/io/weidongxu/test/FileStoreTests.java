package io.weidongxu.test;

import io.weidongxu.util.releaseautomation.Configure;
import io.weidongxu.util.releaseautomation.store.FileTaskStore;
import io.weidongxu.util.releaseautomation.store.LiteReleaseState;
import io.weidongxu.util.releaseautomation.store.ReleaseTask;
import org.junit.jupiter.api.Test;

public class FileStoreTests {

    @Test
    public void testFileStore() {
        FileTaskStore taskStore = new FileTaskStore();
        Configure configure = new Configure();
        configure.setSwagger("testSwagger");
        configure.setTag("test-tag");
        ReleaseTask task = new ReleaseTask(configure);
        task.setState(LiteReleaseState.VERSION_PR_MERGED);
        taskStore.create(task);
        taskStore.close();
    }
}
