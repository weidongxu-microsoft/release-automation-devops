// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

/**
 * Represents a referenec to a service endpoint.
 */
@Fluent
public final class ServiceEndpointReference extends ResourceReference {
    /*
     * The ID of the service endpoint.
     */
    @JsonProperty(value = "id")
    private UUID id;

    /**
     * Creates an instance of ServiceEndpointReference class.
     */
    public ServiceEndpointReference() {
    }

    /**
     * Get the id property: The ID of the service endpoint.
     * 
     * @return the id value.
     */
    public UUID id() {
        return this.id;
    }

    /**
     * Set the id property: The ID of the service endpoint.
     * 
     * @param id the id value to set.
     * @return the ServiceEndpointReference object itself.
     */
    public ServiceEndpointReference withId(UUID id) {
        this.id = id;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceEndpointReference withAlias(String alias) {
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
