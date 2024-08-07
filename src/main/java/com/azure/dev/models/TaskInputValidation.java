// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The TaskInputValidation model.
 */
@Fluent
public final class TaskInputValidation {
    /*
     * Conditional expression
     */
    @JsonProperty(value = "expression")
    private String expression;

    /*
     * Message explaining how user can correct if validation fails
     */
    @JsonProperty(value = "message")
    private String message;

    /**
     * Creates an instance of TaskInputValidation class.
     */
    public TaskInputValidation() {
    }

    /**
     * Get the expression property: Conditional expression.
     * 
     * @return the expression value.
     */
    public String expression() {
        return this.expression;
    }

    /**
     * Set the expression property: Conditional expression.
     * 
     * @param expression the expression value to set.
     * @return the TaskInputValidation object itself.
     */
    public TaskInputValidation withExpression(String expression) {
        this.expression = expression;
        return this;
    }

    /**
     * Get the message property: Message explaining how user can correct if validation fails.
     * 
     * @return the message value.
     */
    public String message() {
        return this.message;
    }

    /**
     * Set the message property: Message explaining how user can correct if validation fails.
     * 
     * @param message the message value to set.
     * @return the TaskInputValidation object itself.
     */
    public TaskInputValidation withMessage(String message) {
        this.message = message;
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
