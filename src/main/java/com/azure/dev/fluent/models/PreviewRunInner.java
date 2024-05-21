// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.fluent.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The PreviewRun model.
 */
@Fluent
public final class PreviewRunInner {
    /*
     * The finalYaml property.
     */
    @JsonProperty(value = "finalYaml")
    private String finalYaml;

    /**
     * Creates an instance of PreviewRunInner class.
     */
    public PreviewRunInner() {
    }

    /**
     * Get the finalYaml property: The finalYaml property.
     * 
     * @return the finalYaml value.
     */
    public String finalYaml() {
        return this.finalYaml;
    }

    /**
     * Set the finalYaml property: The finalYaml property.
     * 
     * @param finalYaml the finalYaml value to set.
     * @return the PreviewRunInner object itself.
     */
    public PreviewRunInner withFinalYaml(String finalYaml) {
        this.finalYaml = finalYaml;
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
