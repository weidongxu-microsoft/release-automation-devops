// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Required information to create a new retention lease.
 */
@Fluent
public final class NewRetentionLease {
    /*
     * The number of days to consider the lease valid. A retention lease valid for more than 100 years (36500 days) will display as retaining the build "forever".
     */
    @JsonProperty(value = "daysValid")
    private Integer daysValid;

    /*
     * The pipeline definition of the run.
     */
    @JsonProperty(value = "definitionId")
    private Integer definitionId;

    /*
     * User-provided string that identifies the owner of a retention lease.
     */
    @JsonProperty(value = "ownerId")
    private String ownerId;

    /*
     * If set, this lease will also prevent the pipeline from being deleted while the lease is still valid.
     */
    @JsonProperty(value = "protectPipeline")
    private Boolean protectPipeline;

    /*
     * The pipeline run to protect.
     */
    @JsonProperty(value = "runId")
    private Integer runId;

    /**
     * Creates an instance of NewRetentionLease class.
     */
    public NewRetentionLease() {
    }

    /**
     * Get the daysValid property: The number of days to consider the lease valid. A retention lease valid for more than
     * 100 years (36500 days) will display as retaining the build "forever".
     * 
     * @return the daysValid value.
     */
    public Integer daysValid() {
        return this.daysValid;
    }

    /**
     * Set the daysValid property: The number of days to consider the lease valid. A retention lease valid for more than
     * 100 years (36500 days) will display as retaining the build "forever".
     * 
     * @param daysValid the daysValid value to set.
     * @return the NewRetentionLease object itself.
     */
    public NewRetentionLease withDaysValid(Integer daysValid) {
        this.daysValid = daysValid;
        return this;
    }

    /**
     * Get the definitionId property: The pipeline definition of the run.
     * 
     * @return the definitionId value.
     */
    public Integer definitionId() {
        return this.definitionId;
    }

    /**
     * Set the definitionId property: The pipeline definition of the run.
     * 
     * @param definitionId the definitionId value to set.
     * @return the NewRetentionLease object itself.
     */
    public NewRetentionLease withDefinitionId(Integer definitionId) {
        this.definitionId = definitionId;
        return this;
    }

    /**
     * Get the ownerId property: User-provided string that identifies the owner of a retention lease.
     * 
     * @return the ownerId value.
     */
    public String ownerId() {
        return this.ownerId;
    }

    /**
     * Set the ownerId property: User-provided string that identifies the owner of a retention lease.
     * 
     * @param ownerId the ownerId value to set.
     * @return the NewRetentionLease object itself.
     */
    public NewRetentionLease withOwnerId(String ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    /**
     * Get the protectPipeline property: If set, this lease will also prevent the pipeline from being deleted while the
     * lease is still valid.
     * 
     * @return the protectPipeline value.
     */
    public Boolean protectPipeline() {
        return this.protectPipeline;
    }

    /**
     * Set the protectPipeline property: If set, this lease will also prevent the pipeline from being deleted while the
     * lease is still valid.
     * 
     * @param protectPipeline the protectPipeline value to set.
     * @return the NewRetentionLease object itself.
     */
    public NewRetentionLease withProtectPipeline(Boolean protectPipeline) {
        this.protectPipeline = protectPipeline;
        return this;
    }

    /**
     * Get the runId property: The pipeline run to protect.
     * 
     * @return the runId value.
     */
    public Integer runId() {
        return this.runId;
    }

    /**
     * Set the runId property: The pipeline run to protect.
     * 
     * @param runId the runId value to set.
     * @return the NewRetentionLease object itself.
     */
    public NewRetentionLease withRunId(Integer runId) {
        this.runId = runId;
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
