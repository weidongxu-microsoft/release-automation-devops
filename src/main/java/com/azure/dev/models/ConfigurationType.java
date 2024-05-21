// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Type of configuration.
 */
public enum ConfigurationType {
    /**
     * Enum value unknown.
     */
    UNKNOWN("unknown"),

    /**
     * Enum value yaml.
     */
    YAML("yaml"),

    /**
     * Enum value designerJson.
     */
    DESIGNER_JSON("designerJson"),

    /**
     * Enum value justInTime.
     */
    JUST_IN_TIME("justInTime"),

    /**
     * Enum value designerHyphenJson.
     */
    DESIGNER_HYPHEN_JSON("designerHyphenJson");

    /**
     * The actual serialized value for a ConfigurationType instance.
     */
    private final String value;

    ConfigurationType(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a ConfigurationType instance.
     * 
     * @param value the serialized value to parse.
     * @return the parsed ConfigurationType object, or null if unable to parse.
     */
    @JsonCreator
    public static ConfigurationType fromString(String value) {
        if (value == null) {
            return null;
        }
        ConfigurationType[] items = ConfigurationType.values();
        for (ConfigurationType item : items) {
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
