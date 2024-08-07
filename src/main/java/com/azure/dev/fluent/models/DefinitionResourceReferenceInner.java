// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.fluent.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The DefinitionResourceReference model.
 */
@Fluent
public final class DefinitionResourceReferenceInner {
    /*
     * Indicates whether the resource is authorized for use.
     */
    @JsonProperty(value = "authorized")
    private Boolean authorized;

    /*
     * The id of the resource.
     */
    @JsonProperty(value = "id")
    private String id;

    /*
     * A friendly name for the resource.
     */
    @JsonProperty(value = "name")
    private String name;

    /*
     * The type of the resource.
     */
    @JsonProperty(value = "type")
    private String type;

    /**
     * Creates an instance of DefinitionResourceReferenceInner class.
     */
    public DefinitionResourceReferenceInner() {
    }

    /**
     * Get the authorized property: Indicates whether the resource is authorized for use.
     * 
     * @return the authorized value.
     */
    public Boolean authorized() {
        return this.authorized;
    }

    /**
     * Set the authorized property: Indicates whether the resource is authorized for use.
     * 
     * @param authorized the authorized value to set.
     * @return the DefinitionResourceReferenceInner object itself.
     */
    public DefinitionResourceReferenceInner withAuthorized(Boolean authorized) {
        this.authorized = authorized;
        return this;
    }

    /**
     * Get the id property: The id of the resource.
     * 
     * @return the id value.
     */
    public String id() {
        return this.id;
    }

    /**
     * Set the id property: The id of the resource.
     * 
     * @param id the id value to set.
     * @return the DefinitionResourceReferenceInner object itself.
     */
    public DefinitionResourceReferenceInner withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * Get the name property: A friendly name for the resource.
     * 
     * @return the name value.
     */
    public String name() {
        return this.name;
    }

    /**
     * Set the name property: A friendly name for the resource.
     * 
     * @param name the name value to set.
     * @return the DefinitionResourceReferenceInner object itself.
     */
    public DefinitionResourceReferenceInner withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get the type property: The type of the resource.
     * 
     * @return the type value.
     */
    public String type() {
        return this.type;
    }

    /**
     * Set the type property: The type of the resource.
     * 
     * @param type the type value to set.
     * @return the DefinitionResourceReferenceInner object itself.
     */
    public DefinitionResourceReferenceInner withType(String type) {
        this.type = type;
        return this;
    }

    /**
     * Validates the instance.
     * 
     * @throws IllegalArgumentException thrown if the instance is not valid.
     */
    public void validate() {
    }
}
