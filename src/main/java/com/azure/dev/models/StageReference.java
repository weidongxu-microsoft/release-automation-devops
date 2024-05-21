// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Stage in pipeline.
 */
@Fluent
public final class StageReference {
    /*
     * Attempt number of stage
     */
    @JsonProperty(value = "attempt")
    private Integer attempt;

    /*
     * Name of the stage. Maximum supported length for name is 256 character.
     */
    @JsonProperty(value = "stageName")
    private String stageName;

    /**
     * Creates an instance of StageReference class.
     */
    public StageReference() {
    }

    /**
     * Get the attempt property: Attempt number of stage.
     * 
     * @return the attempt value.
     */
    public Integer attempt() {
        return this.attempt;
    }

    /**
     * Set the attempt property: Attempt number of stage.
     * 
     * @param attempt the attempt value to set.
     * @return the StageReference object itself.
     */
    public StageReference withAttempt(Integer attempt) {
        this.attempt = attempt;
        return this;
    }

    /**
     * Get the stageName property: Name of the stage. Maximum supported length for name is 256 character.
     * 
     * @return the stageName value.
     */
    public String stageName() {
        return this.stageName;
    }

    /**
     * Set the stageName property: Name of the stage. Maximum supported length for name is 256 character.
     * 
     * @param stageName the stageName value to set.
     * @return the StageReference object itself.
     */
    public StageReference withStageName(String stageName) {
        this.stageName = stageName;
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
