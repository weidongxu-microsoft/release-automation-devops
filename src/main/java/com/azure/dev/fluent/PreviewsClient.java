// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.fluent;

import com.azure.core.annotation.ReturnType;
import com.azure.core.annotation.ServiceMethod;
import com.azure.core.http.rest.Response;
import com.azure.core.util.Context;
import com.azure.dev.fluent.models.PreviewRunInner;
import com.azure.dev.models.RunPipelineParameters;

/**
 * An instance of this class provides access to all the operations defined in PreviewsClient.
 */
public interface PreviewsClient {
    /**
     * Queues a dry run of the pipeline and returns an object containing the final yaml.
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
    @ServiceMethod(returns = ReturnType.SINGLE)
    Response<PreviewRunInner> previewWithResponse(String organization, String project, int pipelineId,
        RunPipelineParameters body, Integer pipelineVersion, Context context);

    /**
     * Queues a dry run of the pipeline and returns an object containing the final yaml.
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
    @ServiceMethod(returns = ReturnType.SINGLE)
    PreviewRunInner preview(String organization, String project, int pipelineId, RunPipelineParameters body);
}
