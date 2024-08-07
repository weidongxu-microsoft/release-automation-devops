// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.implementation;

import com.azure.core.annotation.ExpectedResponses;
import com.azure.core.annotation.Get;
import com.azure.core.annotation.HeaderParam;
import com.azure.core.annotation.Headers;
import com.azure.core.annotation.Host;
import com.azure.core.annotation.HostParam;
import com.azure.core.annotation.PathParam;
import com.azure.core.annotation.QueryParam;
import com.azure.core.annotation.ReturnType;
import com.azure.core.annotation.ServiceInterface;
import com.azure.core.annotation.ServiceMethod;
import com.azure.core.annotation.UnexpectedResponseExceptionType;
import com.azure.core.http.rest.Response;
import com.azure.core.http.rest.RestProxy;
import com.azure.core.management.exception.ManagementException;
import com.azure.core.util.Context;
import com.azure.core.util.FluxUtil;
import com.azure.dev.fluent.LogsClient;
import com.azure.dev.fluent.models.LogCollectionInner;
import com.azure.dev.fluent.models.LogInner;
import com.azure.dev.models.GetLogExpandOptions;
import reactor.core.publisher.Mono;

/**
 * An instance of this class provides access to all the operations defined in LogsClient.
 */
public final class LogsClientImpl implements LogsClient {
    /**
     * The proxy service used to perform REST calls.
     */
    private final LogsService service;

    /**
     * The service client containing this operation class.
     */
    private final DevClientImpl client;

    /**
     * Initializes an instance of LogsClientImpl.
     * 
     * @param client the instance of the service client containing this operation class.
     */
    LogsClientImpl(DevClientImpl client) {
        this.service = RestProxy.create(LogsService.class, client.getHttpPipeline(), client.getSerializerAdapter());
        this.client = client;
    }

    /**
     * The interface defining all the services for DevClientLogs to be used by the proxy service to perform REST calls.
     */
    @Host("{$host}")
    @ServiceInterface(name = "DevClientLogs")
    public interface LogsService {
        @Headers({ "Content-Type: application/json" })
        @Get("/{organization}/{project}/_apis/pipelines/{pipelineId}/runs/{runId}/logs")
        @ExpectedResponses({ 200 })
        @UnexpectedResponseExceptionType(ManagementException.class)
        Mono<Response<LogCollectionInner>> list(@HostParam("$host") String endpoint,
            @PathParam("organization") String organization, @PathParam("project") String project,
            @PathParam("pipelineId") int pipelineId, @PathParam("runId") int runId,
            @QueryParam("$expand") GetLogExpandOptions expand, @QueryParam("api-version") String apiVersion,
            @HeaderParam("Accept") String accept, Context context);

        @Headers({ "Content-Type: application/json" })
        @Get("/{organization}/{project}/_apis/pipelines/{pipelineId}/runs/{runId}/logs/{logId}")
        @ExpectedResponses({ 200 })
        @UnexpectedResponseExceptionType(ManagementException.class)
        Mono<Response<LogInner>> get(@HostParam("$host") String endpoint,
            @PathParam("organization") String organization, @PathParam("project") String project,
            @PathParam("pipelineId") int pipelineId, @PathParam("runId") int runId, @PathParam("logId") int logId,
            @QueryParam("$expand") GetLogExpandOptions expand, @QueryParam("api-version") String apiVersion,
            @HeaderParam("Accept") String accept, Context context);
    }

    /**
     * Get a list of logs from a pipeline run.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param pipelineId ID of the pipeline.
     * @param runId ID of the run of that pipeline.
     * @param expand Expand options. Default is None.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a list of logs from a pipeline run along with {@link Response} on successful completion of {@link Mono}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    private Mono<Response<LogCollectionInner>> listWithResponseAsync(String organization, String project,
        int pipelineId, int runId, GetLogExpandOptions expand) {
        if (this.client.getEndpoint() == null) {
            return Mono.error(
                new IllegalArgumentException("Parameter this.client.getEndpoint() is required and cannot be null."));
        }
        if (organization == null) {
            return Mono.error(new IllegalArgumentException("Parameter organization is required and cannot be null."));
        }
        if (project == null) {
            return Mono.error(new IllegalArgumentException("Parameter project is required and cannot be null."));
        }
        final String accept = "application/json";
        return FluxUtil
            .withContext(context -> service.list(this.client.getEndpoint(), organization, project, pipelineId, runId,
                expand, this.client.getApiVersion(), accept, context))
            .contextWrite(context -> context.putAll(FluxUtil.toReactorContext(this.client.getContext()).readOnly()));
    }

    /**
     * Get a list of logs from a pipeline run.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param pipelineId ID of the pipeline.
     * @param runId ID of the run of that pipeline.
     * @param expand Expand options. Default is None.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a list of logs from a pipeline run along with {@link Response} on successful completion of {@link Mono}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    private Mono<Response<LogCollectionInner>> listWithResponseAsync(String organization, String project,
        int pipelineId, int runId, GetLogExpandOptions expand, Context context) {
        if (this.client.getEndpoint() == null) {
            return Mono.error(
                new IllegalArgumentException("Parameter this.client.getEndpoint() is required and cannot be null."));
        }
        if (organization == null) {
            return Mono.error(new IllegalArgumentException("Parameter organization is required and cannot be null."));
        }
        if (project == null) {
            return Mono.error(new IllegalArgumentException("Parameter project is required and cannot be null."));
        }
        final String accept = "application/json";
        context = this.client.mergeContext(context);
        return service.list(this.client.getEndpoint(), organization, project, pipelineId, runId, expand,
            this.client.getApiVersion(), accept, context);
    }

    /**
     * Get a list of logs from a pipeline run.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param pipelineId ID of the pipeline.
     * @param runId ID of the run of that pipeline.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a list of logs from a pipeline run on successful completion of {@link Mono}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    private Mono<LogCollectionInner> listAsync(String organization, String project, int pipelineId, int runId) {
        final GetLogExpandOptions expand = null;
        return listWithResponseAsync(organization, project, pipelineId, runId, expand)
            .flatMap(res -> Mono.justOrEmpty(res.getValue()));
    }

    /**
     * Get a list of logs from a pipeline run.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param pipelineId ID of the pipeline.
     * @param runId ID of the run of that pipeline.
     * @param expand Expand options. Default is None.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a list of logs from a pipeline run along with {@link Response}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<LogCollectionInner> listWithResponse(String organization, String project, int pipelineId, int runId,
        GetLogExpandOptions expand, Context context) {
        return listWithResponseAsync(organization, project, pipelineId, runId, expand, context).block();
    }

    /**
     * Get a list of logs from a pipeline run.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param pipelineId ID of the pipeline.
     * @param runId ID of the run of that pipeline.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a list of logs from a pipeline run.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public LogCollectionInner list(String organization, String project, int pipelineId, int runId) {
        final GetLogExpandOptions expand = null;
        return listWithResponse(organization, project, pipelineId, runId, expand, Context.NONE).getValue();
    }

    /**
     * Get a specific log from a pipeline run.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param pipelineId ID of the pipeline.
     * @param runId ID of the run of that pipeline.
     * @param logId ID of the log.
     * @param expand Expand options. Default is None.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a specific log from a pipeline run along with {@link Response} on successful completion of {@link Mono}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    private Mono<Response<LogInner>> getWithResponseAsync(String organization, String project, int pipelineId,
        int runId, int logId, GetLogExpandOptions expand) {
        if (this.client.getEndpoint() == null) {
            return Mono.error(
                new IllegalArgumentException("Parameter this.client.getEndpoint() is required and cannot be null."));
        }
        if (organization == null) {
            return Mono.error(new IllegalArgumentException("Parameter organization is required and cannot be null."));
        }
        if (project == null) {
            return Mono.error(new IllegalArgumentException("Parameter project is required and cannot be null."));
        }
        final String accept = "application/json";
        return FluxUtil
            .withContext(context -> service.get(this.client.getEndpoint(), organization, project, pipelineId, runId,
                logId, expand, this.client.getApiVersion(), accept, context))
            .contextWrite(context -> context.putAll(FluxUtil.toReactorContext(this.client.getContext()).readOnly()));
    }

    /**
     * Get a specific log from a pipeline run.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param pipelineId ID of the pipeline.
     * @param runId ID of the run of that pipeline.
     * @param logId ID of the log.
     * @param expand Expand options. Default is None.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a specific log from a pipeline run along with {@link Response} on successful completion of {@link Mono}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    private Mono<Response<LogInner>> getWithResponseAsync(String organization, String project, int pipelineId,
        int runId, int logId, GetLogExpandOptions expand, Context context) {
        if (this.client.getEndpoint() == null) {
            return Mono.error(
                new IllegalArgumentException("Parameter this.client.getEndpoint() is required and cannot be null."));
        }
        if (organization == null) {
            return Mono.error(new IllegalArgumentException("Parameter organization is required and cannot be null."));
        }
        if (project == null) {
            return Mono.error(new IllegalArgumentException("Parameter project is required and cannot be null."));
        }
        final String accept = "application/json";
        context = this.client.mergeContext(context);
        return service.get(this.client.getEndpoint(), organization, project, pipelineId, runId, logId, expand,
            this.client.getApiVersion(), accept, context);
    }

    /**
     * Get a specific log from a pipeline run.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param pipelineId ID of the pipeline.
     * @param runId ID of the run of that pipeline.
     * @param logId ID of the log.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a specific log from a pipeline run on successful completion of {@link Mono}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    private Mono<LogInner> getAsync(String organization, String project, int pipelineId, int runId, int logId) {
        final GetLogExpandOptions expand = null;
        return getWithResponseAsync(organization, project, pipelineId, runId, logId, expand)
            .flatMap(res -> Mono.justOrEmpty(res.getValue()));
    }

    /**
     * Get a specific log from a pipeline run.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param pipelineId ID of the pipeline.
     * @param runId ID of the run of that pipeline.
     * @param logId ID of the log.
     * @param expand Expand options. Default is None.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a specific log from a pipeline run along with {@link Response}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<LogInner> getWithResponse(String organization, String project, int pipelineId, int runId, int logId,
        GetLogExpandOptions expand, Context context) {
        return getWithResponseAsync(organization, project, pipelineId, runId, logId, expand, context).block();
    }

    /**
     * Get a specific log from a pipeline run.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param pipelineId ID of the pipeline.
     * @param runId ID of the run of that pipeline.
     * @param logId ID of the log.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a specific log from a pipeline run.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public LogInner get(String organization, String project, int pipelineId, int runId, int logId) {
        final GetLogExpandOptions expand = null;
        return getWithResponse(organization, project, pipelineId, runId, logId, expand, Context.NONE).getValue();
    }
}
