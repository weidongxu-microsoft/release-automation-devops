// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The RepositoryResource model.
 */
@Fluent
public final class RepositoryResource {
    /*
     * The refName property.
     */
    @JsonProperty(value = "refName")
    private String refName;

    /*
     * The repository property.
     */
    @JsonProperty(value = "repository")
    private Repository repository;

    /*
     * The version property.
     */
    @JsonProperty(value = "version")
    private String version;

    /**
     * Creates an instance of RepositoryResource class.
     */
    public RepositoryResource() {
    }

    /**
     * Get the refName property: The refName property.
     * 
     * @return the refName value.
     */
    public String refName() {
        return this.refName;
    }

    /**
     * Set the refName property: The refName property.
     * 
     * @param refName the refName value to set.
     * @return the RepositoryResource object itself.
     */
    public RepositoryResource withRefName(String refName) {
        this.refName = refName;
        return this;
    }

    /**
     * Get the repository property: The repository property.
     * 
     * @return the repository value.
     */
    public Repository repository() {
        return this.repository;
    }

    /**
     * Set the repository property: The repository property.
     * 
     * @param repository the repository value to set.
     * @return the RepositoryResource object itself.
     */
    public RepositoryResource withRepository(Repository repository) {
        this.repository = repository;
        return this;
    }

    /**
     * Get the version property: The version property.
     * 
     * @return the version value.
     */
    public String version() {
        return this.version;
    }

    /**
     * Set the version property: The version property.
     * 
     * @param version the version value to set.
     * @return the RepositoryResource object itself.
     */
    public RepositoryResource withVersion(String version) {
        this.version = version;
        return this;
    }

    /**
     * Validates the instance.
     * 
     * @throws IllegalArgumentException thrown if the instance is not valid.
     */
    public void validate() {
        if (repository() != null) {
            repository().validate();
        }
    }
}
