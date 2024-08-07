// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.models;

import com.azure.dev.fluent.models.RetentionLeaseInner;
import java.time.OffsetDateTime;

/**
 * An immutable client-side representation of RetentionLease.
 */
public interface RetentionLease {
    /**
     * Gets the createdOn property: When the lease was created.
     * 
     * @return the createdOn value.
     */
    OffsetDateTime createdOn();

    /**
     * Gets the definitionId property: The pipeline definition of the run.
     * 
     * @return the definitionId value.
     */
    Integer definitionId();

    /**
     * Gets the leaseId property: The unique identifier for this lease.
     * 
     * @return the leaseId value.
     */
    Integer leaseId();

    /**
     * Gets the ownerId property: Non-unique string that identifies the owner of a retention lease.
     * 
     * @return the ownerId value.
     */
    String ownerId();

    /**
     * Gets the protectPipeline property: If set, this lease will also prevent the pipeline from being deleted while the
     * lease is still valid.
     * 
     * @return the protectPipeline value.
     */
    Boolean protectPipeline();

    /**
     * Gets the runId property: The pipeline run protected by this lease.
     * 
     * @return the runId value.
     */
    Integer runId();

    /**
     * Gets the validUntil property: The last day the lease is considered valid.
     * 
     * @return the validUntil value.
     */
    OffsetDateTime validUntil();

    /**
     * Gets the inner com.azure.dev.fluent.models.RetentionLeaseInner object.
     * 
     * @return the inner object.
     */
    RetentionLeaseInner innerModel();
}
