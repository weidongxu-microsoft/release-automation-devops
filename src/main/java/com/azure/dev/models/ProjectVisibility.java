// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/** Defines values for ProjectVisibility. */
public enum ProjectVisibility {
    /** Enum value private. */
    PRIVATE("private"),

    /** Enum value public. */
    PUBLIC("public");

    /** The actual serialized value for a ProjectVisibility instance. */
    private final String value;

    ProjectVisibility(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a ProjectVisibility instance.
     *
     * @param value the serialized value to parse.
     * @return the parsed ProjectVisibility object, or null if unable to parse.
     */
    @JsonCreator
    public static ProjectVisibility fromString(String value) {
        ProjectVisibility[] items = ProjectVisibility.values();
        for (ProjectVisibility item : items) {
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
