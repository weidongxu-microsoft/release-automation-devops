// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.fluent.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents information about resources used by builds in the system.
 */
@Fluent
public final class BuildResourceUsageInner {
    /*
     * The number of build agents.
     */
    @JsonProperty(value = "distributedTaskAgents")
    private Integer distributedTaskAgents;

    /*
     * The number of paid private agent slots.
     */
    @JsonProperty(value = "paidPrivateAgentSlots")
    private Integer paidPrivateAgentSlots;

    /*
     * The total usage.
     */
    @JsonProperty(value = "totalUsage")
    private Integer totalUsage;

    /*
     * The number of XAML controllers.
     */
    @JsonProperty(value = "xamlControllers")
    private Integer xamlControllers;

    /**
     * Creates an instance of BuildResourceUsageInner class.
     */
    public BuildResourceUsageInner() {
    }

    /**
     * Get the distributedTaskAgents property: The number of build agents.
     * 
     * @return the distributedTaskAgents value.
     */
    public Integer distributedTaskAgents() {
        return this.distributedTaskAgents;
    }

    /**
     * Set the distributedTaskAgents property: The number of build agents.
     * 
     * @param distributedTaskAgents the distributedTaskAgents value to set.
     * @return the BuildResourceUsageInner object itself.
     */
    public BuildResourceUsageInner withDistributedTaskAgents(Integer distributedTaskAgents) {
        this.distributedTaskAgents = distributedTaskAgents;
        return this;
    }

    /**
     * Get the paidPrivateAgentSlots property: The number of paid private agent slots.
     * 
     * @return the paidPrivateAgentSlots value.
     */
    public Integer paidPrivateAgentSlots() {
        return this.paidPrivateAgentSlots;
    }

    /**
     * Set the paidPrivateAgentSlots property: The number of paid private agent slots.
     * 
     * @param paidPrivateAgentSlots the paidPrivateAgentSlots value to set.
     * @return the BuildResourceUsageInner object itself.
     */
    public BuildResourceUsageInner withPaidPrivateAgentSlots(Integer paidPrivateAgentSlots) {
        this.paidPrivateAgentSlots = paidPrivateAgentSlots;
        return this;
    }

    /**
     * Get the totalUsage property: The total usage.
     * 
     * @return the totalUsage value.
     */
    public Integer totalUsage() {
        return this.totalUsage;
    }

    /**
     * Set the totalUsage property: The total usage.
     * 
     * @param totalUsage the totalUsage value to set.
     * @return the BuildResourceUsageInner object itself.
     */
    public BuildResourceUsageInner withTotalUsage(Integer totalUsage) {
        this.totalUsage = totalUsage;
        return this;
    }

    /**
     * Get the xamlControllers property: The number of XAML controllers.
     * 
     * @return the xamlControllers value.
     */
    public Integer xamlControllers() {
        return this.xamlControllers;
    }

    /**
     * Set the xamlControllers property: The number of XAML controllers.
     * 
     * @param xamlControllers the xamlControllers value to set.
     * @return the BuildResourceUsageInner object itself.
     */
    public BuildResourceUsageInner withXamlControllers(Integer xamlControllers) {
        this.xamlControllers = xamlControllers;
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
