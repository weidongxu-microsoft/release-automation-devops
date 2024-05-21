// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Repository model.
 */
@Fluent
public final class Repository {
    /*
     * The type property.
     */
    @JsonProperty(value = "type")
    private RepositoryType type;

    /**
     * Creates an instance of Repository class.
     */
    public Repository() {
    }

    /**
     * Get the type property: The type property.
     * 
     * @return the type value.
     */
    public RepositoryType type() {
        return this.type;
    }

    /**
     * Set the type property: The type property.
     * 
     * @param type the type value to set.
     * @return the Repository object itself.
     */
    public Repository withType(RepositoryType type) {
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
