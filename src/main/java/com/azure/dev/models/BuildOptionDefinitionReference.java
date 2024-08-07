// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

/**
 * Represents a reference to a build option definition.
 */
@Fluent
public class BuildOptionDefinitionReference {
    /*
     * The ID of the referenced build option.
     */
    @JsonProperty(value = "id")
    private UUID id;

    /**
     * Creates an instance of BuildOptionDefinitionReference class.
     */
    public BuildOptionDefinitionReference() {
    }

    /**
     * Get the id property: The ID of the referenced build option.
     * 
     * @return the id value.
     */
    public UUID id() {
        return this.id;
    }

    /**
     * Set the id property: The ID of the referenced build option.
     * 
     * @param id the id value to set.
     * @return the BuildOptionDefinitionReference object itself.
     */
    public BuildOptionDefinitionReference withId(UUID id) {
        this.id = id;
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
