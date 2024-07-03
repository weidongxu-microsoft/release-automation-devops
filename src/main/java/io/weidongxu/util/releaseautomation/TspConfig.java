package io.weidongxu.util.releaseautomation;

import com.azure.core.http.HttpMethod;
import com.azure.core.http.HttpPipeline;
import com.azure.core.http.HttpRequest;
import com.azure.core.http.HttpResponse;
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

    private static final Yaml YAML;
    static {
        Representer representer = new Representer(new DumperOptions());

        representer.setPropertyUtils(new AnnotatedPropertyUtils());
        representer.getPropertyUtils().setSkipMissingProperties(true);
        YAML = new Yaml(representer);
    }

    public static TspConfig parse(GHRepository repository, HttpPipeline httpPipeline, String tspConfigUrl) throws Exception {
        String urlWithSha1;
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
        if (matcher.find()) {
            urlWithSha1 = matcher.replaceFirst("https://raw.githubusercontent.com/$1$3");
        } else {
            throw new IllegalArgumentException("Wrong tspConfigUrl: " + urlWithSha1);
        }
        urlWithSha1 = urlWithSha1.replaceFirst("https://github.com/(.*)/(tree|blob)(/.*)", "https://raw.githubusercontent.com/`$1");
        try (HttpResponse response = httpPipeline.send(new HttpRequest(HttpMethod.GET, urlWithSha1)).block()) {
            if (response.getStatusCode() == 200) {
                String tspConfig = response.getBodyAsString().block();
                Config config = YAML.loadAs(tspConfig, Config.class);
                config.validate();
                return new TspConfig(config.getParameters().getServiceDir().getDefaultDir(), config.getOptions().getJavaOptions().getPackageDir());
            } else {
                throw new IllegalArgumentException(response.getBodyAsString().block());
            }
        }
    }

    private final String service;
    private final String packageDir;

    private TspConfig(String service, String packageDir) {
        this.service = service;
        this.packageDir = packageDir;
    }

    public String getService() {
        return service;
    }

    public String getPackageDir() {
        return packageDir;
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
    }

    public static class JavaOptions {
        private String packageDir;

        @YamlProperty("package-dir")
        public String getPackageDir() {
            return packageDir;
        }

        public void setPackageDir(String packageDir) {
            this.packageDir = packageDir;
        }
    }
}
