// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The TestResultsContext model.
 */
@Fluent
public final class TestResultsContext {
    /*
     * Represents a reference to a build.
     */
    @JsonProperty(value = "build")
    private BuildReference build;

    /*
     * The contextType property.
     */
    @JsonProperty(value = "contextType")
    private TestResultsContextType contextType;

    /*
     * Pipeline reference
     */
    @JsonProperty(value = "pipelineReference")
    private PipelineReferenceInBuild pipelineReference;

    /*
     * Reference to a release.
     */
    @JsonProperty(value = "release")
    private ReleaseReference release;

    /**
     * Creates an instance of TestResultsContext class.
     */
    public TestResultsContext() {
    }

    /**
     * Get the build property: Represents a reference to a build.
     * 
     * @return the build value.
     */
    public BuildReference build() {
        return this.build;
    }

    /**
     * Set the build property: Represents a reference to a build.
     * 
     * @param build the build value to set.
     * @return the TestResultsContext object itself.
     */
    public TestResultsContext withBuild(BuildReference build) {
        this.build = build;
        return this;
    }

    /**
     * Get the contextType property: The contextType property.
     * 
     * @return the contextType value.
     */
    public TestResultsContextType contextType() {
        return this.contextType;
    }

    /**
     * Set the contextType property: The contextType property.
     * 
     * @param contextType the contextType value to set.
     * @return the TestResultsContext object itself.
     */
    public TestResultsContext withContextType(TestResultsContextType contextType) {
        this.contextType = contextType;
        return this;
    }

    /**
     * Get the pipelineReference property: Pipeline reference.
     * 
     * @return the pipelineReference value.
     */
    public PipelineReferenceInBuild pipelineReference() {
        return this.pipelineReference;
    }

    /**
     * Set the pipelineReference property: Pipeline reference.
     * 
     * @param pipelineReference the pipelineReference value to set.
     * @return the TestResultsContext object itself.
     */
    public TestResultsContext withPipelineReference(PipelineReferenceInBuild pipelineReference) {
        this.pipelineReference = pipelineReference;
        return this;
    }

    /**
     * Get the release property: Reference to a release.
     * 
     * @return the release value.
     */
    public ReleaseReference release() {
        return this.release;
    }

    /**
     * Set the release property: Reference to a release.
     * 
     * @param release the release value to set.
     * @return the TestResultsContext object itself.
     */
    public TestResultsContext withRelease(ReleaseReference release) {
        this.release = release;
        return this;
    }

    /**
     * Validates the instance.
     * 
     * @throws IllegalArgumentException thrown if the instance is not valid.
     */
    public void validate() {
        if (build() != null) {
            build().validate();
        }
        if (pipelineReference() != null) {
            pipelineReference().validate();
        }
        if (release() != null) {
            release().validate();
        }
    }
}
