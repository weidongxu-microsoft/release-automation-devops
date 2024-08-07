// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.implementation;

import com.azure.core.http.rest.Response;
import com.azure.core.util.Context;
import com.azure.core.util.logging.ClientLogger;
import com.azure.dev.fluent.TagsClient;
import com.azure.dev.models.Tags;
import com.azure.dev.models.UpdateTagParameters;
import java.util.Collections;
import java.util.List;

public final class TagsImpl implements Tags {
    private static final ClientLogger LOGGER = new ClientLogger(TagsImpl.class);

    private final TagsClient innerClient;

    private final com.azure.dev.DevManager serviceManager;

    public TagsImpl(TagsClient innerClient, com.azure.dev.DevManager serviceManager) {
        this.innerClient = innerClient;
        this.serviceManager = serviceManager;
    }

    public Response<List<String>> addBuildTagsWithResponse(String organization, String project, int buildId,
        List<String> body, Context context) {
        return this.serviceClient().addBuildTagsWithResponse(organization, project, buildId, body, context);
    }

    public List<String> addBuildTags(String organization, String project, int buildId, List<String> body) {
        List<String> inner = this.serviceClient().addBuildTags(organization, project, buildId, body);
        if (inner != null) {
            return Collections.unmodifiableList(inner);
        } else {
            return Collections.emptyList();
        }
    }

    public Response<List<String>> getBuildTagsWithResponse(String organization, String project, int buildId,
        Context context) {
        return this.serviceClient().getBuildTagsWithResponse(organization, project, buildId, context);
    }

    public List<String> getBuildTags(String organization, String project, int buildId) {
        List<String> inner = this.serviceClient().getBuildTags(organization, project, buildId);
        if (inner != null) {
            return Collections.unmodifiableList(inner);
        } else {
            return Collections.emptyList();
        }
    }

    public Response<List<String>> updateBuildTagsWithResponse(String organization, String project, int buildId,
        UpdateTagParameters body, Context context) {
        return this.serviceClient().updateBuildTagsWithResponse(organization, project, buildId, body, context);
    }

    public List<String> updateBuildTags(String organization, String project, int buildId, UpdateTagParameters body) {
        List<String> inner = this.serviceClient().updateBuildTags(organization, project, buildId, body);
        if (inner != null) {
            return Collections.unmodifiableList(inner);
        } else {
            return Collections.emptyList();
        }
    }

    public Response<List<String>> addBuildTagWithResponse(String organization, String project, int buildId, String tag,
        Context context) {
        return this.serviceClient().addBuildTagWithResponse(organization, project, buildId, tag, context);
    }

    public List<String> addBuildTag(String organization, String project, int buildId, String tag) {
        List<String> inner = this.serviceClient().addBuildTag(organization, project, buildId, tag);
        if (inner != null) {
            return Collections.unmodifiableList(inner);
        } else {
            return Collections.emptyList();
        }
    }

    public Response<List<String>> deleteBuildTagWithResponse(String organization, String project, int buildId,
        String tag, Context context) {
        return this.serviceClient().deleteBuildTagWithResponse(organization, project, buildId, tag, context);
    }

    public List<String> deleteBuildTag(String organization, String project, int buildId, String tag) {
        List<String> inner = this.serviceClient().deleteBuildTag(organization, project, buildId, tag);
        if (inner != null) {
            return Collections.unmodifiableList(inner);
        } else {
            return Collections.emptyList();
        }
    }

    public Response<List<String>> addDefinitionTagsWithResponse(String organization, String project, int definitionId,
        List<String> body, Context context) {
        return this.serviceClient().addDefinitionTagsWithResponse(organization, project, definitionId, body, context);
    }

    public List<String> addDefinitionTags(String organization, String project, int definitionId, List<String> body) {
        List<String> inner = this.serviceClient().addDefinitionTags(organization, project, definitionId, body);
        if (inner != null) {
            return Collections.unmodifiableList(inner);
        } else {
            return Collections.emptyList();
        }
    }

    public Response<List<String>> getDefinitionTagsWithResponse(String organization, String project, int definitionId,
        Integer revision, Context context) {
        return this.serviceClient()
            .getDefinitionTagsWithResponse(organization, project, definitionId, revision, context);
    }

    public List<String> getDefinitionTags(String organization, String project, int definitionId) {
        List<String> inner = this.serviceClient().getDefinitionTags(organization, project, definitionId);
        if (inner != null) {
            return Collections.unmodifiableList(inner);
        } else {
            return Collections.emptyList();
        }
    }

    public Response<List<String>> updateDefinitionTagsWithResponse(String organization, String project,
        int definitionId, UpdateTagParameters body, Context context) {
        return this.serviceClient()
            .updateDefinitionTagsWithResponse(organization, project, definitionId, body, context);
    }

    public List<String> updateDefinitionTags(String organization, String project, int definitionId,
        UpdateTagParameters body) {
        List<String> inner = this.serviceClient().updateDefinitionTags(organization, project, definitionId, body);
        if (inner != null) {
            return Collections.unmodifiableList(inner);
        } else {
            return Collections.emptyList();
        }
    }

    public Response<List<String>> addDefinitionTagWithResponse(String organization, String project, int definitionId,
        String tag, Context context) {
        return this.serviceClient().addDefinitionTagWithResponse(organization, project, definitionId, tag, context);
    }

    public List<String> addDefinitionTag(String organization, String project, int definitionId, String tag) {
        List<String> inner = this.serviceClient().addDefinitionTag(organization, project, definitionId, tag);
        if (inner != null) {
            return Collections.unmodifiableList(inner);
        } else {
            return Collections.emptyList();
        }
    }

    public Response<List<String>> deleteDefinitionTagWithResponse(String organization, String project, int definitionId,
        String tag, Context context) {
        return this.serviceClient().deleteDefinitionTagWithResponse(organization, project, definitionId, tag, context);
    }

    public List<String> deleteDefinitionTag(String organization, String project, int definitionId, String tag) {
        List<String> inner = this.serviceClient().deleteDefinitionTag(organization, project, definitionId, tag);
        if (inner != null) {
            return Collections.unmodifiableList(inner);
        } else {
            return Collections.emptyList();
        }
    }

    public Response<List<String>> getTagsWithResponse(String organization, String project, Context context) {
        return this.serviceClient().getTagsWithResponse(organization, project, context);
    }

    public List<String> getTags(String organization, String project) {
        List<String> inner = this.serviceClient().getTags(organization, project);
        if (inner != null) {
            return Collections.unmodifiableList(inner);
        } else {
            return Collections.emptyList();
        }
    }

    public Response<List<String>> deleteTagWithResponse(String organization, String project, String tag,
        Context context) {
        return this.serviceClient().deleteTagWithResponse(organization, project, tag, context);
    }

    public List<String> deleteTag(String organization, String project, String tag) {
        List<String> inner = this.serviceClient().deleteTag(organization, project, tag);
        if (inner != null) {
            return Collections.unmodifiableList(inner);
        } else {
            return Collections.emptyList();
        }
    }

    private TagsClient serviceClient() {
        return this.innerClient;
    }

    private com.azure.dev.DevManager manager() {
        return this.serviceManager;
    }
}
