// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.models;

import com.azure.core.annotation.Fluent;
import com.azure.core.util.logging.ClientLogger;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

/** Represents a reference to a plan group. */
@Fluent
public final class TaskOrchestrationPlanGroupReference {
    @JsonIgnore private final ClientLogger logger = new ClientLogger(TaskOrchestrationPlanGroupReference.class);

    /*
     * The name of the plan group.
     */
    @JsonProperty(value = "planGroup")
    private String planGroup;

    /*
     * The project ID.
     */
    @JsonProperty(value = "projectId")
    private UUID projectId;

    /**
     * Get the planGroup property: The name of the plan group.
     *
     * @return the planGroup value.
     */
    public String planGroup() {
        return this.planGroup;
    }

    /**
     * Set the planGroup property: The name of the plan group.
     *
     * @param planGroup the planGroup value to set.
     * @return the TaskOrchestrationPlanGroupReference object itself.
     */
    public TaskOrchestrationPlanGroupReference withPlanGroup(String planGroup) {
        this.planGroup = planGroup;
        return this;
    }

    /**
     * Get the projectId property: The project ID.
     *
     * @return the projectId value.
     */
    public UUID projectId() {
        return this.projectId;
    }

    /**
     * Set the projectId property: The project ID.
     *
     * @param projectId the projectId value to set.
     * @return the TaskOrchestrationPlanGroupReference object itself.
     */
    public TaskOrchestrationPlanGroupReference withProjectId(UUID projectId) {
        this.projectId = projectId;
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
