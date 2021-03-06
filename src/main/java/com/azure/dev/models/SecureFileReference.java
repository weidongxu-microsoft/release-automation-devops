// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.models;

import com.azure.core.annotation.Fluent;
import com.azure.core.util.logging.ClientLogger;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

/** Represents a reference to a secure file. */
@Fluent
public final class SecureFileReference extends ResourceReference {
    @JsonIgnore private final ClientLogger logger = new ClientLogger(SecureFileReference.class);

    /*
     * The ID of the secure file.
     */
    @JsonProperty(value = "id")
    private UUID id;

    /**
     * Get the id property: The ID of the secure file.
     *
     * @return the id value.
     */
    public UUID id() {
        return this.id;
    }

    /**
     * Set the id property: The ID of the secure file.
     *
     * @param id the id value to set.
     * @return the SecureFileReference object itself.
     */
    public SecureFileReference withId(UUID id) {
        this.id = id;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public SecureFileReference withAlias(String alias) {
        super.withAlias(alias);
        return this;
    }

    /**
     * Validates the instance.
     *
     * @throws IllegalArgumentException thrown if the instance is not valid.
     */
    @Override
    public void validate() {
        super.validate();
    }
}
