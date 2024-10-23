package io.weidongxu.util.releaseautomation;

import com.azure.core.http.HttpMethod;
import com.azure.core.http.HttpPipeline;
import com.azure.core.http.HttpRequest;
import com.azure.core.http.HttpResponse;
import com.azure.core.util.CoreUtils;
import io.weidongxu.util.releaseautomation.util.AnnotatedPropertyUtils;
import io.weidongxu.util.releaseautomation.util.YamlProperty;
import org.kohsuke.github.GHRepository;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TspConfig {
    private static final Pattern MAIN_URL = Pattern.compile("^https://github.com/(?<repo>[^/]*/azure-rest-api-specs(-pr)?)/blob/(main)/(?<path>.*)/tspconfig.yaml$");
    private static final Pattern SHA1_URL = Pattern.compile("^https://github.com/(?<repo>[^/]*/azure-rest-api-specs(-pr)?)/blob/(?<commit>[0-9a-f]{40})/(?<path>.*)/tspconfig.yaml$");
    private static final Pattern GITHUB_URL = Pattern.compile("https://github.com/(.*)/(tree|blob)(/.*)");
    private static final Pattern SERVICE_DIR = Pattern.compile("^sdk/([\\w_-]+)$");

    private static final Yaml YAML;
    static {
        Representer representer = new Representer(new DumperOptions());

        representer.setPropertyUtils(new AnnotatedPropertyUtils());
        representer.getPropertyUtils().setSkipMissingProperties(true);
        YAML = new Yaml(representer);
    }

    public String getUrl() {
        return url;
    }

    public String getService() {
        return service;
    }

    public String getPackageDir() {
        return packageDir;
    }

    public static TspConfig parse(GHRepository repository, HttpPipeline httpPipeline, String tspConfigUrl) throws Exception {
        String urlWithSha1; // url for tsp-client
        if (MAIN_URL.matcher(tspConfigUrl).matches()) {
            String sha1 = repository.getBranch("main").getSHA1();
            urlWithSha1 = tspConfigUrl.replace("/main/", String.format("/%s/", sha1));
        } else {
            if (SHA1_URL.matcher(tspConfigUrl).matches()) {
                urlWithSha1 = tspConfigUrl;
            } else {
                throw new IllegalArgumentException(String.format("Illegal tspConfig url format: %s", tspConfigUrl));
            }
        }
        Matcher matcher = GITHUB_URL.matcher(urlWithSha1);
        String githubRawUrl; // url for getting github file content
        if (matcher.find()) {
            githubRawUrl = matcher.replaceFirst("https://raw.githubusercontent.com/$1$3");
        } else {
            throw new IllegalArgumentException("Wrong tspConfigUrl: " + urlWithSha1);
        }
        try (HttpResponse response = httpPipeline.send(new HttpRequest(HttpMethod.GET, githubRawUrl)).block()) {
            if (response.getStatusCode() == 200) {
                String tspConfig = response.getBodyAsString().block();
                Config config = YAML.loadAs(tspConfig, Config.class);
                config.validate();
                TspConfig result = new TspConfig()
                        .withUrl(urlWithSha1)
                        .withPackageDir(config.getOptions().getJavaOptions().getPackageDir());
                Matcher serviceDirMatcher = SERVICE_DIR.matcher(CoreUtils.isNullOrEmpty(config.getOptions().getJavaOptions().getServiceDir()) ? config.getParameters().getServiceDir().getDefaultDir() : config.getOptions().getJavaOptions().getServiceDir());
                if (serviceDirMatcher.matches()) {
                    result.withService(serviceDirMatcher.group(1));
                }
                return result;
            } else {
                throw new IllegalArgumentException(response.getBodyAsString().block());
            }
        }
    }

    private String service;
    private String packageDir;
    private String url;

    private TspConfig withService(String service) {
        this.service = service;
        return this;
    }

    private TspConfig withPackageDir(String packageDir) {
        this.packageDir = packageDir;
        return this;
    }

    private TspConfig withUrl(String url) {
        this.url = url;
        return this;
    }

    public static class Config {
        private Parameters parameters;
        private Options options;

        public Parameters getParameters() {
            return parameters;
        }

        public Options getOptions() {
            return options;
        }

        public void setOptions(Options options) {
            this.options = options;
        }

        public void setParameters(Parameters parameters) {
            this.parameters = parameters;
        }

        public void validate() {
            if (parameters == null) {
                throw new IllegalArgumentException("\"parameters\" null");
            }
            parameters.validate();
            if (options == null) {
                throw new IllegalArgumentException("\"options\" null");
            }
            options.validate();
        }
    }

    public static class Parameters {
        private ServiceDir serviceDir;

        @YamlProperty("service-dir")
        public ServiceDir getServiceDir() {
            return serviceDir;
        }

        public void setServiceDir(ServiceDir serviceDir) {
            this.serviceDir = serviceDir;
        }

        public void validate() {
            if (serviceDir == null) {
                throw new IllegalArgumentException("\"service-dir\" null");
            }
            serviceDir.validate();
        }
    }

    public static class ServiceDir {
        private String defaultDir;

        @YamlProperty("default")
        public String getDefaultDir() {
            return defaultDir;
        }

        public void setDefaultDir(String defaultDir) {
            this.defaultDir = defaultDir;
        }

        public void validate() {
            if (CoreUtils.isNullOrEmpty(defaultDir)) {
                throw new IllegalArgumentException("\"service-dir\" null");
            }
            if (!SERVICE_DIR.matcher(defaultDir).matches()) {
                throw new IllegalArgumentException("Illegal \"service-dir\": " + defaultDir);
            }
        }
    }

    public static class Options {
        private JavaOptions javaOptions;

        @YamlProperty("@azure-tools/typespec-java")
        public JavaOptions getJavaOptions() {
            return javaOptions;
        }

        public void setJavaOptions(JavaOptions javaOptions) {
            this.javaOptions = javaOptions;
        }

        public void validate() {
            if (javaOptions == null) {
                throw new IllegalArgumentException("\"options\" null");
            }
            javaOptions.validate();
        }
    }

    public static class JavaOptions {
        private String packageDir;
        private String serviceDir;

        @YamlProperty("package-dir")
        public String getPackageDir() {
            return packageDir;
        }

        @YamlProperty("service-dir")
        public String getServiceDir() {
            return serviceDir;
        }

        public void setPackageDir(String packageDir) {
            this.packageDir = packageDir;
        }

        public void setServiceDir(String serviceDir) {
            this.serviceDir = serviceDir;
        }

        public void validate() {
            if (CoreUtils.isNullOrEmpty(packageDir)) {
                throw new IllegalArgumentException("\"package-dir\" null");
            }
        }
    }
}
