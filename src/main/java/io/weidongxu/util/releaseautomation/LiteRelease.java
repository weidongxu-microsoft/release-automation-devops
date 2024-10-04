package io.weidongxu.util.releaseautomation;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

public class LiteRelease {
    public static void main(String[] args) throws Exception {
        ReleaseHandler.Options options = getConfigure(ReleaseHandler.Options.class);
        ReleaseHandler releaseHandler = ReleaseHandler.getReleaseHandler(options);

        if (options.isBatch()) {
            List<Configure> configures = parseConfigureList();
            CountDownLatch cdl = new CountDownLatch(configures.size());
            List<ReleaseResult> resultList = new CopyOnWriteArrayList<>();
            configures.forEach(configure -> releaseHandler.submit(configure, () -> {
                try {
                    resultList.add(ReleaseResult.success(configure));
                } finally {
                    cdl.countDown();
                }
            }, () -> {
                try {
                    resultList.add(ReleaseResult.failure(configure));
                } finally {
                    cdl.countDown();
                }
            }));
            cdl.await();
            System.out.println("Release result: " + resultList);
            System.exit(0);
        } else {
            Configure configure = getConfigure();
            releaseHandler.submit(configure, () -> System.exit(0), () -> System.exit(0));
        }
    }

    private static Configure getConfigure() throws IOException {
        return getConfigure(Configure.class);
    }

    private static List<Configure> parseConfigureList() throws IOException {
        Config config = getConfigure(Config.class);
        List<Configure> configs = config.getConfigs();
        configs.forEach(configure -> {
            // configuration validation for Swagger batch release
            if (configure.getSwagger() != null) {
                Objects.requireNonNull(configure.getTag(), "[Configuration Validation] 'tag' can't be null! Swagger: " + configure.getSwagger());
            }
        });
        return configs;
    }

    private static <T> T getConfigure(Class<T> clazz) throws IOException{
        T configure = null;
        Representer representer = new Representer(new DumperOptions());
        representer.getPropertyUtils().setSkipMissingProperties(true);
        Yaml yaml = new Yaml(representer);

        Path configInPath = Paths.get("configure.yml");
        if (Files.exists(configInPath)) {
            try (InputStream in = new FileInputStream(configInPath.toFile())) {
                configure = yaml.loadAs(in, clazz);
            }
        }
        if (configure == null) {
            try (InputStream in = LiteRelease.class.getResourceAsStream("/configure.yml")) {
                configure = yaml.loadAs(in, clazz);
            }
        }
        return configure;
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

    private static class ReleaseResult {
        Configure configure;
        boolean succeeded;

        public static ReleaseResult success(Configure configure) {
            ReleaseResult result = new ReleaseResult();
            result.configure = configure;
            result.succeeded = true;
            return result;
        }

        public static ReleaseResult failure(Configure configure) {
            ReleaseResult result = new ReleaseResult();
            result.configure = configure;
            result.succeeded = false;
            return result;
        }

        @Override
        public String toString() {
            return "ReleaseResult{" +
                "configure=" + configure +
                ", succeeded=" + succeeded +
                '}';
        }
    }
}
