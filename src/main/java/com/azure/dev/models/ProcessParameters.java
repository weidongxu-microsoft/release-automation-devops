// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The ProcessParameters model.
 */
@Fluent
public final class ProcessParameters {
    /*
     * The dataSourceBindings property.
     */
    @JsonProperty(value = "dataSourceBindings")
    private List<DataSourceBindingBase> dataSourceBindings;

    /*
     * The inputs property.
     */
    @JsonProperty(value = "inputs")
    private List<TaskInputDefinitionBase> inputs;

    /*
     * The sourceDefinitions property.
     */
    @JsonProperty(value = "sourceDefinitions")
    private List<TaskSourceDefinitionBase> sourceDefinitions;

    /**
     * Creates an instance of ProcessParameters class.
     */
    public ProcessParameters() {
    }

    /**
     * Get the dataSourceBindings property: The dataSourceBindings property.
     * 
     * @return the dataSourceBindings value.
     */
    public List<DataSourceBindingBase> dataSourceBindings() {
        return this.dataSourceBindings;
    }

    /**
     * Set the dataSourceBindings property: The dataSourceBindings property.
     * 
     * @param dataSourceBindings the dataSourceBindings value to set.
     * @return the ProcessParameters object itself.
     */
    public ProcessParameters withDataSourceBindings(List<DataSourceBindingBase> dataSourceBindings) {
        this.dataSourceBindings = dataSourceBindings;
        return this;
    }

    /**
     * Get the inputs property: The inputs property.
     * 
     * @return the inputs value.
     */
    public List<TaskInputDefinitionBase> inputs() {
        return this.inputs;
    }

    /**
     * Set the inputs property: The inputs property.
     * 
     * @param inputs the inputs value to set.
     * @return the ProcessParameters object itself.
     */
    public ProcessParameters withInputs(List<TaskInputDefinitionBase> inputs) {
        this.inputs = inputs;
        return this;
    }

    /**
     * Get the sourceDefinitions property: The sourceDefinitions property.
     * 
     * @return the sourceDefinitions value.
     */
    public List<TaskSourceDefinitionBase> sourceDefinitions() {
        return this.sourceDefinitions;
    }

    /**
     * Set the sourceDefinitions property: The sourceDefinitions property.
     * 
     * @param sourceDefinitions the sourceDefinitions value to set.
     * @return the ProcessParameters object itself.
     */
    public ProcessParameters withSourceDefinitions(List<TaskSourceDefinitionBase> sourceDefinitions) {
        this.sourceDefinitions = sourceDefinitions;
        return this;
    }

    /**
     * Validates the instance.
     * 
     * @throws IllegalArgumentException thrown if the instance is not valid.
     */
    public void validate() {
        if (dataSourceBindings() != null) {
            dataSourceBindings().forEach(e -> e.validate());
        }
        if (inputs() != null) {
            inputs().forEach(e -> e.validate());
        }
        if (sourceDefinitions() != null) {
            sourceDefinitions().forEach(e -> e.validate());
        }
    }
}
