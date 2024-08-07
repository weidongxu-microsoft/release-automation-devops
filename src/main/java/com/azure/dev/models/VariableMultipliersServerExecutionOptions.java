// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Represents options for running a phase based on values specified by a list of variables.
 */
@Fluent
public final class VariableMultipliersServerExecutionOptions extends ServerTargetExecutionOptions {
    /*
     * Indicates whether failure of one job should prevent the phase from running in other jobs.
     */
    @JsonProperty(value = "continueOnError")
    private Boolean continueOnError;

    /*
     * The maximum number of server jobs to run in parallel.
     */
    @JsonProperty(value = "maxConcurrency")
    private Integer maxConcurrency;

    /*
     * The multipliers property.
     */
    @JsonProperty(value = "multipliers")
    private List<String> multipliers;

    /**
     * Creates an instance of VariableMultipliersServerExecutionOptions class.
     */
    public VariableMultipliersServerExecutionOptions() {
    }

    /**
     * Get the continueOnError property: Indicates whether failure of one job should prevent the phase from running in
     * other jobs.
     * 
     * @return the continueOnError value.
     */
    public Boolean continueOnError() {
        return this.continueOnError;
    }

    /**
     * Set the continueOnError property: Indicates whether failure of one job should prevent the phase from running in
     * other jobs.
     * 
     * @param continueOnError the continueOnError value to set.
     * @return the VariableMultipliersServerExecutionOptions object itself.
     */
    public VariableMultipliersServerExecutionOptions withContinueOnError(Boolean continueOnError) {
        this.continueOnError = continueOnError;
        return this;
    }

    /**
     * Get the maxConcurrency property: The maximum number of server jobs to run in parallel.
     * 
     * @return the maxConcurrency value.
     */
    public Integer maxConcurrency() {
        return this.maxConcurrency;
    }

    /**
     * Set the maxConcurrency property: The maximum number of server jobs to run in parallel.
     * 
     * @param maxConcurrency the maxConcurrency value to set.
     * @return the VariableMultipliersServerExecutionOptions object itself.
     */
    public VariableMultipliersServerExecutionOptions withMaxConcurrency(Integer maxConcurrency) {
        this.maxConcurrency = maxConcurrency;
        return this;
    }

    /**
     * Get the multipliers property: The multipliers property.
     * 
     * @return the multipliers value.
     */
    public List<String> multipliers() {
        return this.multipliers;
    }

    /**
     * Set the multipliers property: The multipliers property.
     * 
     * @param multipliers the multipliers value to set.
     * @return the VariableMultipliersServerExecutionOptions object itself.
     */
    public VariableMultipliersServerExecutionOptions withMultipliers(List<String> multipliers) {
        this.multipliers = multipliers;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VariableMultipliersServerExecutionOptions withType(Integer type) {
        super.withType(type);
        return this;
    }

    /**
     * Validates the instance.
     * 
     * @throws IllegalArgumentException thrown if the instance is not valid.
     */
    @Override
    public void validate() {
        super.validate();
    }
}
