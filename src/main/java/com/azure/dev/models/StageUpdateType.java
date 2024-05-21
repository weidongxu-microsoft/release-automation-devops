// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Defines values for StageUpdateType.
 */
public enum StageUpdateType {
    /**
     * Enum value cancel.
     */
    CANCEL("cancel"),

    /**
     * Enum value retry.
     */
    RETRY("retry");

    /**
     * The actual serialized value for a StageUpdateType instance.
     */
    private final String value;

    StageUpdateType(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a StageUpdateType instance.
     * 
     * @param value the serialized value to parse.
     * @return the parsed StageUpdateType object, or null if unable to parse.
     */
    @JsonCreator
    public static StageUpdateType fromString(String value) {
        if (value == null) {
            return null;
        }
        StageUpdateType[] items = StageUpdateType.values();
        for (StageUpdateType item : items) {
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
