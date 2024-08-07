// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.fluent;

import com.azure.core.annotation.ReturnType;
import com.azure.core.annotation.ServiceMethod;
import com.azure.core.http.rest.Response;
import com.azure.core.util.Context;
import com.azure.dev.fluent.models.ProjectRetentionSettingInner;
import com.azure.dev.models.UpdateProjectRetentionSettingModel;

/**
 * An instance of this class provides access to all the operations defined in RetentionsClient.
 */
public interface RetentionsClient {
    /**
     * Gets the project's retention settings.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the project's retention settings along with {@link Response}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Response<ProjectRetentionSettingInner> getWithResponse(String organization, String project, Context context);

    /**
     * Gets the project's retention settings.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the project's retention settings.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    ProjectRetentionSettingInner get(String organization, String project);

    /**
     * Updates the project's retention settings.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param body Contains members for updating the retention settings values. All fields are optional.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return contains the settings for the retention rules along with {@link Response}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Response<ProjectRetentionSettingInner> updateWithResponse(String organization, String project,
        UpdateProjectRetentionSettingModel body, Context context);

    /**
     * Updates the project's retention settings.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param body Contains members for updating the retention settings values. All fields are optional.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return contains the settings for the retention rules.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    ProjectRetentionSettingInner update(String organization, String project, UpdateProjectRetentionSettingModel body);
}
