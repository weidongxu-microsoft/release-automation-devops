// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/** Defines values for ServiceHostStatus. */
public enum ServiceHostStatus {
    /** Enum value online. */
    ONLINE("online"),

    /** Enum value offline. */
    OFFLINE("offline");

    /** The actual serialized value for a ServiceHostStatus instance. */
    private final String value;

    ServiceHostStatus(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a ServiceHostStatus instance.
     *
     * @param value the serialized value to parse.
     * @return the parsed ServiceHostStatus object, or null if unable to parse.
     */
    @JsonCreator
    public static ServiceHostStatus fromString(String value) {
        ServiceHostStatus[] items = ServiceHostStatus.values();
        for (ServiceHostStatus item : items) {
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
