package io.weidongxu.util.releaseautomation;

import com.azure.core.http.HttpMethod;
import com.azure.core.http.HttpPipeline;
import com.azure.core.http.HttpRequest;
import com.azure.core.http.HttpResponse;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VersionConfigure {

    public static class SdkVersion {
        private final String dependencyVersion; // released version
        private final String currentVersion;    // version to be released

        public SdkVersion(String dependencyVersion, String currentVersion) {
            this.dependencyVersion = dependencyVersion;
            this.currentVersion = currentVersion;
        }

        public String getDependencyVersion() {
            return dependencyVersion;
        }

        public String getCurrentVersion() {
            return currentVersion;
        }

        public String getCurrentVersionAsStable() {
            int betaLocation = currentVersion.indexOf("-beta.");
            return betaLocation >= 0 ? currentVersion.substring(0, betaLocation) : currentVersion;
        }

        public boolean isStableReleased() {
            // after stable release, currentVersion would at least be 1.1.0-beta.1
            return !currentVersion.startsWith("1.0.0");
        }
    }

    public static Optional<SdkVersion> parseVersion(HttpPipeline httpPipeline, String sdk) {
        SdkVersion sdkVersion = null;
        final String versionClientUrl = "https://raw.githubusercontent.com/Azure/azure-sdk-for-java/main/eng/versioning/version_client.txt";
        HttpRequest request = new HttpRequest(HttpMethod.GET, versionClientUrl);
        HttpResponse response = httpPipeline.send(request).block();
        if (response.getStatusCode() == 200) {
            String versionClient = response.getBodyAsString().block();
            response.close();

            String sdkArtifact = "azure-resourcemanager-" + sdk;

            Matcher matcher = Pattern.compile("^com.azure.resourcemanager:" + sdkArtifact +";(.*);(.*)$", Pattern.MULTILINE).matcher(versionClient);
            if (matcher.find()) {
                String dependencyVersion = matcher.group(1);
                String currentVersion = matcher.group(2);
                sdkVersion = new SdkVersion(dependencyVersion, currentVersion);
            }
        }
        return Optional.ofNullable(sdkVersion);
    }
}
