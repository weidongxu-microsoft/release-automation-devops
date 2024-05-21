// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.models;

import com.azure.dev.fluent.models.BuildLogInner;
import java.time.OffsetDateTime;

/**
 * An immutable client-side representation of BuildLog.
 */
public interface BuildLog {
    /**
     * Gets the id property: The ID of the log.
     * 
     * @return the id value.
     */
    Integer id();

    /**
     * Gets the type property: The type of the log location.
     * 
     * @return the type value.
     */
    String type();

    /**
     * Gets the url property: A full link to the log resource.
     * 
     * @return the url value.
     */
    String url();

    /**
     * Gets the createdOn property: The date and time the log was created.
     * 
     * @return the createdOn value.
     */
    OffsetDateTime createdOn();

    /**
     * Gets the lastChangedOn property: The date and time the log was last changed.
     * 
     * @return the lastChangedOn value.
     */
    OffsetDateTime lastChangedOn();

    /**
     * Gets the lineCount property: The number of lines in the log.
     * 
     * @return the lineCount value.
     */
    Long lineCount();

    /**
     * Gets the inner com.azure.dev.fluent.models.BuildLogInner object.
     * 
     * @return the inner object.
     */
    BuildLogInner innerModel();
}
