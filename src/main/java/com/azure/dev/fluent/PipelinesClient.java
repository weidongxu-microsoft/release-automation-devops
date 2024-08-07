// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.fluent;

import com.azure.core.annotation.ReturnType;
import com.azure.core.annotation.ServiceMethod;
import com.azure.core.http.rest.PagedIterable;
import com.azure.core.http.rest.Response;
import com.azure.core.util.Context;
import com.azure.dev.fluent.models.PipelineInner;
import com.azure.dev.models.CreatePipelineParameters;

/**
 * An instance of this class provides access to all the operations defined in PipelinesClient.
 */
public interface PipelinesClient {
    /**
     * Create a pipeline.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param body Input parameters.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return definition of a pipeline along with {@link Response}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Response<PipelineInner> createWithResponse(String organization, String project, CreatePipelineParameters body,
        Context context);

    /**
     * Create a pipeline.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param body Input parameters.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return definition of a pipeline.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    PipelineInner create(String organization, String project, CreatePipelineParameters body);

    /**
     * Get a list of pipelines.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a list of pipelines as paginated response with {@link PagedIterable}.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    PagedIterable<PipelineInner> list(String organization, String project);

    /**
     * Get a list of pipelines.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param orderBy A sort expression. Defaults to "name asc".
     * @param top The maximum number of pipelines to return.
     * @param continuationToken A continuation token from a previous request, to retrieve the next page of results.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a list of pipelines as paginated response with {@link PagedIterable}.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    PagedIterable<PipelineInner> list(String organization, String project, String orderBy, Integer top,
        String continuationToken, Context context);

    /**
     * Gets a pipeline, optionally at the specified version.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param pipelineId The pipeline ID.
     * @param pipelineVersion The pipeline version.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a pipeline, optionally at the specified version along with {@link Response}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Response<PipelineInner> getWithResponse(String organization, String project, int pipelineId,
        Integer pipelineVersion, Context context);

    /**
     * Gets a pipeline, optionally at the specified version.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param pipelineId The pipeline ID.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a pipeline, optionally at the specified version.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    PipelineInner get(String organization, String project, int pipelineId);
}
