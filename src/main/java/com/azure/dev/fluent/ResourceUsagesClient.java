// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.fluent;

import com.azure.core.annotation.ReturnType;
import com.azure.core.annotation.ServiceMethod;
import com.azure.core.http.rest.Response;
import com.azure.core.util.Context;
import com.azure.dev.fluent.models.BuildResourceUsageInner;

/**
 * An instance of this class provides access to all the operations defined in ResourceUsagesClient.
 */
public interface ResourceUsagesClient {
    /**
     * Gets information about build resources in the system.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return information about build resources in the system along with {@link Response}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Response<BuildResourceUsageInner> getWithResponse(String organization, Context context);

    /**
     * Gets information about build resources in the system.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return information about build resources in the system.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    BuildResourceUsageInner get(String organization);
}
