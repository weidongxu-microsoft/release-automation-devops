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
import com.azure.dev.fluent.AuthorizedresourcesClient;
import com.azure.dev.fluent.models.DefinitionResourceReferenceInner;
import java.util.List;
import reactor.core.publisher.Mono;

/**
 * An instance of this class provides access to all the operations defined in AuthorizedresourcesClient.
 */
public final class AuthorizedresourcesClientImpl implements AuthorizedresourcesClient {
    /**
     * The proxy service used to perform REST calls.
     */
    private final AuthorizedresourcesService service;

    /**
     * The service client containing this operation class.
     */
    private final DevClientImpl client;

    /**
     * Initializes an instance of AuthorizedresourcesClientImpl.
     * 
     * @param client the instance of the service client containing this operation class.
     */
    AuthorizedresourcesClientImpl(DevClientImpl client) {
        this.service = RestProxy.create(AuthorizedresourcesService.class, client.getHttpPipeline(),
            client.getSerializerAdapter());
        this.client = client;
    }

    /**
     * The interface defining all the services for DevClientAuthorizedresources to be used by the proxy service to
     * perform REST calls.
     */
    @Host("{$host}")
    @ServiceInterface(name = "DevClientAuthorizedr")
    public interface AuthorizedresourcesService {
        @Headers({ "Content-Type: application/json" })
        @Patch("/{organization}/{project}/_apis/build/authorizedresources")
        @ExpectedResponses({ 200 })
        @UnexpectedResponseExceptionType(ManagementException.class)
        Mono<Response<List<DefinitionResourceReferenceInner>>> authorizeProjectResources(
            @HostParam("$host") String endpoint, @PathParam("organization") String organization,
            @PathParam("project") String project, @QueryParam("api-version") String apiVersion,
            @BodyParam("application/json") List<DefinitionResourceReferenceInner> body,
            @HeaderParam("Accept") String accept, Context context);

        @Headers({ "Content-Type: application/json" })
        @Get("/{organization}/{project}/_apis/build/authorizedresources")
        @ExpectedResponses({ 200 })
        @UnexpectedResponseExceptionType(ManagementException.class)
        Mono<Response<List<DefinitionResourceReferenceInner>>> list(@HostParam("$host") String endpoint,
            @PathParam("organization") String organization, @PathParam("project") String project,
            @QueryParam("type") String type, @QueryParam("id") String id, @QueryParam("api-version") String apiVersion,
            @HeaderParam("Accept") String accept, Context context);
    }

    /**
     * The authorizeProjectResources operation.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param body Array of DefinitionResourceReference.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return array of DefinitionResourceReference along with {@link Response} on successful completion of
     * {@link Mono}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    private Mono<Response<List<DefinitionResourceReferenceInner>>> authorizeProjectResourcesWithResponseAsync(
        String organization, String project, List<DefinitionResourceReferenceInner> body) {
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
            body.forEach(e -> e.validate());
        }
        final String accept = "application/json";
        return FluxUtil
            .withContext(context -> service.authorizeProjectResources(this.client.getEndpoint(), organization, project,
                this.client.getApiVersion(), body, accept, context))
            .contextWrite(context -> context.putAll(FluxUtil.toReactorContext(this.client.getContext()).readOnly()));
    }

    /**
     * The authorizeProjectResources operation.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param body Array of DefinitionResourceReference.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return array of DefinitionResourceReference along with {@link Response} on successful completion of
     * {@link Mono}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    private Mono<Response<List<DefinitionResourceReferenceInner>>> authorizeProjectResourcesWithResponseAsync(
        String organization, String project, List<DefinitionResourceReferenceInner> body, Context context) {
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
            body.forEach(e -> e.validate());
        }
        final String accept = "application/json";
        context = this.client.mergeContext(context);
        return service.authorizeProjectResources(this.client.getEndpoint(), organization, project,
            this.client.getApiVersion(), body, accept, context);
    }

    /**
     * The authorizeProjectResources operation.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param body Array of DefinitionResourceReference.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return array of DefinitionResourceReference on successful completion of {@link Mono}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    private Mono<List<DefinitionResourceReferenceInner>> authorizeProjectResourcesAsync(String organization,
        String project, List<DefinitionResourceReferenceInner> body) {
        return authorizeProjectResourcesWithResponseAsync(organization, project, body)
            .flatMap(res -> Mono.justOrEmpty(res.getValue()));
    }

    /**
     * The authorizeProjectResources operation.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param body Array of DefinitionResourceReference.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return array of DefinitionResourceReference along with {@link Response}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<List<DefinitionResourceReferenceInner>> authorizeProjectResourcesWithResponse(String organization,
        String project, List<DefinitionResourceReferenceInner> body, Context context) {
        return authorizeProjectResourcesWithResponseAsync(organization, project, body, context).block();
    }

    /**
     * The authorizeProjectResources operation.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param body Array of DefinitionResourceReference.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return array of DefinitionResourceReference.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public List<DefinitionResourceReferenceInner> authorizeProjectResources(String organization, String project,
        List<DefinitionResourceReferenceInner> body) {
        return authorizeProjectResourcesWithResponse(organization, project, body, Context.NONE).getValue();
    }

    /**
     * The list operation.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param type The type parameter.
     * @param id The id parameter.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return array of DefinitionResourceReference along with {@link Response} on successful completion of
     * {@link Mono}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    private Mono<Response<List<DefinitionResourceReferenceInner>>> listWithResponseAsync(String organization,
        String project, String type, String id) {
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
            .withContext(context -> service.list(this.client.getEndpoint(), organization, project, type, id,
                this.client.getApiVersion(), accept, context))
            .contextWrite(context -> context.putAll(FluxUtil.toReactorContext(this.client.getContext()).readOnly()));
    }

    /**
     * The list operation.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param type The type parameter.
     * @param id The id parameter.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return array of DefinitionResourceReference along with {@link Response} on successful completion of
     * {@link Mono}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    private Mono<Response<List<DefinitionResourceReferenceInner>>> listWithResponseAsync(String organization,
        String project, String type, String id, Context context) {
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
        return service.list(this.client.getEndpoint(), organization, project, type, id, this.client.getApiVersion(),
            accept, context);
    }

    /**
     * The list operation.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return array of DefinitionResourceReference on successful completion of {@link Mono}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    private Mono<List<DefinitionResourceReferenceInner>> listAsync(String organization, String project) {
        final String type = null;
        final String id = null;
        return listWithResponseAsync(organization, project, type, id).flatMap(res -> Mono.justOrEmpty(res.getValue()));
    }

    /**
     * The list operation.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param type The type parameter.
     * @param id The id parameter.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return array of DefinitionResourceReference along with {@link Response}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<List<DefinitionResourceReferenceInner>> listWithResponse(String organization, String project,
        String type, String id, Context context) {
        return listWithResponseAsync(organization, project, type, id, context).block();
    }

    /**
     * The list operation.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return array of DefinitionResourceReference.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public List<DefinitionResourceReferenceInner> list(String organization, String project) {
        final String type = null;
        final String id = null;
        return listWithResponse(organization, project, type, id, Context.NONE).getValue();
    }
}
