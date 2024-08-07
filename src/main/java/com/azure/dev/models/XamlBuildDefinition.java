// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.models;

import com.azure.dev.fluent.models.XamlBuildDefinitionInner;
import java.time.OffsetDateTime;

/**
 * An immutable client-side representation of XamlBuildDefinition.
 */
public interface XamlBuildDefinition {
    /**
     * Gets the createdDate property: The date this version of the definition was created.
     * 
     * @return the createdDate value.
     */
    OffsetDateTime createdDate();

    /**
     * Gets the id property: The ID of the referenced definition.
     * 
     * @return the id value.
     */
    Integer id();

    /**
     * Gets the name property: The name of the referenced definition.
     * 
     * @return the name value.
     */
    String name();

    /**
     * Gets the path property: The folder path of the definition.
     * 
     * @return the path value.
     */
    String path();

    /**
     * Gets the project property: A reference to the project.
     * 
     * @return the project value.
     */
    TeamProjectReference project();

    /**
     * Gets the queueStatus property: A value that indicates whether builds can be queued against this definition.
     * 
     * @return the queueStatus value.
     */
    DefinitionQueueStatus queueStatus();

    /**
     * Gets the revision property: The definition revision number.
     * 
     * @return the revision value.
     */
    Integer revision();

    /**
     * Gets the type property: The type of the definition.
     * 
     * @return the type value.
     */
    DefinitionType type();

    /**
     * Gets the uri property: The definition's URI.
     * 
     * @return the uri value.
     */
    String uri();

    /**
     * Gets the url property: The REST URL of the definition.
     * 
     * @return the url value.
     */
    String url();

    /**
     * Gets the links property: The class to represent a collection of REST reference links.
     * 
     * @return the links value.
     */
    ReferenceLinks links();

    /**
     * Gets the batchSize property: Batch size of the definition.
     * 
     * @return the batchSize value.
     */
    Integer batchSize();

    /**
     * Gets the buildArgs property: The buildArgs property.
     * 
     * @return the buildArgs value.
     */
    String buildArgs();

    /**
     * Gets the continuousIntegrationQuietPeriod property: The continuous integration quiet period.
     * 
     * @return the continuousIntegrationQuietPeriod value.
     */
    Integer continuousIntegrationQuietPeriod();

    /**
     * Gets the controller property: The build controller.
     * 
     * @return the controller value.
     */
    BuildController controller();

    /**
     * Gets the createdOn property: The date this definition was created.
     * 
     * @return the createdOn value.
     */
    OffsetDateTime createdOn();

    /**
     * Gets the defaultDropLocation property: Default drop location for builds from this definition.
     * 
     * @return the defaultDropLocation value.
     */
    String defaultDropLocation();

    /**
     * Gets the description property: Description of the definition.
     * 
     * @return the description value.
     */
    String description();

    /**
     * Gets the lastBuild property: The last build on this definition.
     * 
     * @return the lastBuild value.
     */
    XamlBuildReference lastBuild();

    /**
     * Gets the repository property: The repository.
     * 
     * @return the repository value.
     */
    BuildRepository repository();

    /**
     * Gets the supportedReasons property: The reasons supported by the template.
     * 
     * @return the supportedReasons value.
     */
    BuildReason supportedReasons();

    /**
     * Gets the triggerType property: How builds are triggered from this definition.
     * 
     * @return the triggerType value.
     */
    DefinitionTriggerType triggerType();

    /**
     * Gets the inner com.azure.dev.fluent.models.XamlBuildDefinitionInner object.
     * 
     * @return the inner object.
     */
    XamlBuildDefinitionInner innerModel();
}
