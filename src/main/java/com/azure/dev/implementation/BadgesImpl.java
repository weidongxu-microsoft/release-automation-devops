// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.implementation;

import com.azure.core.http.rest.Response;
import com.azure.core.util.Context;
import com.azure.core.util.logging.ClientLogger;
import com.azure.dev.fluent.BadgesClient;
import com.azure.dev.models.Badges;
import java.util.UUID;

public final class BadgesImpl implements Badges {
    private static final ClientLogger LOGGER = new ClientLogger(BadgesImpl.class);

    private final BadgesClient innerClient;

    private final com.azure.dev.DevManager serviceManager;

    public BadgesImpl(BadgesClient innerClient, com.azure.dev.DevManager serviceManager) {
        this.innerClient = innerClient;
        this.serviceManager = serviceManager;
    }

    public Response<String> getWithResponse(String organization, UUID project, int definitionId, String branchName,
        Context context) {
        return this.serviceClient().getWithResponse(organization, project, definitionId, branchName, context);
    }

    public String get(String organization, UUID project, int definitionId) {
        return this.serviceClient().get(organization, project, definitionId);
    }

    public Response<String> getBuildBadgeDataWithResponse(String organization, String project, String repoType,
        String repoId, String branchName, Context context) {
        return this.serviceClient()
            .getBuildBadgeDataWithResponse(organization, project, repoType, repoId, branchName, context);
    }

    public String getBuildBadgeData(String organization, String project, String repoType) {
        return this.serviceClient().getBuildBadgeData(organization, project, repoType);
    }

    private BadgesClient serviceClient() {
        return this.innerClient;
    }

    private com.azure.dev.DevManager manager() {
        return this.serviceManager;
    }
}
