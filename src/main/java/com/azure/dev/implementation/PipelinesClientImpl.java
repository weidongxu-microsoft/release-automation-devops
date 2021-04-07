// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.implementation;

import com.azure.core.annotation.BodyParam;
import com.azure.core.annotation.ExpectedResponses;
import com.azure.core.annotation.Get;
import com.azure.core.annotation.HeaderParam;
import com.azure.core.annotation.Headers;
import com.azure.core.annotation.Host;
import com.azure.core.annotation.HostParam;
import com.azure.core.annotation.PathParam;
import com.azure.core.annotation.Post;
import com.azure.core.annotation.QueryParam;
import com.azure.core.annotation.ReturnType;
import com.azure.core.annotation.ServiceInterface;
import com.azure.core.annotation.ServiceMethod;
import com.azure.core.annotation.UnexpectedResponseExceptionType;
import com.azure.core.http.rest.PagedFlux;
import com.azure.core.http.rest.PagedIterable;
import com.azure.core.http.rest.PagedResponse;
import com.azure.core.http.rest.PagedResponseBase;
import com.azure.core.http.rest.Response;
import com.azure.core.http.rest.RestProxy;
import com.azure.core.management.exception.ManagementException;
import com.azure.core.util.Context;
import com.azure.core.util.FluxUtil;
import com.azure.core.util.logging.ClientLogger;
import com.azure.dev.fluent.PipelinesClient;
import com.azure.dev.fluent.models.PipelineInner;
import com.azure.dev.models.CreatePipelineParameters;
import com.azure.dev.models.PipelineListResult;
import reactor.core.publisher.Mono;

/** An instance of this class provides access to all the operations defined in PipelinesClient. */
public final class PipelinesClientImpl implements PipelinesClient {
    private final ClientLogger logger = new ClientLogger(PipelinesClientImpl.class);

    /** The proxy service used to perform REST calls. */
    private final PipelinesService service;

    /** The service client containing this operation class. */
    private final DevClientImpl client;

    /**
     * Initializes an instance of PipelinesClientImpl.
     *
     * @param client the instance of the service client containing this operation class.
     */
    PipelinesClientImpl(DevClientImpl client) {
        this.service =
            RestProxy.create(PipelinesService.class, client.getHttpPipeline(), client.getSerializerAdapter());
        this.client = client;
    }

    /**
     * The interface defining all the services for DevClientPipelines to be used by the proxy service to perform REST
     * calls.
     */
    @Host("{$host}")
    @ServiceInterface(name = "DevClientPipelines")
    private interface PipelinesService {
        @Headers({"Content-Type: application/json"})
        @Post("/{organization}/{project}/_apis/pipelines")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(ManagementException.class)
        Mono<Response<PipelineInner>> create(
            @HostParam("$host") String endpoint,
            @PathParam("organization") String organization,
            @PathParam("project") String project,
            @QueryParam("api-version") String apiVersion,
            @BodyParam("application/json") CreatePipelineParameters body,
            @HeaderParam("Accept") String accept,
            Context context);

        @Headers({"Content-Type: application/json"})
        @Get("/{organization}/{project}/_apis/pipelines")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(ManagementException.class)
        Mono<Response<PipelineListResult>> list(
            @HostParam("$host") String endpoint,
            @PathParam("organization") String organization,
            @PathParam("project") String project,
            @QueryParam("orderBy") String orderBy,
            @QueryParam("$top") Integer top,
            @QueryParam("continuationToken") String continuationToken,
            @QueryParam("api-version") String apiVersion,
            @HeaderParam("Accept") String accept,
            Context context);

        @Headers({"Content-Type: application/json"})
        @Get("/{organization}/{project}/_apis/pipelines/{pipelineId}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(ManagementException.class)
        Mono<Response<PipelineInner>> get(
            @HostParam("$host") String endpoint,
            @PathParam("organization") String organization,
            @PathParam("project") String project,
            @PathParam("pipelineId") int pipelineId,
            @QueryParam("pipelineVersion") Integer pipelineVersion,
            @QueryParam("api-version") String apiVersion,
            @HeaderParam("Accept") String accept,
            Context context);
    }

    /**
     * Create a pipeline.
     *
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param body Input parameters.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return definition of a pipeline.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    private Mono<Response<PipelineInner>> createWithResponseAsync(
        String organization, String project, CreatePipelineParameters body) {
        if (this.client.getEndpoint() == null) {
            return Mono
                .error(
                    new IllegalArgumentException(
                        "Parameter this.client.getEndpoint() is required and cannot be null."));
        }
        if (organization == null) {
            return Mono.error(new IllegalArgumentException("Parameter organization is required and cannot be null."));
        }
        if (project == null) {
            return Mono.error(new IllegalArgumentException("Parameter project is required and cannot be null."));
        }
        if (body == null) {
            return Mono.error(new IllegalArgumentException("Parameter body is required and cannot be null."));
        } else {
            body.validate();
        }
        final String apiVersion = "6.0-preview";
        final String accept = "application/json";
        return FluxUtil
            .withContext(
                context ->
                    service.create(this.client.getEndpoint(), organization, project, apiVersion, body, accept, context))
            .contextWrite(context -> context.putAll(FluxUtil.toReactorContext(this.client.getContext()).readOnly()));
    }

    /**
     * Create a pipeline.
     *
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param body Input parameters.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return definition of a pipeline.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    private Mono<Response<PipelineInner>> createWithResponseAsync(
        String organization, String project, CreatePipelineParameters body, Context context) {
        if (this.client.getEndpoint() == null) {
            return Mono
                .error(
                    new IllegalArgumentException(
                        "Parameter this.client.getEndpoint() is required and cannot be null."));
        }
        if (organization == null) {
            return Mono.error(new IllegalArgumentException("Parameter organization is required and cannot be null."));
        }
        if (project == null) {
            return Mono.error(new IllegalArgumentException("Parameter project is required and cannot be null."));
        }
        if (body == null) {
            return Mono.error(new IllegalArgumentException("Parameter body is required and cannot be null."));
        } else {
            body.validate();
        }
        final String apiVersion = "6.0-preview";
        final String accept = "application/json";
        context = this.client.mergeContext(context);
        return service.create(this.client.getEndpoint(), organization, project, apiVersion, body, accept, context);
    }

    /**
     * Create a pipeline.
     *
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param body Input parameters.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return definition of a pipeline.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    private Mono<PipelineInner> createAsync(String organization, String project, CreatePipelineParameters body) {
        return createWithResponseAsync(organization, project, body)
            .flatMap(
                (Response<PipelineInner> res) -> {
                    if (res.getValue() != null) {
                        return Mono.just(res.getValue());
                    } else {
                        return Mono.empty();
                    }
                });
    }

    /**
     * Create a pipeline.
     *
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param body Input parameters.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return definition of a pipeline.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public PipelineInner create(String organization, String project, CreatePipelineParameters body) {
        return createAsync(organization, project, body).block();
    }

    /**
     * Create a pipeline.
     *
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param body Input parameters.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return definition of a pipeline.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<PipelineInner> createWithResponse(
        String organization, String project, CreatePipelineParameters body, Context context) {
        return createWithResponseAsync(organization, project, body, context).block();
    }

    /**
     * Get a list of pipelines.
     *
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param orderBy A sort expression. Defaults to "name asc".
     * @param top The maximum number of pipelines to return.
     * @param continuationToken A continuation token from a previous request, to retrieve the next page of results.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a list of pipelines.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    private Mono<PagedResponse<PipelineInner>> listSinglePageAsync(
        String organization, String project, String orderBy, Integer top, String continuationToken) {
        if (this.client.getEndpoint() == null) {
            return Mono
                .error(
                    new IllegalArgumentException(
                        "Parameter this.client.getEndpoint() is required and cannot be null."));
        }
        if (organization == null) {
            return Mono.error(new IllegalArgumentException("Parameter organization is required and cannot be null."));
        }
        if (project == null) {
            return Mono.error(new IllegalArgumentException("Parameter project is required and cannot be null."));
        }
        final String apiVersion = "6.0-preview";
        final String accept = "application/json";
        return FluxUtil
            .withContext(
                context ->
                    service
                        .list(
                            this.client.getEndpoint(),
                            organization,
                            project,
                            orderBy,
                            top,
                            continuationToken,
                            apiVersion,
                            accept,
                            context))
            .<PagedResponse<PipelineInner>>map(
                res ->
                    new PagedResponseBase<>(
                        res.getRequest(), res.getStatusCode(), res.getHeaders(), res.getValue().value(), null, null))
            .contextWrite(context -> context.putAll(FluxUtil.toReactorContext(this.client.getContext()).readOnly()));
    }

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
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a list of pipelines.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    private Mono<PagedResponse<PipelineInner>> listSinglePageAsync(
        String organization, String project, String orderBy, Integer top, String continuationToken, Context context) {
        if (this.client.getEndpoint() == null) {
            return Mono
                .error(
                    new IllegalArgumentException(
                        "Parameter this.client.getEndpoint() is required and cannot be null."));
        }
        if (organization == null) {
            return Mono.error(new IllegalArgumentException("Parameter organization is required and cannot be null."));
        }
        if (project == null) {
            return Mono.error(new IllegalArgumentException("Parameter project is required and cannot be null."));
        }
        final String apiVersion = "6.0-preview";
        final String accept = "application/json";
        context = this.client.mergeContext(context);
        return service
            .list(
                this.client.getEndpoint(),
                organization,
                project,
                orderBy,
                top,
                continuationToken,
                apiVersion,
                accept,
                context)
            .map(
                res ->
                    new PagedResponseBase<>(
                        res.getRequest(), res.getStatusCode(), res.getHeaders(), res.getValue().value(), null, null));
    }

    /**
     * Get a list of pipelines.
     *
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param orderBy A sort expression. Defaults to "name asc".
     * @param top The maximum number of pipelines to return.
     * @param continuationToken A continuation token from a previous request, to retrieve the next page of results.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a list of pipelines.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    private PagedFlux<PipelineInner> listAsync(
        String organization, String project, String orderBy, Integer top, String continuationToken) {
        return new PagedFlux<>(() -> listSinglePageAsync(organization, project, orderBy, top, continuationToken));
    }

    /**
     * Get a list of pipelines.
     *
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a list of pipelines.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    private PagedFlux<PipelineInner> listAsync(String organization, String project) {
        final String orderBy = null;
        final Integer top = null;
        final String continuationToken = null;
        return new PagedFlux<>(() -> listSinglePageAsync(organization, project, orderBy, top, continuationToken));
    }

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
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a list of pipelines.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    private PagedFlux<PipelineInner> listAsync(
        String organization, String project, String orderBy, Integer top, String continuationToken, Context context) {
        return new PagedFlux<>(
            () -> listSinglePageAsync(organization, project, orderBy, top, continuationToken, context));
    }

    /**
     * Get a list of pipelines.
     *
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a list of pipelines.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedIterable<PipelineInner> list(String organization, String project) {
        final String orderBy = null;
        final Integer top = null;
        final String continuationToken = null;
        return new PagedIterable<>(listAsync(organization, project, orderBy, top, continuationToken));
    }

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
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a list of pipelines.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedIterable<PipelineInner> list(
        String organization, String project, String orderBy, Integer top, String continuationToken, Context context) {
        return new PagedIterable<>(listAsync(organization, project, orderBy, top, continuationToken, context));
    }

    /**
     * Gets a pipeline, optionally at the specified version.
     *
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param pipelineId The pipeline ID.
     * @param pipelineVersion The pipeline version.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a pipeline, optionally at the specified version.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    private Mono<Response<PipelineInner>> getWithResponseAsync(
        String organization, String project, int pipelineId, Integer pipelineVersion) {
        if (this.client.getEndpoint() == null) {
            return Mono
                .error(
                    new IllegalArgumentException(
                        "Parameter this.client.getEndpoint() is required and cannot be null."));
        }
        if (organization == null) {
            return Mono.error(new IllegalArgumentException("Parameter organization is required and cannot be null."));
        }
        if (project == null) {
            return Mono.error(new IllegalArgumentException("Parameter project is required and cannot be null."));
        }
        final String apiVersion = "6.0-preview";
        final String accept = "application/json";
        return FluxUtil
            .withContext(
                context ->
                    service
                        .get(
                            this.client.getEndpoint(),
                            organization,
                            project,
                            pipelineId,
                            pipelineVersion,
                            apiVersion,
                            accept,
                            context))
            .contextWrite(context -> context.putAll(FluxUtil.toReactorContext(this.client.getContext()).readOnly()));
    }

    /**
     * Gets a pipeline, optionally at the specified version.
     *
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param pipelineId The pipeline ID.
     * @param pipelineVersion The pipeline version.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a pipeline, optionally at the specified version.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    private Mono<Response<PipelineInner>> getWithResponseAsync(
        String organization, String project, int pipelineId, Integer pipelineVersion, Context context) {
        if (this.client.getEndpoint() == null) {
            return Mono
                .error(
                    new IllegalArgumentException(
                        "Parameter this.client.getEndpoint() is required and cannot be null."));
        }
        if (organization == null) {
            return Mono.error(new IllegalArgumentException("Parameter organization is required and cannot be null."));
        }
        if (project == null) {
            return Mono.error(new IllegalArgumentException("Parameter project is required and cannot be null."));
        }
        final String apiVersion = "6.0-preview";
        final String accept = "application/json";
        context = this.client.mergeContext(context);
        return service
            .get(
                this.client.getEndpoint(),
                organization,
                project,
                pipelineId,
                pipelineVersion,
                apiVersion,
                accept,
                context);
    }

    /**
     * Gets a pipeline, optionally at the specified version.
     *
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param pipelineId The pipeline ID.
     * @param pipelineVersion The pipeline version.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a pipeline, optionally at the specified version.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    private Mono<PipelineInner> getAsync(String organization, String project, int pipelineId, Integer pipelineVersion) {
        return getWithResponseAsync(organization, project, pipelineId, pipelineVersion)
            .flatMap(
                (Response<PipelineInner> res) -> {
                    if (res.getValue() != null) {
                        return Mono.just(res.getValue());
                    } else {
                        return Mono.empty();
                    }
                });
    }

    /**
     * Gets a pipeline, optionally at the specified version.
     *
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param pipelineId The pipeline ID.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a pipeline, optionally at the specified version.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    private Mono<PipelineInner> getAsync(String organization, String project, int pipelineId) {
        final Integer pipelineVersion = null;
        return getWithResponseAsync(organization, project, pipelineId, pipelineVersion)
            .flatMap(
                (Response<PipelineInner> res) -> {
                    if (res.getValue() != null) {
                        return Mono.just(res.getValue());
                    } else {
                        return Mono.empty();
                    }
                });
    }

    /**
     * Gets a pipeline, optionally at the specified version.
     *
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param pipelineId The pipeline ID.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a pipeline, optionally at the specified version.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public PipelineInner get(String organization, String project, int pipelineId) {
        final Integer pipelineVersion = null;
        return getAsync(organization, project, pipelineId, pipelineVersion).block();
    }

    /**
     * Gets a pipeline, optionally at the specified version.
     *
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param pipelineId The pipeline ID.
     * @param pipelineVersion The pipeline version.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a pipeline, optionally at the specified version.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<PipelineInner> getWithResponse(
        String organization, String project, int pipelineId, Integer pipelineVersion, Context context) {
        return getWithResponseAsync(organization, project, pipelineId, pipelineVersion, context).block();
    }
}
