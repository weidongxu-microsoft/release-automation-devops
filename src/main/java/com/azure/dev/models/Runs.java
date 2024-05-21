// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.models;

import com.azure.core.http.rest.PagedIterable;
import com.azure.core.http.rest.Response;
import com.azure.core.util.Context;

/**
 * Resource collection API of Runs.
 */
public interface Runs {
    /**
     * Gets top 10000 runs for a particular pipeline.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param pipelineId The pipeline id.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return top 10000 runs for a particular pipeline as paginated response with {@link PagedIterable}.
     */
    PagedIterable<Run> list(String organization, String project, int pipelineId);

    /**
     * Gets top 10000 runs for a particular pipeline.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param pipelineId The pipeline id.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return top 10000 runs for a particular pipeline as paginated response with {@link PagedIterable}.
     */
    PagedIterable<Run> list(String organization, String project, int pipelineId, Context context);

    /**
     * Runs a pipeline.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param pipelineId The pipeline ID.
     * @param body Optional additional parameters for this run.
     * @param pipelineVersion The pipeline version.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response body along with {@link Response}.
     */
    Response<Run> runPipelineWithResponse(String organization, String project, int pipelineId,
        RunPipelineParameters body, Integer pipelineVersion, Context context);

    /**
     * Runs a pipeline.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param pipelineId The pipeline ID.
     * @param body Optional additional parameters for this run.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    Run runPipeline(String organization, String project, int pipelineId, RunPipelineParameters body);

    /**
     * Gets a run for a particular pipeline.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param pipelineId The pipeline id.
     * @param runId The run id.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a run for a particular pipeline along with {@link Response}.
     */
    Response<Run> getWithResponse(String organization, String project, int pipelineId, int runId, Context context);

    /**
     * Gets a run for a particular pipeline.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param pipelineId The pipeline id.
     * @param runId The run id.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a run for a particular pipeline.
     */
    Run get(String organization, String project, int pipelineId, int runId);
}
