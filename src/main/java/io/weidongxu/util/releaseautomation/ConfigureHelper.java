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

public class ConfigureHelper {

    private static final Yaml YAML;

    static {
        Representer representer = new Representer(new DumperOptions());
        representer.getPropertyUtils().setSkipMissingProperties(true);
        YAML = new Yaml(representer);
    }

    public static <T> T getConfigure(Class<T> configureClazz) throws IOException {
        T configure = null;

        Path configInPath = Paths.get("configure.yml");
        if (Files.exists(configInPath)) {
            try (InputStream in = new FileInputStream(configInPath.toFile())) {
                configure = YAML.loadAs(in, configureClazz);
            }
        }
        if (configure == null) {
            try (InputStream in = LiteRelease.class.getResourceAsStream("/configure.yml")) {
                configure = YAML.loadAs(in, configureClazz);
            }
        }
        return configure;
    }
}
