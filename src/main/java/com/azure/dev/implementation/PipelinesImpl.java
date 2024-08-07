// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.implementation;

import com.azure.core.http.rest.PagedIterable;
import com.azure.core.http.rest.Response;
import com.azure.core.http.rest.SimpleResponse;
import com.azure.core.util.Context;
import com.azure.core.util.logging.ClientLogger;
import com.azure.dev.fluent.PipelinesClient;
import com.azure.dev.fluent.models.PipelineInner;
import com.azure.dev.models.CreatePipelineParameters;
import com.azure.dev.models.Pipeline;
import com.azure.dev.models.Pipelines;

public final class PipelinesImpl implements Pipelines {
    private static final ClientLogger LOGGER = new ClientLogger(PipelinesImpl.class);

    private final PipelinesClient innerClient;

    private final com.azure.dev.DevManager serviceManager;

    public PipelinesImpl(PipelinesClient innerClient, com.azure.dev.DevManager serviceManager) {
        this.innerClient = innerClient;
        this.serviceManager = serviceManager;
    }

    public Response<Pipeline> createWithResponse(String organization, String project, CreatePipelineParameters body,
        Context context) {
        Response<PipelineInner> inner = this.serviceClient().createWithResponse(organization, project, body, context);
        if (inner != null) {
            return new SimpleResponse<>(inner.getRequest(), inner.getStatusCode(), inner.getHeaders(),
                new PipelineImpl(inner.getValue(), this.manager()));
        } else {
            return null;
        }
    }

    public Pipeline create(String organization, String project, CreatePipelineParameters body) {
        PipelineInner inner = this.serviceClient().create(organization, project, body);
        if (inner != null) {
            return new PipelineImpl(inner, this.manager());
        } else {
            return null;
        }
    }

    public PagedIterable<Pipeline> list(String organization, String project) {
        PagedIterable<PipelineInner> inner = this.serviceClient().list(organization, project);
        return ResourceManagerUtils.mapPage(inner, inner1 -> new PipelineImpl(inner1, this.manager()));
    }

    public PagedIterable<Pipeline> list(String organization, String project, String orderBy, Integer top,
        String continuationToken, Context context) {
        PagedIterable<PipelineInner> inner
            = this.serviceClient().list(organization, project, orderBy, top, continuationToken, context);
        return ResourceManagerUtils.mapPage(inner, inner1 -> new PipelineImpl(inner1, this.manager()));
    }

    public Response<Pipeline> getWithResponse(String organization, String project, int pipelineId,
        Integer pipelineVersion, Context context) {
        Response<PipelineInner> inner
            = this.serviceClient().getWithResponse(organization, project, pipelineId, pipelineVersion, context);
        if (inner != null) {
            return new SimpleResponse<>(inner.getRequest(), inner.getStatusCode(), inner.getHeaders(),
                new PipelineImpl(inner.getValue(), this.manager()));
        } else {
            return null;
        }
    }

    public Pipeline get(String organization, String project, int pipelineId) {
        PipelineInner inner = this.serviceClient().get(organization, project, pipelineId);
        if (inner != null) {
            return new PipelineImpl(inner, this.manager());
        } else {
            return null;
        }
    }

    private PipelinesClient serviceClient() {
        return this.innerClient;
    }

    private com.azure.dev.DevManager manager() {
        return this.serviceManager;
    }
}
