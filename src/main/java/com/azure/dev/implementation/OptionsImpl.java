// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.implementation;

import com.azure.core.http.rest.Response;
import com.azure.core.http.rest.SimpleResponse;
import com.azure.core.util.Context;
import com.azure.core.util.logging.ClientLogger;
import com.azure.dev.fluent.OptionsClient;
import com.azure.dev.fluent.models.BuildOptionDefinitionInner;
import com.azure.dev.models.BuildOptionDefinition;
import com.azure.dev.models.Options;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class OptionsImpl implements Options {
    private static final ClientLogger LOGGER = new ClientLogger(OptionsImpl.class);

    private final OptionsClient innerClient;

    private final com.azure.dev.DevManager serviceManager;

    public OptionsImpl(OptionsClient innerClient, com.azure.dev.DevManager serviceManager) {
        this.innerClient = innerClient;
        this.serviceManager = serviceManager;
    }

    public Response<List<BuildOptionDefinition>> listWithResponse(String organization, String project,
        Context context) {
        Response<List<BuildOptionDefinitionInner>> inner
            = this.serviceClient().listWithResponse(organization, project, context);
        if (inner != null) {
            return new SimpleResponse<>(inner.getRequest(), inner.getStatusCode(), inner.getHeaders(),
                inner.getValue()
                    .stream()
                    .map(inner1 -> new BuildOptionDefinitionImpl(inner1, this.manager()))
                    .collect(Collectors.toList()));
        } else {
            return null;
        }
    }

    public List<BuildOptionDefinition> list(String organization, String project) {
        List<BuildOptionDefinitionInner> inner = this.serviceClient().list(organization, project);
        if (inner != null) {
            return Collections.unmodifiableList(inner.stream()
                .map(inner1 -> new BuildOptionDefinitionImpl(inner1, this.manager()))
                .collect(Collectors.toList()));
        } else {
            return Collections.emptyList();
        }
    }

    private OptionsClient serviceClient() {
        return this.innerClient;
    }

    private com.azure.dev.DevManager manager() {
        return this.serviceManager;
    }
}
