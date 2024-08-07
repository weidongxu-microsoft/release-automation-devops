// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.fluent;

import com.azure.core.annotation.ReturnType;
import com.azure.core.annotation.ServiceMethod;
import com.azure.core.http.rest.Response;
import com.azure.core.util.Context;
import com.azure.dev.fluent.models.PullRequestInner;
import com.azure.dev.fluent.models.RepositoryWebhookInner;
import com.azure.dev.fluent.models.SourceProviderAttributesInner;
import com.azure.dev.fluent.models.SourceRepositoriesInner;
import com.azure.dev.fluent.models.SourceRepositoryItemInner;
import com.azure.dev.models.ResultSet;
import java.util.List;
import java.util.UUID;

/**
 * An instance of this class provides access to all the operations defined in SourceProvidersClient.
 */
public interface SourceProvidersClient {
    /**
     * Get a list of source providers and their capabilities.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a list of source providers and their capabilities along with {@link Response}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Response<List<SourceProviderAttributesInner>> listWithResponse(String organization, String project,
        Context context);

    /**
     * Get a list of source providers and their capabilities.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a list of source providers and their capabilities.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    List<SourceProviderAttributesInner> list(String organization, String project);

    /**
     * Gets a list of branches for the given source code repository.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param providerName The name of the source provider.
     * @param serviceEndpointId If specified, the ID of the service endpoint to query. Can only be omitted for providers
     * that do not use service endpoints, e.g. TFVC or TFGit.
     * @param repository The vendor-specific identifier or the name of the repository to get branches. Can only be
     * omitted for providers that do not support multiple repositories.
     * @param branchName If supplied, the name of the branch to check for specifically.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a list of branches for the given source code repository along with {@link Response}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Response<List<String>> listBranchesWithResponse(String organization, String project, String providerName,
        UUID serviceEndpointId, String repository, String branchName, Context context);

    /**
     * Gets a list of branches for the given source code repository.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param providerName The name of the source provider.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a list of branches for the given source code repository.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    List<String> listBranches(String organization, String project, String providerName);

    /**
     * Gets the contents of a file in the given source code repository.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param providerName The name of the source provider.
     * @param serviceEndpointId If specified, the ID of the service endpoint to query. Can only be omitted for providers
     * that do not use service endpoints, e.g. TFVC or TFGit.
     * @param repository If specified, the vendor-specific identifier or the name of the repository to get branches. Can
     * only be omitted for providers that do not support multiple repositories.
     * @param commitOrBranch The identifier of the commit or branch from which a file's contents are retrieved.
     * @param path The path to the file to retrieve, relative to the root of the repository.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the contents of a file in the given source code repository along with {@link Response}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Response<String> getFileContentsWithResponse(String organization, String project, String providerName,
        UUID serviceEndpointId, String repository, String commitOrBranch, String path, Context context);

    /**
     * Gets the contents of a file in the given source code repository.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param providerName The name of the source provider.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the contents of a file in the given source code repository.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    String getFileContents(String organization, String project, String providerName);

    /**
     * Gets the contents of a directory in the given source code repository.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param providerName The name of the source provider.
     * @param serviceEndpointId If specified, the ID of the service endpoint to query. Can only be omitted for providers
     * that do not use service endpoints, e.g. TFVC or TFGit.
     * @param repository If specified, the vendor-specific identifier or the name of the repository to get branches. Can
     * only be omitted for providers that do not support multiple repositories.
     * @param commitOrBranch The identifier of the commit or branch from which a file's contents are retrieved.
     * @param path The path contents to list, relative to the root of the repository.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the contents of a directory in the given source code repository along with {@link Response}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Response<List<SourceRepositoryItemInner>> getPathContentsWithResponse(String organization, String project,
        String providerName, UUID serviceEndpointId, String repository, String commitOrBranch, String path,
        Context context);

    /**
     * Gets the contents of a directory in the given source code repository.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param providerName The name of the source provider.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the contents of a directory in the given source code repository.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    List<SourceRepositoryItemInner> getPathContents(String organization, String project, String providerName);

    /**
     * Gets a pull request object from source provider.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param providerName The name of the source provider.
     * @param pullRequestId Vendor-specific id of the pull request.
     * @param repositoryId Vendor-specific identifier or the name of the repository that contains the pull request.
     * @param serviceEndpointId If specified, the ID of the service endpoint to query. Can only be omitted for providers
     * that do not use service endpoints, e.g. TFVC or TFGit.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a pull request object from source provider along with {@link Response}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Response<PullRequestInner> getPullRequestWithResponse(String organization, String project, String providerName,
        String pullRequestId, String repositoryId, UUID serviceEndpointId, Context context);

    /**
     * Gets a pull request object from source provider.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param providerName The name of the source provider.
     * @param pullRequestId Vendor-specific id of the pull request.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a pull request object from source provider.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    PullRequestInner getPullRequest(String organization, String project, String providerName, String pullRequestId);

    /**
     * Gets a list of source code repositories.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param providerName The name of the source provider.
     * @param serviceEndpointId If specified, the ID of the service endpoint to query. Can only be omitted for providers
     * that do not use service endpoints, e.g. TFVC or TFGit.
     * @param repository If specified, the vendor-specific identifier or the name of a single repository to get.
     * @param resultSet 'top' for the repositories most relevant for the endpoint. If not set, all repositories are
     * returned. Ignored if 'repository' is set.
     * @param pageResults If set to true, this will limit the set of results and will return a continuation token to
     * continue the query.
     * @param continuationToken When paging results, this is a continuation token, returned by a previous call to this
     * method, that can be used to return the next set of repositories.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a list of source code repositories along with {@link Response}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Response<SourceRepositoriesInner> listRepositoriesWithResponse(String organization, String project,
        String providerName, UUID serviceEndpointId, String repository, ResultSet resultSet, Boolean pageResults,
        String continuationToken, Context context);

    /**
     * Gets a list of source code repositories.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param providerName The name of the source provider.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a list of source code repositories.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    SourceRepositoriesInner listRepositories(String organization, String project, String providerName);

    /**
     * Recreates the webhooks for the specified triggers in the given source code repository.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param providerName The name of the source provider.
     * @param body The types of triggers to restore webhooks for.
     * @param serviceEndpointId If specified, the ID of the service endpoint to query. Can only be omitted for providers
     * that do not use service endpoints, e.g. TFVC or TFGit.
     * @param repository If specified, the vendor-specific identifier or the name of the repository to get webhooks. Can
     * only be omitted for providers that do not support multiple repositories.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the {@link Response}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Response<Void> restoreWebhooksWithResponse(String organization, String project, String providerName,
        List<String> body, UUID serviceEndpointId, String repository, Context context);

    /**
     * Recreates the webhooks for the specified triggers in the given source code repository.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param providerName The name of the source provider.
     * @param body The types of triggers to restore webhooks for.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    void restoreWebhooks(String organization, String project, String providerName, List<String> body);

    /**
     * Gets a list of webhooks installed in the given source code repository.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param providerName The name of the source provider.
     * @param serviceEndpointId If specified, the ID of the service endpoint to query. Can only be omitted for providers
     * that do not use service endpoints, e.g. TFVC or TFGit.
     * @param repository If specified, the vendor-specific identifier or the name of the repository to get webhooks. Can
     * only be omitted for providers that do not support multiple repositories.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a list of webhooks installed in the given source code repository along with {@link Response}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Response<List<RepositoryWebhookInner>> listWebhooksWithResponse(String organization, String project,
        String providerName, UUID serviceEndpointId, String repository, Context context);

    /**
     * Gets a list of webhooks installed in the given source code repository.
     * 
     * @param organization The name of the Azure DevOps organization.
     * @param project Project ID or project name.
     * @param providerName The name of the source provider.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a list of webhooks installed in the given source code repository.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    List<RepositoryWebhookInner> listWebhooks(String organization, String project, String providerName);
}
