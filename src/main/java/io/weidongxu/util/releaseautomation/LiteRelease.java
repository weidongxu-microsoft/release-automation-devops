package io.weidongxu.util.releaseautomation;

import java.util.List;

public class LiteRelease {
    public static void main(String[] args) throws Exception {
        ReleaseHandler releaseHandler = ReleaseHandler.getReleaseHandler();

        List<Configure> tasks = releaseHandler.getTaskList();

        List<ReleaseResult> results = releaseHandler.release(tasks);

        System.out.println("Release result: " + results);
        System.exit(0);
    }
}
