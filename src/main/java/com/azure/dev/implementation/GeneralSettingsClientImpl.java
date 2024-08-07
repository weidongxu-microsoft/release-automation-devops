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
import com.azure.core.annotation.Patch;
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
import com.azure.dev.fluent.GeneralSettingsClient;
import com.azure.dev.fluent.models.PipelineGeneralSettingsInner;
import reactor.core.publisher.Mono;

/**
 * An instance of this class provides access to all the operations defined in GeneralSettingsClient.
 */
public final class GeneralSettingsClientImpl implements GeneralSettingsClient {
    /**
     * The proxy service used to perform REST calls.
     */
    private final GeneralSettingsService service;

    /**
     * The service client containing this operation class.
     */
    private final DevClientImpl client;

    /**
     * Initializes an instance of GeneralSettingsClientImpl.
     * 
     * @param client the instance of the service client containing this operation class.
     */
    GeneralSettingsClientImpl(DevClientImpl client) {
        this.service
            = RestProxy.create(GeneralSettingsService.class, client.getHttpPipeline(), client.getSerializerAdapter());
        this.client = client;
    }

    /**
     * The interface defining all the services for DevClientGeneralSettings to be used by the proxy service to perform
     * REST calls.
     */
    @Host("{$host}")
    @ServiceInterface(name = "DevClientGeneralSett")
    public interface GeneralSettingsService {
        @Headers({ "Content-Type: application/json" })
        @Get("/{organization}/{project}/_apis/build/generalsettings")
        @ExpectedResponses({ 200 })
        @UnexpectedResponseExceptionType(ManagementException.class)
        Mono<Response<PipelineGeneralSettingsInner>> get(@HostParam("$host") String endpoint,
            @PathParam("organization") String organization, @PathParam("project") String project,
            @QueryParam("api-version") String apiVersion, @HeaderParam("Accept") String accept, Context context);

        @Headers({ "Content-Type: application/json" })
        @Patch("/{organization}/{project}/_apis/build/generalsettings")
        @ExpectedResponses({ 200 })
        @UnexpectedResponseExceptionType(ManagementException.class)
        Mono<Response<PipelineGeneralSettingsInner>> update(@HostParam("$host") String endpoint,
            @PathParam("organization") String organization, @PathParam("project") String project,
            @QueryParam("api-version") String apiVersion,
            @BodyParam("application/json") PipelineGeneralSettingsInner body, @HeaderParam("Accept") String accept,
            Context context);
    }

    /**
     * Gets pipeline general settings.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return pipeline general settings along with {@link Response} on successful completion of {@link Mono}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    private Mono<Response<PipelineGeneralSettingsInner>> getWithResponseAsync(String organization, String project) {
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
            .withContext(context -> service.get(this.client.getEndpoint(), organization, project,
                this.client.getApiVersion(), accept, context))
            .contextWrite(context -> context.putAll(FluxUtil.toReactorContext(this.client.getContext()).readOnly()));
    }

    /**
     * Gets pipeline general settings.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return pipeline general settings along with {@link Response} on successful completion of {@link Mono}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    private Mono<Response<PipelineGeneralSettingsInner>> getWithResponseAsync(String organization, String project,
        Context context) {
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
        return service.get(this.client.getEndpoint(), organization, project, this.client.getApiVersion(), accept,
            context);
    }

    /**
     * Gets pipeline general settings.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return pipeline general settings on successful completion of {@link Mono}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    private Mono<PipelineGeneralSettingsInner> getAsync(String organization, String project) {
        return getWithResponseAsync(organization, project).flatMap(res -> Mono.justOrEmpty(res.getValue()));
    }

    /**
     * Gets pipeline general settings.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return pipeline general settings along with {@link Response}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<PipelineGeneralSettingsInner> getWithResponse(String organization, String project,
        Context context) {
        return getWithResponseAsync(organization, project, context).block();
    }

    /**
     * Gets pipeline general settings.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return pipeline general settings.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public PipelineGeneralSettingsInner get(String organization, String project) {
        return getWithResponse(organization, project, Context.NONE).getValue();
    }

    /**
     * Updates pipeline general settings.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param body Contains pipeline general settings.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return contains pipeline general settings along with {@link Response} on successful completion of {@link Mono}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    private Mono<Response<PipelineGeneralSettingsInner>> updateWithResponseAsync(String organization, String project,
        PipelineGeneralSettingsInner body) {
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
        if (body == null) {
            return Mono.error(new IllegalArgumentException("Parameter body is required and cannot be null."));
        } else {
            body.validate();
        }
        final String accept = "application/json";
        return FluxUtil
            .withContext(context -> service.update(this.client.getEndpoint(), organization, project,
                this.client.getApiVersion(), body, accept, context))
            .contextWrite(context -> context.putAll(FluxUtil.toReactorContext(this.client.getContext()).readOnly()));
    }

    /**
     * Updates pipeline general settings.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param body Contains pipeline general settings.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return contains pipeline general settings along with {@link Response} on successful completion of {@link Mono}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    private Mono<Response<PipelineGeneralSettingsInner>> updateWithResponseAsync(String organization, String project,
        PipelineGeneralSettingsInner body, Context context) {
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
        if (body == null) {
            return Mono.error(new IllegalArgumentException("Parameter body is required and cannot be null."));
        } else {
            body.validate();
        }
        final String accept = "application/json";
        context = this.client.mergeContext(context);
        return service.update(this.client.getEndpoint(), organization, project, this.client.getApiVersion(), body,
            accept, context);
    }

    /**
     * Updates pipeline general settings.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param body Contains pipeline general settings.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return contains pipeline general settings on successful completion of {@link Mono}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    private Mono<PipelineGeneralSettingsInner> updateAsync(String organization, String project,
        PipelineGeneralSettingsInner body) {
        return updateWithResponseAsync(organization, project, body).flatMap(res -> Mono.justOrEmpty(res.getValue()));
    }

    /**
     * Updates pipeline general settings.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param body Contains pipeline general settings.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return contains pipeline general settings along with {@link Response}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<PipelineGeneralSettingsInner> updateWithResponse(String organization, String project,
        PipelineGeneralSettingsInner body, Context context) {
        return updateWithResponseAsync(organization, project, body, context).block();
    }

    /**
     * Updates pipeline general settings.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param body Contains pipeline general settings.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return contains pipeline general settings.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public PipelineGeneralSettingsInner update(String organization, String project, PipelineGeneralSettingsInner body) {
        return updateWithResponse(organization, project, body, Context.NONE).getValue();
    }
}
