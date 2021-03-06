// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev;

import com.azure.core.credential.TokenCredential;
import com.azure.core.http.HttpClient;
import com.azure.core.http.HttpPipeline;
import com.azure.core.http.HttpPipelineBuilder;
import com.azure.core.http.policy.AddDatePolicy;
import com.azure.core.http.policy.HttpLogOptions;
import com.azure.core.http.policy.HttpLoggingPolicy;
import com.azure.core.http.policy.HttpPipelinePolicy;
import com.azure.core.http.policy.HttpPolicyProviders;
import com.azure.core.http.policy.RequestIdPolicy;
import com.azure.core.http.policy.RetryPolicy;
import com.azure.core.http.policy.UserAgentPolicy;
import com.azure.core.management.http.policy.ArmChallengeAuthenticationPolicy;
import com.azure.core.management.profile.AzureProfile;
import com.azure.core.util.Configuration;
import com.azure.core.util.logging.ClientLogger;
import com.azure.dev.fluent.DevClient;
import com.azure.dev.implementation.ArtifactsImpl;
import com.azure.dev.implementation.AttachmentsImpl;
import com.azure.dev.implementation.AuthorizedresourcesImpl;
import com.azure.dev.implementation.BadgesImpl;
import com.azure.dev.implementation.BuildsImpl;
import com.azure.dev.implementation.ControllersImpl;
import com.azure.dev.implementation.DefinitionsImpl;
import com.azure.dev.implementation.DevClientBuilder;
import com.azure.dev.implementation.FoldersImpl;
import com.azure.dev.implementation.GeneralSettingsImpl;
import com.azure.dev.implementation.LatestsImpl;
import com.azure.dev.implementation.LeasesImpl;
import com.azure.dev.implementation.LogsImpl;
import com.azure.dev.implementation.MetricsImpl;
import com.azure.dev.implementation.OptionsImpl;
import com.azure.dev.implementation.PipelinesImpl;
import com.azure.dev.implementation.PropertiesImpl;
import com.azure.dev.implementation.ReportsImpl;
import com.azure.dev.implementation.ResourceUsagesImpl;
import com.azure.dev.implementation.ResourcesImpl;
import com.azure.dev.implementation.RetentionsImpl;
import com.azure.dev.implementation.RunsImpl;
import com.azure.dev.implementation.SettingsImpl;
import com.azure.dev.implementation.SourceProvidersImpl;
import com.azure.dev.implementation.StagesImpl;
import com.azure.dev.implementation.StatusImpl;
import com.azure.dev.implementation.TagsImpl;
import com.azure.dev.implementation.TemplatesImpl;
import com.azure.dev.implementation.TimelinesImpl;
import com.azure.dev.models.Artifacts;
import com.azure.dev.models.Attachments;
import com.azure.dev.models.Authorizedresources;
import com.azure.dev.models.Badges;
import com.azure.dev.models.Builds;
import com.azure.dev.models.Controllers;
import com.azure.dev.models.Definitions;
import com.azure.dev.models.Folders;
import com.azure.dev.models.GeneralSettings;
import com.azure.dev.models.Latests;
import com.azure.dev.models.Leases;
import com.azure.dev.models.Logs;
import com.azure.dev.models.Metrics;
import com.azure.dev.models.Options;
import com.azure.dev.models.Pipelines;
import com.azure.dev.models.Properties;
import com.azure.dev.models.Reports;
import com.azure.dev.models.ResourceUsages;
import com.azure.dev.models.Resources;
import com.azure.dev.models.Retentions;
import com.azure.dev.models.Runs;
import com.azure.dev.models.Settings;
import com.azure.dev.models.SourceProviders;
import com.azure.dev.models.Stages;
import com.azure.dev.models.Status;
import com.azure.dev.models.Tags;
import com.azure.dev.models.Templates;
import com.azure.dev.models.Timelines;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Entry point to DevManager. */
public final class DevManager {
    private Controllers controllers;

    private ResourceUsages resourceUsages;

    private Badges badges;

    private Authorizedresources authorizedresources;

    private Builds builds;

    private Attachments attachments;

    private Artifacts artifacts;

    private Properties properties;

    private Reports reports;

    private Stages stages;

    private Tags tags;

    private Timelines timelines;

    private Definitions definitions;

    private Metrics metrics;

    private Resources resources;

    private Templates templates;

    private Folders folders;

    private GeneralSettings generalSettings;

    private Latests latests;

    private Options options;

    private Retentions retentions;

    private Leases leases;

    private Settings settings;

    private Status status;

    private SourceProviders sourceProviders;

    private Pipelines pipelines;

    private Runs runs;

    private Logs logs;

    private final DevClient clientObject;

    private DevManager(HttpPipeline httpPipeline, AzureProfile profile, Duration defaultPollInterval) {
        Objects.requireNonNull(httpPipeline, "'httpPipeline' cannot be null.");
        Objects.requireNonNull(profile, "'profile' cannot be null.");
        this.clientObject =
            new DevClientBuilder()
                .pipeline(httpPipeline)
//                .endpoint(profile.getEnvironment().getResourceManagerEndpoint())
//                .defaultPollInterval(defaultPollInterval)
                .buildClient();
    }

    /**
     * Creates an instance of Dev service API entry point.
     *
     * @param credential the credential to use.
     * @param profile the Azure profile for client.
     * @return the Dev service API instance.
     */
    public static DevManager authenticate(TokenCredential credential, AzureProfile profile) {
        Objects.requireNonNull(credential, "'credential' cannot be null.");
        Objects.requireNonNull(profile, "'profile' cannot be null.");
        return configure().authenticate(credential, profile);
    }

    /**
     * Gets a Configurable instance that can be used to create DevManager with optional configuration.
     *
     * @return the Configurable instance allowing configurations.
     */
    public static Configurable configure() {
        return new DevManager.Configurable();
    }

    /** The Configurable allowing configurations to be set. */
    public static final class Configurable {
        private final ClientLogger logger = new ClientLogger(Configurable.class);

        private HttpClient httpClient;
        private HttpLogOptions httpLogOptions;
        private final List<HttpPipelinePolicy> policies = new ArrayList<>();
        private final List<String> scopes = new ArrayList<>();
        private RetryPolicy retryPolicy;
        private Duration defaultPollInterval;

        private Configurable() {
        }

        /**
         * Sets the http client.
         *
         * @param httpClient the HTTP client.
         * @return the configurable object itself.
         */
        public Configurable withHttpClient(HttpClient httpClient) {
            this.httpClient = Objects.requireNonNull(httpClient, "'httpClient' cannot be null.");
            return this;
        }

        /**
         * Sets the logging options to the HTTP pipeline.
         *
         * @param httpLogOptions the HTTP log options.
         * @return the configurable object itself.
         */
        public Configurable withLogOptions(HttpLogOptions httpLogOptions) {
            this.httpLogOptions = Objects.requireNonNull(httpLogOptions, "'httpLogOptions' cannot be null.");
            return this;
        }

        /**
         * Adds the pipeline policy to the HTTP pipeline.
         *
         * @param policy the HTTP pipeline policy.
         * @return the configurable object itself.
         */
        public Configurable withPolicy(HttpPipelinePolicy policy) {
            this.policies.add(Objects.requireNonNull(policy, "'policy' cannot be null."));
            return this;
        }

        /**
         * Adds the scope to permission sets.
         *
         * @param scope the scope.
         * @return the configurable object itself.
         */
        public Configurable withScope(String scope) {
            this.scopes.add(Objects.requireNonNull(scope, "'scope' cannot be null."));
            return this;
        }

        /**
         * Sets the retry policy to the HTTP pipeline.
         *
         * @param retryPolicy the HTTP pipeline retry policy.
         * @return the configurable object itself.
         */
        public Configurable withRetryPolicy(RetryPolicy retryPolicy) {
            this.retryPolicy = Objects.requireNonNull(retryPolicy, "'retryPolicy' cannot be null.");
            return this;
        }

        /**
         * Sets the default poll interval, used when service does not provide "Retry-After" header.
         *
         * @param defaultPollInterval the default poll interval.
         * @return the configurable object itself.
         */
        public Configurable withDefaultPollInterval(Duration defaultPollInterval) {
            this.defaultPollInterval = Objects.requireNonNull(defaultPollInterval, "'retryPolicy' cannot be null.");
            if (this.defaultPollInterval.isNegative()) {
                throw logger.logExceptionAsError(new IllegalArgumentException("'httpPipeline' cannot be negative"));
            }
            return this;
        }

        /**
         * Creates an instance of Dev service API entry point.
         *
         * @param credential the credential to use.
         * @param profile the Azure profile for client.
         * @return the Dev service API instance.
         */
        public DevManager authenticate(TokenCredential credential, AzureProfile profile) {
            Objects.requireNonNull(credential, "'credential' cannot be null.");
            Objects.requireNonNull(profile, "'profile' cannot be null.");

            StringBuilder userAgentBuilder = new StringBuilder();
            userAgentBuilder
                .append("azsdk-java")
                .append("-")
                .append("com.azure.dev")
                .append("/")
                .append("1.0.0-beta.1");
            if (!Configuration.getGlobalConfiguration().get("AZURE_TELEMETRY_DISABLED", false)) {
                userAgentBuilder
                    .append(" (")
                    .append(Configuration.getGlobalConfiguration().get("java.version"))
                    .append("; ")
                    .append(Configuration.getGlobalConfiguration().get("os.name"))
                    .append("; ")
                    .append(Configuration.getGlobalConfiguration().get("os.version"))
                    .append("; auto-generated)");
            } else {
                userAgentBuilder.append(" (auto-generated)");
            }

            if (scopes.isEmpty()) {
                scopes.add(profile.getEnvironment().getManagementEndpoint() + "/.default");
            }
            if (retryPolicy == null) {
                retryPolicy = new RetryPolicy("Retry-After", ChronoUnit.SECONDS);
            }
            List<HttpPipelinePolicy> policies = new ArrayList<>();
            policies.add(new UserAgentPolicy(userAgentBuilder.toString()));
            policies.add(new RequestIdPolicy());
            HttpPolicyProviders.addBeforeRetryPolicies(policies);
            policies.add(retryPolicy);
            policies.add(new AddDatePolicy());
            policies.add(new ArmChallengeAuthenticationPolicy(credential, scopes.toArray(new String[0])));
            policies.addAll(this.policies);
            HttpPolicyProviders.addAfterRetryPolicies(policies);
            policies.add(new HttpLoggingPolicy(httpLogOptions));
            HttpPipeline httpPipeline =
                new HttpPipelineBuilder()
                    .httpClient(httpClient)
                    .policies(policies.toArray(new HttpPipelinePolicy[0]))
                    .build();
            return new DevManager(httpPipeline, profile, defaultPollInterval);
        }
    }

    /** @return Resource collection API of Controllers. */
    public Controllers controllers() {
        if (this.controllers == null) {
            this.controllers = new ControllersImpl(clientObject.getControllers(), this);
        }
        return controllers;
    }

    /** @return Resource collection API of ResourceUsages. */
    public ResourceUsages resourceUsages() {
        if (this.resourceUsages == null) {
            this.resourceUsages = new ResourceUsagesImpl(clientObject.getResourceUsages(), this);
        }
        return resourceUsages;
    }

    /** @return Resource collection API of Badges. */
    public Badges badges() {
        if (this.badges == null) {
            this.badges = new BadgesImpl(clientObject.getBadges(), this);
        }
        return badges;
    }

    /** @return Resource collection API of Authorizedresources. */
    public Authorizedresources authorizedresources() {
        if (this.authorizedresources == null) {
            this.authorizedresources = new AuthorizedresourcesImpl(clientObject.getAuthorizedresources(), this);
        }
        return authorizedresources;
    }

    /** @return Resource collection API of Builds. */
    public Builds builds() {
        if (this.builds == null) {
            this.builds = new BuildsImpl(clientObject.getBuilds(), this);
        }
        return builds;
    }

    /** @return Resource collection API of Attachments. */
    public Attachments attachments() {
        if (this.attachments == null) {
            this.attachments = new AttachmentsImpl(clientObject.getAttachments(), this);
        }
        return attachments;
    }

    /** @return Resource collection API of Artifacts. */
    public Artifacts artifacts() {
        if (this.artifacts == null) {
            this.artifacts = new ArtifactsImpl(clientObject.getArtifacts(), this);
        }
        return artifacts;
    }

    /** @return Resource collection API of Properties. */
    public Properties properties() {
        if (this.properties == null) {
            this.properties = new PropertiesImpl(clientObject.getProperties(), this);
        }
        return properties;
    }

    /** @return Resource collection API of Reports. */
    public Reports reports() {
        if (this.reports == null) {
            this.reports = new ReportsImpl(clientObject.getReports(), this);
        }
        return reports;
    }

    /** @return Resource collection API of Stages. */
    public Stages stages() {
        if (this.stages == null) {
            this.stages = new StagesImpl(clientObject.getStages(), this);
        }
        return stages;
    }

    /** @return Resource collection API of Tags. */
    public Tags tags() {
        if (this.tags == null) {
            this.tags = new TagsImpl(clientObject.getTags(), this);
        }
        return tags;
    }

    /** @return Resource collection API of Timelines. */
    public Timelines timelines() {
        if (this.timelines == null) {
            this.timelines = new TimelinesImpl(clientObject.getTimelines(), this);
        }
        return timelines;
    }

    /** @return Resource collection API of Definitions. */
    public Definitions definitions() {
        if (this.definitions == null) {
            this.definitions = new DefinitionsImpl(clientObject.getDefinitions(), this);
        }
        return definitions;
    }

    /** @return Resource collection API of Metrics. */
    public Metrics metrics() {
        if (this.metrics == null) {
            this.metrics = new MetricsImpl(clientObject.getMetrics(), this);
        }
        return metrics;
    }

    /** @return Resource collection API of Resources. */
    public Resources resources() {
        if (this.resources == null) {
            this.resources = new ResourcesImpl(clientObject.getResources(), this);
        }
        return resources;
    }

    /** @return Resource collection API of Templates. */
    public Templates templates() {
        if (this.templates == null) {
            this.templates = new TemplatesImpl(clientObject.getTemplates(), this);
        }
        return templates;
    }

    /** @return Resource collection API of Folders. */
    public Folders folders() {
        if (this.folders == null) {
            this.folders = new FoldersImpl(clientObject.getFolders(), this);
        }
        return folders;
    }

    /** @return Resource collection API of GeneralSettings. */
    public GeneralSettings generalSettings() {
        if (this.generalSettings == null) {
            this.generalSettings = new GeneralSettingsImpl(clientObject.getGeneralSettings(), this);
        }
        return generalSettings;
    }

    /** @return Resource collection API of Latests. */
    public Latests latests() {
        if (this.latests == null) {
            this.latests = new LatestsImpl(clientObject.getLatests(), this);
        }
        return latests;
    }

    /** @return Resource collection API of Options. */
    public Options options() {
        if (this.options == null) {
            this.options = new OptionsImpl(clientObject.getOptions(), this);
        }
        return options;
    }

    /** @return Resource collection API of Retentions. */
    public Retentions retentions() {
        if (this.retentions == null) {
            this.retentions = new RetentionsImpl(clientObject.getRetentions(), this);
        }
        return retentions;
    }

    /** @return Resource collection API of Leases. */
    public Leases leases() {
        if (this.leases == null) {
            this.leases = new LeasesImpl(clientObject.getLeases(), this);
        }
        return leases;
    }

    /** @return Resource collection API of Settings. */
    public Settings settings() {
        if (this.settings == null) {
            this.settings = new SettingsImpl(clientObject.getSettings(), this);
        }
        return settings;
    }

    /** @return Resource collection API of Status. */
    public Status status() {
        if (this.status == null) {
            this.status = new StatusImpl(clientObject.getStatus(), this);
        }
        return status;
    }

    /** @return Resource collection API of SourceProviders. */
    public SourceProviders sourceProviders() {
        if (this.sourceProviders == null) {
            this.sourceProviders = new SourceProvidersImpl(clientObject.getSourceProviders(), this);
        }
        return sourceProviders;
    }

    /** @return Resource collection API of Pipelines. */
    public Pipelines pipelines() {
        if (this.pipelines == null) {
            this.pipelines = new PipelinesImpl(clientObject.getPipelines(), this);
        }
        return pipelines;
    }

    /** @return Resource collection API of Runs. */
    public Runs runs() {
        if (this.runs == null) {
            this.runs = new RunsImpl(clientObject.getRuns(), this);
        }
        return runs;
    }

    /** @return Resource collection API of Logs. */
    public Logs logs() {
        if (this.logs == null) {
            this.logs = new LogsImpl(clientObject.getLogs(), this);
        }
        return logs;
    }

    /**
     * @return Wrapped service client DevClient providing direct access to the underlying auto-generated API
     *     implementation, based on Azure REST API.
     */
    public DevClient serviceClient() {
        return this.clientObject;
    }
}
