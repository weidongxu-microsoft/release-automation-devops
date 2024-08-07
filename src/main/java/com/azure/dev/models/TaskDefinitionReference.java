// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

/**
 * A reference to a task definition.
 */
@Fluent
public final class TaskDefinitionReference {
    /*
     * The type of task (task or task group).
     */
    @JsonProperty(value = "definitionType")
    private String definitionType;

    /*
     * The ID of the task.
     */
    @JsonProperty(value = "id")
    private UUID id;

    /*
     * The version of the task.
     */
    @JsonProperty(value = "versionSpec")
    private String versionSpec;

    /**
     * Creates an instance of TaskDefinitionReference class.
     */
    public TaskDefinitionReference() {
    }

    /**
     * Get the definitionType property: The type of task (task or task group).
     * 
     * @return the definitionType value.
     */
    public String definitionType() {
        return this.definitionType;
    }

    /**
     * Set the definitionType property: The type of task (task or task group).
     * 
     * @param definitionType the definitionType value to set.
     * @return the TaskDefinitionReference object itself.
     */
    public TaskDefinitionReference withDefinitionType(String definitionType) {
        this.definitionType = definitionType;
        return this;
    }

    /**
     * Get the id property: The ID of the task.
     * 
     * @return the id value.
     */
    public UUID id() {
        return this.id;
    }

    /**
     * Set the id property: The ID of the task.
     * 
     * @param id the id value to set.
     * @return the TaskDefinitionReference object itself.
     */
    public TaskDefinitionReference withId(UUID id) {
        this.id = id;
        return this;
    }

    /**
     * Get the versionSpec property: The version of the task.
     * 
     * @return the versionSpec value.
     */
    public String versionSpec() {
        return this.versionSpec;
    }

    /**
     * Set the versionSpec property: The version of the task.
     * 
     * @param versionSpec the versionSpec value to set.
     * @return the TaskDefinitionReference object itself.
     */
    public TaskDefinitionReference withVersionSpec(String versionSpec) {
        this.versionSpec = versionSpec;
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
