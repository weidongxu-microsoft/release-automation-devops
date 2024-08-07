// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The PipelineConfiguration model.
 */
@Fluent
public final class PipelineConfiguration {
    /*
     * Type of configuration.
     */
    @JsonProperty(value = "type")
    private ConfigurationType type;

    /**
     * Creates an instance of PipelineConfiguration class.
     */
    public PipelineConfiguration() {
    }

    /**
     * Get the type property: Type of configuration.
     * 
     * @return the type value.
     */
    public ConfigurationType type() {
        return this.type;
    }

    /**
     * Set the type property: Type of configuration.
     * 
     * @param type the type value to set.
     * @return the PipelineConfiguration object itself.
     */
    public PipelineConfiguration withType(ConfigurationType type) {
        this.type = type;
        return this;
    }

    /**
     * Validates the instance.
     * 
     * @throws IllegalArgumentException thrown if the instance is not valid.
     */
    public void validate() {
    }
}
