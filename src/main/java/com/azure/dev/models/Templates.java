// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.models;

import com.azure.core.http.rest.Response;
import com.azure.core.util.Context;
import com.azure.dev.fluent.models.BuildDefinitionTemplateInner;
import java.util.List;

/**
 * Resource collection API of Templates.
 */
public interface Templates {
    /**
     * Gets all definition templates.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return all definition templates along with {@link Response}.
     */
    Response<List<BuildDefinitionTemplate>> listWithResponse(String organization, String project, Context context);

    /**
     * Gets all definition templates.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return all definition templates.
     */
    List<BuildDefinitionTemplate> list(String organization, String project);

    /**
     * Deletes a build definition template.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param templateId The ID of the template.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the {@link Response}.
     */
    Response<Void> deleteWithResponse(String organization, String project, String templateId, Context context);

    /**
     * Deletes a build definition template.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param templateId The ID of the template.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    void delete(String organization, String project, String templateId);

    /**
     * Gets a specific build definition template.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param templateId The ID of the requested template.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a specific build definition template along with {@link Response}.
     */
    Response<BuildDefinitionTemplate> getWithResponse(String organization, String project, String templateId,
        Context context);

    /**
     * Gets a specific build definition template.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param templateId The ID of the requested template.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a specific build definition template.
     */
    BuildDefinitionTemplate get(String organization, String project, String templateId);

    /**
     * Updates an existing build definition template.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param templateId The ID of the template.
     * @param body The new version of the template.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return represents a template from which new build definitions can be created along with {@link Response}.
     */
    Response<BuildDefinitionTemplate> saveTemplateWithResponse(String organization, String project, String templateId,
        BuildDefinitionTemplateInner body, Context context);

    /**
     * Updates an existing build definition template.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param templateId The ID of the template.
     * @param body The new version of the template.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return represents a template from which new build definitions can be created.
     */
    BuildDefinitionTemplate saveTemplate(String organization, String project, String templateId,
        BuildDefinitionTemplateInner body);
}
