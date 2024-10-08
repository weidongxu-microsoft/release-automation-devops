package io.weidongxu.util.releaseautomation.store;

import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class FileTaskStore implements TaskStore {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileTaskStore.class);

    private volatile boolean closed;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Map<String, ReleaseTask> taskMap = new HashMap<>();
    private final File logFile;
    private final ScheduledExecutorService fileSync;

    public FileTaskStore() {
        new File("logs").mkdirs();
        logFile = new File("logs", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) + ".log");
        fileSync = Executors.newScheduledThreadPool(1, new DefaultThreadFactory("file-sync"));
        fileSync.scheduleAtFixedRate(dumpToFile(), 30, 10, TimeUnit.SECONDS);
    }

    @Override
    public ReleaseTask create(ReleaseTask releaseTask) {
        lock.writeLock().lock();
        try {
            this.taskMap.put(releaseTask.getId(), releaseTask);
        } finally {
            lock.writeLock().unlock();
        }
        return releaseTask;
    }

    @Override
    public void update(ReleaseTask task) {
        lock.writeLock().lock();
        try {
            this.taskMap.put(task.getId(), task);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void close() {
        lock.writeLock().lock();
        try {
            if (!this.closed) {
                fileSync.shutdown();
                dumpToFile().run();
                this.closed = true;
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    private Runnable dumpToFile() {
        return () -> {
            lock.readLock().lock();

            try {
                if (!closed) {
                    StringBuilder sb = new StringBuilder();
                    taskMap.values().forEach(releaseTask -> {
                        sb.append(releaseTask);
                        sb.append("\n");
                    });
                    try (FileWriter fileWriter = new FileWriter(logFile)) {
                        fileWriter.write(sb.toString());
                        fileWriter.flush();
                    } catch (IOException e) {
                        LOGGER.error("file write failed!", e);
                    }
                }
            } finally {
                lock.readLock().unlock();
            }
        };
    }
}
