// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Defines values for ResultSet.
 */
public enum ResultSet {
    /**
     * Enum value all.
     */
    ALL("all"),

    /**
     * Enum value top.
     */
    TOP("top");

    /**
     * The actual serialized value for a ResultSet instance.
     */
    private final String value;

    ResultSet(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a ResultSet instance.
     * 
     * @param value the serialized value to parse.
     * @return the parsed ResultSet object, or null if unable to parse.
     */
    @JsonCreator
    public static ResultSet fromString(String value) {
        if (value == null) {
            return null;
        }
        ResultSet[] items = ResultSet.values();
        for (ResultSet item : items) {
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
