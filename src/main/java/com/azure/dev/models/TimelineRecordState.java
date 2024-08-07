// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The state of the record.
 */
public enum TimelineRecordState {
    /**
     * Enum value pending.
     */
    PENDING("pending"),

    /**
     * Enum value inProgress.
     */
    IN_PROGRESS("inProgress"),

    /**
     * Enum value completed.
     */
    COMPLETED("completed");

    /**
     * The actual serialized value for a TimelineRecordState instance.
     */
    private final String value;

    TimelineRecordState(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a TimelineRecordState instance.
     * 
     * @param value the serialized value to parse.
     * @return the parsed TimelineRecordState object, or null if unable to parse.
     */
    @JsonCreator
    public static TimelineRecordState fromString(String value) {
        if (value == null) {
            return null;
        }
        TimelineRecordState[] items = TimelineRecordState.values();
        for (TimelineRecordState item : items) {
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
