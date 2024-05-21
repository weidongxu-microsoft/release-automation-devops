// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.implementation;

import com.azure.core.http.rest.Response;
import com.azure.core.http.rest.SimpleResponse;
import com.azure.core.util.Context;
import com.azure.core.util.logging.ClientLogger;
import com.azure.dev.fluent.BuildsClient;
import com.azure.dev.fluent.models.BuildInner;
import com.azure.dev.fluent.models.BuildLogInner;
import com.azure.dev.fluent.models.ChangeInner;
import com.azure.dev.fluent.models.ResourceRefInner;
import com.azure.dev.fluent.models.RetentionLeaseInner;
import com.azure.dev.models.Build;
import com.azure.dev.models.BuildLog;
import com.azure.dev.models.BuildQueryOrder;
import com.azure.dev.models.BuildReason;
import com.azure.dev.models.BuildResult;
import com.azure.dev.models.Builds;
import com.azure.dev.models.BuildStatus;
import com.azure.dev.models.Change;
import com.azure.dev.models.QueryDeletedOption;
import com.azure.dev.models.ResourceRef;
import com.azure.dev.models.RetentionLease;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class BuildsImpl implements Builds {
    private static final ClientLogger LOGGER = new ClientLogger(BuildsImpl.class);

    private final BuildsClient innerClient;

    private final com.azure.dev.DevManager serviceManager;

    public BuildsImpl(BuildsClient innerClient, com.azure.dev.DevManager serviceManager) {
        this.innerClient = innerClient;
        this.serviceManager = serviceManager;
    }

    public Response<List<Build>> listWithResponse(String organization, String project, String definitions,
        String queues, String buildNumber, OffsetDateTime minTime, OffsetDateTime maxTime, String requestedFor,
        BuildReason reasonFilter, BuildStatus statusFilter, BuildResult resultFilter, String tagFilters,
        String properties, Integer top, String continuationToken, Integer maxBuildsPerDefinition,
        QueryDeletedOption deletedFilter, BuildQueryOrder queryOrder, String branchName, String buildIds,
        String repositoryId, String repositoryType, Context context) {
        Response<List<BuildInner>> inner = this.serviceClient()
            .listWithResponse(organization, project, definitions, queues, buildNumber, minTime, maxTime, requestedFor,
                reasonFilter, statusFilter, resultFilter, tagFilters, properties, top, continuationToken,
                maxBuildsPerDefinition, deletedFilter, queryOrder, branchName, buildIds, repositoryId, repositoryType,
                context);
        if (inner != null) {
            return new SimpleResponse<>(inner.getRequest(), inner.getStatusCode(), inner.getHeaders(),
                inner.getValue()
                    .stream()
                    .map(inner1 -> new BuildImpl(inner1, this.manager()))
                    .collect(Collectors.toList()));
        } else {
            return null;
        }
    }

    public List<Build> list(String organization, String project) {
        List<BuildInner> inner = this.serviceClient().list(organization, project);
        if (inner != null) {
            return Collections.unmodifiableList(
                inner.stream().map(inner1 -> new BuildImpl(inner1, this.manager())).collect(Collectors.toList()));
        } else {
            return Collections.emptyList();
        }
    }

    public Response<Build> queueWithResponse(String organization, String project, BuildInner body,
        Boolean ignoreWarnings, String checkInTicket, Integer sourceBuildId, Integer definitionId, Context context) {
        Response<BuildInner> inner = this.serviceClient()
            .queueWithResponse(organization, project, body, ignoreWarnings, checkInTicket, sourceBuildId, definitionId,
                context);
        if (inner != null) {
            return new SimpleResponse<>(inner.getRequest(), inner.getStatusCode(), inner.getHeaders(),
                new BuildImpl(inner.getValue(), this.manager()));
        } else {
            return null;
        }
    }

    public Build queue(String organization, String project, BuildInner body) {
        BuildInner inner = this.serviceClient().queue(organization, project, body);
        if (inner != null) {
            return new BuildImpl(inner, this.manager());
        } else {
            return null;
        }
    }

    public Response<List<Build>> updateBuildsWithResponse(String organization, String project, List<BuildInner> body,
        Context context) {
        Response<List<BuildInner>> inner
            = this.serviceClient().updateBuildsWithResponse(organization, project, body, context);
        if (inner != null) {
            return new SimpleResponse<>(inner.getRequest(), inner.getStatusCode(), inner.getHeaders(),
                inner.getValue()
                    .stream()
                    .map(inner1 -> new BuildImpl(inner1, this.manager()))
                    .collect(Collectors.toList()));
        } else {
            return null;
        }
    }

    public List<Build> updateBuilds(String organization, String project, List<BuildInner> body) {
        List<BuildInner> inner = this.serviceClient().updateBuilds(organization, project, body);
        if (inner != null) {
            return Collections.unmodifiableList(
                inner.stream().map(inner1 -> new BuildImpl(inner1, this.manager())).collect(Collectors.toList()));
        } else {
            return Collections.emptyList();
        }
    }

    public Response<Void> deleteWithResponse(String organization, String project, int buildId, Context context) {
        return this.serviceClient().deleteWithResponse(organization, project, buildId, context);
    }

    public void delete(String organization, String project, int buildId) {
        this.serviceClient().delete(organization, project, buildId);
    }

    public Response<Build> getWithResponse(String organization, String project, int buildId, String propertyFilters,
        Context context) {
        Response<BuildInner> inner
            = this.serviceClient().getWithResponse(organization, project, buildId, propertyFilters, context);
        if (inner != null) {
            return new SimpleResponse<>(inner.getRequest(), inner.getStatusCode(), inner.getHeaders(),
                new BuildImpl(inner.getValue(), this.manager()));
        } else {
            return null;
        }
    }

    public Build get(String organization, String project, int buildId) {
        BuildInner inner = this.serviceClient().get(organization, project, buildId);
        if (inner != null) {
            return new BuildImpl(inner, this.manager());
        } else {
            return null;
        }
    }

    public Response<Build> updateBuildWithResponse(String organization, String project, int buildId, BuildInner body,
        Boolean retry, Context context) {
        Response<BuildInner> inner
            = this.serviceClient().updateBuildWithResponse(organization, project, buildId, body, retry, context);
        if (inner != null) {
            return new SimpleResponse<>(inner.getRequest(), inner.getStatusCode(), inner.getHeaders(),
                new BuildImpl(inner.getValue(), this.manager()));
        } else {
            return null;
        }
    }

    public Build updateBuild(String organization, String project, int buildId, BuildInner body) {
        BuildInner inner = this.serviceClient().updateBuild(organization, project, buildId, body);
        if (inner != null) {
            return new BuildImpl(inner, this.manager());
        } else {
            return null;
        }
    }

    public Response<List<Change>> getBuildChangesWithResponse(String organization, String project, int buildId,
        String continuationToken, Integer top, Boolean includeSourceChange, Context context) {
        Response<List<ChangeInner>> inner = this.serviceClient()
            .getBuildChangesWithResponse(organization, project, buildId, continuationToken, top, includeSourceChange,
                context);
        if (inner != null) {
            return new SimpleResponse<>(inner.getRequest(), inner.getStatusCode(), inner.getHeaders(),
                inner.getValue()
                    .stream()
                    .map(inner1 -> new ChangeImpl(inner1, this.manager()))
                    .collect(Collectors.toList()));
        } else {
            return null;
        }
    }

    public List<Change> getBuildChanges(String organization, String project, int buildId) {
        List<ChangeInner> inner = this.serviceClient().getBuildChanges(organization, project, buildId);
        if (inner != null) {
            return Collections.unmodifiableList(
                inner.stream().map(inner1 -> new ChangeImpl(inner1, this.manager())).collect(Collectors.toList()));
        } else {
            return Collections.emptyList();
        }
    }

    public Response<List<RetentionLease>> getRetentionLeasesForBuildWithResponse(String organization, String project,
        int buildId, Context context) {
        Response<List<RetentionLeaseInner>> inner
            = this.serviceClient().getRetentionLeasesForBuildWithResponse(organization, project, buildId, context);
        if (inner != null) {
            return new SimpleResponse<>(inner.getRequest(), inner.getStatusCode(), inner.getHeaders(),
                inner.getValue()
                    .stream()
                    .map(inner1 -> new RetentionLeaseImpl(inner1, this.manager()))
                    .collect(Collectors.toList()));
        } else {
            return null;
        }
    }

    public List<RetentionLease> getRetentionLeasesForBuild(String organization, String project, int buildId) {
        List<RetentionLeaseInner> inner
            = this.serviceClient().getRetentionLeasesForBuild(organization, project, buildId);
        if (inner != null) {
            return Collections.unmodifiableList(inner.stream()
                .map(inner1 -> new RetentionLeaseImpl(inner1, this.manager()))
                .collect(Collectors.toList()));
        } else {
            return Collections.emptyList();
        }
    }

    public Response<List<BuildLog>> getBuildLogsWithResponse(String organization, String project, int buildId,
        Context context) {
        Response<List<BuildLogInner>> inner
            = this.serviceClient().getBuildLogsWithResponse(organization, project, buildId, context);
        if (inner != null) {
            return new SimpleResponse<>(inner.getRequest(), inner.getStatusCode(), inner.getHeaders(),
                inner.getValue()
                    .stream()
                    .map(inner1 -> new BuildLogImpl(inner1, this.manager()))
                    .collect(Collectors.toList()));
        } else {
            return null;
        }
    }

    public List<BuildLog> getBuildLogs(String organization, String project, int buildId) {
        List<BuildLogInner> inner = this.serviceClient().getBuildLogs(organization, project, buildId);
        if (inner != null) {
            return Collections.unmodifiableList(
                inner.stream().map(inner1 -> new BuildLogImpl(inner1, this.manager())).collect(Collectors.toList()));
        } else {
            return Collections.emptyList();
        }
    }

    public Response<String> getBuildLogWithResponse(String organization, String project, int buildId, int logId,
        Long startLine, Long endLine, Context context) {
        return this.serviceClient()
            .getBuildLogWithResponse(organization, project, buildId, logId, startLine, endLine, context);
    }

    public String getBuildLog(String organization, String project, int buildId, int logId) {
        return this.serviceClient().getBuildLog(organization, project, buildId, logId);
    }

    public Response<List<ResourceRef>> getBuildWorkItemsRefsWithResponse(String organization, String project,
        int buildId, Integer top, Context context) {
        Response<List<ResourceRefInner>> inner
            = this.serviceClient().getBuildWorkItemsRefsWithResponse(organization, project, buildId, top, context);
        if (inner != null) {
            return new SimpleResponse<>(inner.getRequest(), inner.getStatusCode(), inner.getHeaders(),
                inner.getValue()
                    .stream()
                    .map(inner1 -> new ResourceRefImpl(inner1, this.manager()))
                    .collect(Collectors.toList()));
        } else {
            return null;
        }
    }

    public List<ResourceRef> getBuildWorkItemsRefs(String organization, String project, int buildId) {
        List<ResourceRefInner> inner = this.serviceClient().getBuildWorkItemsRefs(organization, project, buildId);
        if (inner != null) {
            return Collections.unmodifiableList(
                inner.stream().map(inner1 -> new ResourceRefImpl(inner1, this.manager())).collect(Collectors.toList()));
        } else {
            return Collections.emptyList();
        }
    }

    public Response<List<ResourceRef>> getBuildWorkItemsRefsFromCommitsWithResponse(String organization, String project,
        int buildId, List<String> body, Integer top, Context context) {
        Response<List<ResourceRefInner>> inner = this.serviceClient()
            .getBuildWorkItemsRefsFromCommitsWithResponse(organization, project, buildId, body, top, context);
        if (inner != null) {
            return new SimpleResponse<>(inner.getRequest(), inner.getStatusCode(), inner.getHeaders(),
                inner.getValue()
                    .stream()
                    .map(inner1 -> new ResourceRefImpl(inner1, this.manager()))
                    .collect(Collectors.toList()));
        } else {
            return null;
        }
    }

    public List<ResourceRef> getBuildWorkItemsRefsFromCommits(String organization, String project, int buildId,
        List<String> body) {
        List<ResourceRefInner> inner
            = this.serviceClient().getBuildWorkItemsRefsFromCommits(organization, project, buildId, body);
        if (inner != null) {
            return Collections.unmodifiableList(
                inner.stream().map(inner1 -> new ResourceRefImpl(inner1, this.manager())).collect(Collectors.toList()));
        } else {
            return Collections.emptyList();
        }
    }

    public Response<List<Change>> getChangesBetweenBuildsWithResponse(String organization, String project,
        Integer fromBuildId, Integer toBuildId, Integer top, Context context) {
        Response<List<ChangeInner>> inner = this.serviceClient()
            .getChangesBetweenBuildsWithResponse(organization, project, fromBuildId, toBuildId, top, context);
        if (inner != null) {
            return new SimpleResponse<>(inner.getRequest(), inner.getStatusCode(), inner.getHeaders(),
                inner.getValue()
                    .stream()
                    .map(inner1 -> new ChangeImpl(inner1, this.manager()))
                    .collect(Collectors.toList()));
        } else {
            return null;
        }
    }

    public List<Change> getChangesBetweenBuilds(String organization, String project) {
        List<ChangeInner> inner = this.serviceClient().getChangesBetweenBuilds(organization, project);
        if (inner != null) {
            return Collections.unmodifiableList(
                inner.stream().map(inner1 -> new ChangeImpl(inner1, this.manager())).collect(Collectors.toList()));
        } else {
            return Collections.emptyList();
        }
    }

    public Response<List<ResourceRef>> getWorkItemsBetweenBuildsWithResponse(String organization, String project,
        int fromBuildId, int toBuildId, Integer top, Context context) {
        Response<List<ResourceRefInner>> inner = this.serviceClient()
            .getWorkItemsBetweenBuildsWithResponse(organization, project, fromBuildId, toBuildId, top, context);
        if (inner != null) {
            return new SimpleResponse<>(inner.getRequest(), inner.getStatusCode(), inner.getHeaders(),
                inner.getValue()
                    .stream()
                    .map(inner1 -> new ResourceRefImpl(inner1, this.manager()))
                    .collect(Collectors.toList()));
        } else {
            return null;
        }
    }

    public List<ResourceRef> getWorkItemsBetweenBuilds(String organization, String project, int fromBuildId,
        int toBuildId) {
        List<ResourceRefInner> inner
            = this.serviceClient().getWorkItemsBetweenBuilds(organization, project, fromBuildId, toBuildId);
        if (inner != null) {
            return Collections.unmodifiableList(
                inner.stream().map(inner1 -> new ResourceRefImpl(inner1, this.manager())).collect(Collectors.toList()));
        } else {
            return Collections.emptyList();
        }
    }

    private BuildsClient serviceClient() {
        return this.innerClient;
    }

    private com.azure.dev.DevManager manager() {
        return this.serviceManager;
    }
}
