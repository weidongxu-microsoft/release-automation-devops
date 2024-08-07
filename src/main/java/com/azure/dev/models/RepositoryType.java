// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Defines values for RepositoryType.
 */
public enum RepositoryType {
    /**
     * Enum value unknown.
     */
    UNKNOWN("unknown"),

    /**
     * Enum value gitHub.
     */
    GIT_HUB("gitHub"),

    /**
     * Enum value azureReposGit.
     */
    AZURE_REPOS_GIT("azureReposGit"),

    /**
     * Enum value gitHubEnterprise.
     */
    GIT_HUB_ENTERPRISE("gitHubEnterprise"),

    /**
     * Enum value azureReposGitHyphenated.
     */
    AZURE_REPOS_GIT_HYPHENATED("azureReposGitHyphenated");

    /**
     * The actual serialized value for a RepositoryType instance.
     */
    private final String value;

    RepositoryType(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a RepositoryType instance.
     * 
     * @param value the serialized value to parse.
     * @return the parsed RepositoryType object, or null if unable to parse.
     */
    @JsonCreator
    public static RepositoryType fromString(String value) {
        if (value == null) {
            return null;
        }
        RepositoryType[] items = RepositoryType.values();
        for (RepositoryType item : items) {
            if (item.toString().equalsIgnoreCase(value)) {
                return item;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @JsonValue
    @Override
    public String toString() {
        return this.value;
    }
}
