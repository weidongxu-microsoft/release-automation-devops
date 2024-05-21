// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.models;

import com.azure.dev.fluent.models.ChangeInner;
import java.time.OffsetDateTime;

/**
 * An immutable client-side representation of Change.
 */
public interface Change {
    /**
     * Gets the author property: The author of the change.
     * 
     * @return the author value.
     */
    IdentityRef author();

    /**
     * Gets the displayUri property: The location of a user-friendly representation of the resource.
     * 
     * @return the displayUri value.
     */
    String displayUri();

    /**
     * Gets the id property: The identifier for the change. For a commit, this would be the SHA1. For a TFVC changeset,
     * this would be the changeset ID.
     * 
     * @return the id value.
     */
    String id();

    /**
     * Gets the location property: The location of the full representation of the resource.
     * 
     * @return the location value.
     */
    String location();

    /**
     * Gets the message property: The description of the change. This might be a commit message or changeset
     * description.
     * 
     * @return the message value.
     */
    String message();

    /**
     * Gets the messageTruncated property: Indicates whether the message was truncated.
     * 
     * @return the messageTruncated value.
     */
    Boolean messageTruncated();

    /**
     * Gets the pusher property: The person or process that pushed the change.
     * 
     * @return the pusher value.
     */
    String pusher();

    /**
     * Gets the timestamp property: The timestamp for the change.
     * 
     * @return the timestamp value.
     */
    OffsetDateTime timestamp();

    /**
     * Gets the type property: The type of change. "commit", "changeset", etc.
     * 
     * @return the type value.
     */
    String type();

    /**
     * Gets the inner com.azure.dev.fluent.models.ChangeInner object.
     * 
     * @return the inner object.
     */
    ChangeInner innerModel();
}
