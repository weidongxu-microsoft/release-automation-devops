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
public final class VariableMultipliersAgentExecutionOptions extends AgentTargetExecutionOptions {
    /*
     * Indicates whether failure on one agent should prevent the phase from running on other agents.
     */
    @JsonProperty(value = "continueOnError")
    private Boolean continueOnError;

    /*
     * The maximum number of agents to use in parallel.
     */
    @JsonProperty(value = "maxConcurrency")
    private Integer maxConcurrency;

    /*
     * The multipliers property.
     */
    @JsonProperty(value = "multipliers")
    private List<String> multipliers;

    /**
     * Creates an instance of VariableMultipliersAgentExecutionOptions class.
     */
    public VariableMultipliersAgentExecutionOptions() {
    }

    /**
     * Get the continueOnError property: Indicates whether failure on one agent should prevent the phase from running on
     * other agents.
     * 
     * @return the continueOnError value.
     */
    public Boolean continueOnError() {
        return this.continueOnError;
    }

    /**
     * Set the continueOnError property: Indicates whether failure on one agent should prevent the phase from running on
     * other agents.
     * 
     * @param continueOnError the continueOnError value to set.
     * @return the VariableMultipliersAgentExecutionOptions object itself.
     */
    public VariableMultipliersAgentExecutionOptions withContinueOnError(Boolean continueOnError) {
        this.continueOnError = continueOnError;
        return this;
    }

    /**
     * Get the maxConcurrency property: The maximum number of agents to use in parallel.
     * 
     * @return the maxConcurrency value.
     */
    public Integer maxConcurrency() {
        return this.maxConcurrency;
    }

    /**
     * Set the maxConcurrency property: The maximum number of agents to use in parallel.
     * 
     * @param maxConcurrency the maxConcurrency value to set.
     * @return the VariableMultipliersAgentExecutionOptions object itself.
     */
    public VariableMultipliersAgentExecutionOptions withMaxConcurrency(Integer maxConcurrency) {
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
     * @return the VariableMultipliersAgentExecutionOptions object itself.
     */
    public VariableMultipliersAgentExecutionOptions withMultipliers(List<String> multipliers) {
        this.multipliers = multipliers;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VariableMultipliersAgentExecutionOptions withType(Integer type) {
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
