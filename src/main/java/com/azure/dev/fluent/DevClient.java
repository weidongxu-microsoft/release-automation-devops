// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.fluent;

import com.azure.core.http.HttpPipeline;
import java.time.Duration;

/**
 * The interface for DevClient class.
 */
public interface DevClient {
    /**
     * Gets server parameter.
     * 
     * @return the endpoint value.
     */
    String getEndpoint();

    /**
     * Gets Api Version.
     * 
     * @return the apiVersion value.
     */
    String getApiVersion();

    /**
     * Gets The HTTP pipeline to send requests through.
     * 
     * @return the httpPipeline value.
     */
    HttpPipeline getHttpPipeline();

    /**
     * Gets The default poll interval for long-running operation.
     * 
     * @return the defaultPollInterval value.
     */
    Duration getDefaultPollInterval();

    /**
     * Gets the ControllersClient object to access its operations.
     * 
     * @return the ControllersClient object.
     */
    ControllersClient getControllers();

    /**
     * Gets the ResourceUsagesClient object to access its operations.
     * 
     * @return the ResourceUsagesClient object.
     */
    ResourceUsagesClient getResourceUsages();

    /**
     * Gets the HistoriesClient object to access its operations.
     * 
     * @return the HistoriesClient object.
     */
    HistoriesClient getHistories();

    /**
     * Gets the BadgesClient object to access its operations.
     * 
     * @return the BadgesClient object.
     */
    BadgesClient getBadges();

    /**
     * Gets the AuthorizedresourcesClient object to access its operations.
     * 
     * @return the AuthorizedresourcesClient object.
     */
    AuthorizedresourcesClient getAuthorizedresources();

    /**
     * Gets the BuildsClient object to access its operations.
     * 
     * @return the BuildsClient object.
     */
    BuildsClient getBuilds();

    /**
     * Gets the AttachmentsClient object to access its operations.
     * 
     * @return the AttachmentsClient object.
     */
    AttachmentsClient getAttachments();

    /**
     * Gets the ArtifactsClient object to access its operations.
     * 
     * @return the ArtifactsClient object.
     */
    ArtifactsClient getArtifacts();

    /**
     * Gets the PropertiesClient object to access its operations.
     * 
     * @return the PropertiesClient object.
     */
    PropertiesClient getProperties();

    /**
     * Gets the ReportsClient object to access its operations.
     * 
     * @return the ReportsClient object.
     */
    ReportsClient getReports();

    /**
     * Gets the StagesClient object to access its operations.
     * 
     * @return the StagesClient object.
     */
    StagesClient getStages();

    /**
     * Gets the TagsClient object to access its operations.
     * 
     * @return the TagsClient object.
     */
    TagsClient getTags();

    /**
     * Gets the TimelinesClient object to access its operations.
     * 
     * @return the TimelinesClient object.
     */
    TimelinesClient getTimelines();

    /**
     * Gets the DefinitionsClient object to access its operations.
     * 
     * @return the DefinitionsClient object.
     */
    DefinitionsClient getDefinitions();

    /**
     * Gets the MetricsClient object to access its operations.
     * 
     * @return the MetricsClient object.
     */
    MetricsClient getMetrics();

    /**
     * Gets the ResourcesClient object to access its operations.
     * 
     * @return the ResourcesClient object.
     */
    ResourcesClient getResources();

    /**
     * Gets the YamlsClient object to access its operations.
     * 
     * @return the YamlsClient object.
     */
    YamlsClient getYamls();

    /**
     * Gets the TemplatesClient object to access its operations.
     * 
     * @return the TemplatesClient object.
     */
    TemplatesClient getTemplates();

    /**
     * Gets the FoldersClient object to access its operations.
     * 
     * @return the FoldersClient object.
     */
    FoldersClient getFolders();

    /**
     * Gets the GeneralSettingsClient object to access its operations.
     * 
     * @return the GeneralSettingsClient object.
     */
    GeneralSettingsClient getGeneralSettings();

    /**
     * Gets the LatestsClient object to access its operations.
     * 
     * @return the LatestsClient object.
     */
    LatestsClient getLatests();

    /**
     * Gets the OptionsClient object to access its operations.
     * 
     * @return the OptionsClient object.
     */
    OptionsClient getOptions();

    /**
     * Gets the RetentionsClient object to access its operations.
     * 
     * @return the RetentionsClient object.
     */
    RetentionsClient getRetentions();

    /**
     * Gets the LeasesClient object to access its operations.
     * 
     * @return the LeasesClient object.
     */
    LeasesClient getLeases();

    /**
     * Gets the SettingsClient object to access its operations.
     * 
     * @return the SettingsClient object.
     */
    SettingsClient getSettings();

    /**
     * Gets the StatusClient object to access its operations.
     * 
     * @return the StatusClient object.
     */
    StatusClient getStatus();

    /**
     * Gets the SourceProvidersClient object to access its operations.
     * 
     * @return the SourceProvidersClient object.
     */
    SourceProvidersClient getSourceProviders();

    /**
     * Gets the PipelinesClient object to access its operations.
     * 
     * @return the PipelinesClient object.
     */
    PipelinesClient getPipelines();

    /**
     * Gets the PreviewsClient object to access its operations.
     * 
     * @return the PreviewsClient object.
     */
    PreviewsClient getPreviews();

    /**
     * Gets the RunsClient object to access its operations.
     * 
     * @return the RunsClient object.
     */
    RunsClient getRuns();

    /**
     * Gets the LogsClient object to access its operations.
     * 
     * @return the LogsClient object.
     */
    LogsClient getLogs();
}
