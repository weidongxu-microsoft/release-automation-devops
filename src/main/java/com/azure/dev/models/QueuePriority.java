// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Azure Pipelines does not support job priority. This field is deprecated.
 */
public enum QueuePriority {
    /**
     * Enum value low.
     */
    LOW("low"),

    /**
     * Enum value belowNormal.
     */
    BELOW_NORMAL("belowNormal"),

    /**
     * Enum value normal.
     */
    NORMAL("normal"),

    /**
     * Enum value aboveNormal.
     */
    ABOVE_NORMAL("aboveNormal"),

    /**
     * Enum value high.
     */
    HIGH("high");

    /**
     * The actual serialized value for a QueuePriority instance.
     */
    private final String value;

    QueuePriority(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a QueuePriority instance.
     * 
     * @param value the serialized value to parse.
     * @return the parsed QueuePriority object, or null if unable to parse.
     */
    @JsonCreator
    public static QueuePriority fromString(String value) {
        if (value == null) {
            return null;
        }
        QueuePriority[] items = QueuePriority.values();
        for (QueuePriority item : items) {
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
