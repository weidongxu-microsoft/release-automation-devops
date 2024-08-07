// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.fluent;

import com.azure.core.annotation.ReturnType;
import com.azure.core.annotation.ServiceMethod;
import com.azure.core.http.rest.Response;
import com.azure.core.util.Context;
import com.azure.dev.fluent.models.YamlBuildInner;
import java.time.OffsetDateTime;

/**
 * An instance of this class provides access to all the operations defined in YamlsClient.
 */
public interface YamlsClient {
    /**
     * Converts a definition to YAML, optionally at a specific revision.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param definitionId The ID of the definition.
     * @param revision The revision number to retrieve. If this is not specified, the latest version will be returned.
     * @param minMetricsTime If specified, indicates the date from which metrics should be included.
     * @param propertyFilters A comma-delimited list of properties to include in the results.
     * @param includeLatestBuilds The includeLatestBuilds parameter.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return represents a yaml build along with {@link Response}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Response<YamlBuildInner> getWithResponse(String organization, String project, int definitionId, Integer revision,
        OffsetDateTime minMetricsTime, String propertyFilters, Boolean includeLatestBuilds, Context context);

    /**
     * Converts a definition to YAML, optionally at a specific revision.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param definitionId The ID of the definition.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return represents a yaml build.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    YamlBuildInner get(String organization, String project, int definitionId);
}
