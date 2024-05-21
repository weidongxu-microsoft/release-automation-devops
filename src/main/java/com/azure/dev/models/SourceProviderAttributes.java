// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.models;

import com.azure.dev.fluent.models.SourceProviderAttributesInner;
import java.util.List;
import java.util.Map;

/**
 * An immutable client-side representation of SourceProviderAttributes.
 */
public interface SourceProviderAttributes {
    /**
     * Gets the name property: The name of the source provider.
     * 
     * @return the name value.
     */
    String name();

    /**
     * Gets the supportedCapabilities property: The capabilities supported by this source provider.
     * 
     * @return the supportedCapabilities value.
     */
    Map<String, Boolean> supportedCapabilities();

    /**
     * Gets the supportedTriggers property: The types of triggers supported by this source provider.
     * 
     * @return the supportedTriggers value.
     */
    List<SupportedTrigger> supportedTriggers();

    /**
     * Gets the inner com.azure.dev.fluent.models.SourceProviderAttributesInner object.
     * 
     * @return the inner object.
     */
    SourceProviderAttributesInner innerModel();
}
