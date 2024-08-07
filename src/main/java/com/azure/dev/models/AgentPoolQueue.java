// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a queue for running builds.
 */
@Fluent
public final class AgentPoolQueue {
    /*
     * The class to represent a collection of REST reference links.
     */
    @JsonProperty(value = "_links")
    private ReferenceLinks links;

    /*
     * The ID of the queue.
     */
    @JsonProperty(value = "id")
    private Integer id;

    /*
     * The name of the queue.
     */
    @JsonProperty(value = "name")
    private String name;

    /*
     * The pool used by this queue.
     */
    @JsonProperty(value = "pool")
    private TaskAgentPoolReference pool;

    /*
     * The full http link to the resource.
     */
    @JsonProperty(value = "url")
    private String url;

    /**
     * Creates an instance of AgentPoolQueue class.
     */
    public AgentPoolQueue() {
    }

    /**
     * Get the links property: The class to represent a collection of REST reference links.
     * 
     * @return the links value.
     */
    public ReferenceLinks links() {
        return this.links;
    }

    /**
     * Set the links property: The class to represent a collection of REST reference links.
     * 
     * @param links the links value to set.
     * @return the AgentPoolQueue object itself.
     */
    public AgentPoolQueue withLinks(ReferenceLinks links) {
        this.links = links;
        return this;
    }

    /**
     * Get the id property: The ID of the queue.
     * 
     * @return the id value.
     */
    public Integer id() {
        return this.id;
    }

    /**
     * Set the id property: The ID of the queue.
     * 
     * @param id the id value to set.
     * @return the AgentPoolQueue object itself.
     */
    public AgentPoolQueue withId(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * Get the name property: The name of the queue.
     * 
     * @return the name value.
     */
    public String name() {
        return this.name;
    }

    /**
     * Set the name property: The name of the queue.
     * 
     * @param name the name value to set.
     * @return the AgentPoolQueue object itself.
     */
    public AgentPoolQueue withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get the pool property: The pool used by this queue.
     * 
     * @return the pool value.
     */
    public TaskAgentPoolReference pool() {
        return this.pool;
    }

    /**
     * Set the pool property: The pool used by this queue.
     * 
     * @param pool the pool value to set.
     * @return the AgentPoolQueue object itself.
     */
    public AgentPoolQueue withPool(TaskAgentPoolReference pool) {
        this.pool = pool;
        return this;
    }

    /**
     * Get the url property: The full http link to the resource.
     * 
     * @return the url value.
     */
    public String url() {
        return this.url;
    }

    /**
     * Set the url property: The full http link to the resource.
     * 
     * @param url the url value to set.
     * @return the AgentPoolQueue object itself.
     */
    public AgentPoolQueue withUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * Validates the instance.
     * 
     * @throws IllegalArgumentException thrown if the instance is not valid.
     */
    public void validate() {
        if (links() != null) {
            links().validate();
        }
        if (pool() != null) {
            pool().validate();
        }
    }
}
