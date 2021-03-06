// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/** Defines values for ProjectState. */
public enum ProjectState {
    /** Enum value deleting. */
    DELETING("deleting"),

    /** Enum value new. */
    NEW("new"),

    /** Enum value wellFormed. */
    WELL_FORMED("wellFormed"),

    /** Enum value createPending. */
    CREATE_PENDING("createPending"),

    /** Enum value all. */
    ALL("all"),

    /** Enum value unchanged. */
    UNCHANGED("unchanged"),

    /** Enum value deleted. */
    DELETED("deleted");

    /** The actual serialized value for a ProjectState instance. */
    private final String value;

    ProjectState(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a ProjectState instance.
     *
     * @param value the serialized value to parse.
     * @return the parsed ProjectState object, or null if unable to parse.
     */
    @JsonCreator
    public static ProjectState fromString(String value) {
        ProjectState[] items = ProjectState.values();
        for (ProjectState item : items) {
            if (item.toString().equalsIgnoreCase(value)) {
                return item;
            }
        }
        return null;
    }

    @JsonValue
    @Override
    public String toString() {
        return this.value;
    }
}
