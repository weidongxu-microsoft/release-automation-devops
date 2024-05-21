// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Represents a retention policy for a build definition.
 */
@Fluent
public final class RetentionPolicy {
    /*
     * The artifacts property.
     */
    @JsonProperty(value = "artifacts")
    private List<String> artifacts;

    /*
     * The artifactTypesToDelete property.
     */
    @JsonProperty(value = "artifactTypesToDelete")
    private List<String> artifactTypesToDelete;

    /*
     * The branches property.
     */
    @JsonProperty(value = "branches")
    private List<String> branches;

    /*
     * The number of days to keep builds.
     */
    @JsonProperty(value = "daysToKeep")
    private Integer daysToKeep;

    /*
     * Indicates whether the build record itself should be deleted.
     */
    @JsonProperty(value = "deleteBuildRecord")
    private Boolean deleteBuildRecord;

    /*
     * Indicates whether to delete test results associated with the build.
     */
    @JsonProperty(value = "deleteTestResults")
    private Boolean deleteTestResults;

    /*
     * The minimum number of builds to keep.
     */
    @JsonProperty(value = "minimumToKeep")
    private Integer minimumToKeep;

    /**
     * Creates an instance of RetentionPolicy class.
     */
    public RetentionPolicy() {
    }

    /**
     * Get the artifacts property: The artifacts property.
     * 
     * @return the artifacts value.
     */
    public List<String> artifacts() {
        return this.artifacts;
    }

    /**
     * Set the artifacts property: The artifacts property.
     * 
     * @param artifacts the artifacts value to set.
     * @return the RetentionPolicy object itself.
     */
    public RetentionPolicy withArtifacts(List<String> artifacts) {
        this.artifacts = artifacts;
        return this;
    }

    /**
     * Get the artifactTypesToDelete property: The artifactTypesToDelete property.
     * 
     * @return the artifactTypesToDelete value.
     */
    public List<String> artifactTypesToDelete() {
        return this.artifactTypesToDelete;
    }

    /**
     * Set the artifactTypesToDelete property: The artifactTypesToDelete property.
     * 
     * @param artifactTypesToDelete the artifactTypesToDelete value to set.
     * @return the RetentionPolicy object itself.
     */
    public RetentionPolicy withArtifactTypesToDelete(List<String> artifactTypesToDelete) {
        this.artifactTypesToDelete = artifactTypesToDelete;
        return this;
    }

    /**
     * Get the branches property: The branches property.
     * 
     * @return the branches value.
     */
    public List<String> branches() {
        return this.branches;
    }

    /**
     * Set the branches property: The branches property.
     * 
     * @param branches the branches value to set.
     * @return the RetentionPolicy object itself.
     */
    public RetentionPolicy withBranches(List<String> branches) {
        this.branches = branches;
        return this;
    }

    /**
     * Get the daysToKeep property: The number of days to keep builds.
     * 
     * @return the daysToKeep value.
     */
    public Integer daysToKeep() {
        return this.daysToKeep;
    }

    /**
     * Set the daysToKeep property: The number of days to keep builds.
     * 
     * @param daysToKeep the daysToKeep value to set.
     * @return the RetentionPolicy object itself.
     */
    public RetentionPolicy withDaysToKeep(Integer daysToKeep) {
        this.daysToKeep = daysToKeep;
        return this;
    }

    /**
     * Get the deleteBuildRecord property: Indicates whether the build record itself should be deleted.
     * 
     * @return the deleteBuildRecord value.
     */
    public Boolean deleteBuildRecord() {
        return this.deleteBuildRecord;
    }

    /**
     * Set the deleteBuildRecord property: Indicates whether the build record itself should be deleted.
     * 
     * @param deleteBuildRecord the deleteBuildRecord value to set.
     * @return the RetentionPolicy object itself.
     */
    public RetentionPolicy withDeleteBuildRecord(Boolean deleteBuildRecord) {
        this.deleteBuildRecord = deleteBuildRecord;
        return this;
    }

    /**
     * Get the deleteTestResults property: Indicates whether to delete test results associated with the build.
     * 
     * @return the deleteTestResults value.
     */
    public Boolean deleteTestResults() {
        return this.deleteTestResults;
    }

    /**
     * Set the deleteTestResults property: Indicates whether to delete test results associated with the build.
     * 
     * @param deleteTestResults the deleteTestResults value to set.
     * @return the RetentionPolicy object itself.
     */
    public RetentionPolicy withDeleteTestResults(Boolean deleteTestResults) {
        this.deleteTestResults = deleteTestResults;
        return this;
    }

    /**
     * Get the minimumToKeep property: The minimum number of builds to keep.
     * 
     * @return the minimumToKeep value.
     */
    public Integer minimumToKeep() {
        return this.minimumToKeep;
    }

    /**
     * Set the minimumToKeep property: The minimum number of builds to keep.
     * 
     * @param minimumToKeep the minimumToKeep value to set.
     * @return the RetentionPolicy object itself.
     */
    public RetentionPolicy withMinimumToKeep(Integer minimumToKeep) {
        this.minimumToKeep = minimumToKeep;
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
