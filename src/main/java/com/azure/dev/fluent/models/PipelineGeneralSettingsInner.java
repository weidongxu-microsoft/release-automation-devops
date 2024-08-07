// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.fluent.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Contains pipeline general settings.
 */
@Fluent
public final class PipelineGeneralSettingsInner {
    /*
     * If enabled, audit logs will be generated whenever someone queues a pipeline run and defines variables that are not marked as "Settable at queue time".
     */
    @JsonProperty(value = "auditEnforceSettableVar")
    private Boolean auditEnforceSettableVar;

    /*
     * Enable forked repositories to build pull requests.
     */
    @JsonProperty(value = "buildsEnabledForForks")
    private Boolean buildsEnabledForForks;

    /*
     * Disable classic build pipelines creation.
     */
    @JsonProperty(value = "disableClassicBuildPipelineCreation")
    private Boolean disableClassicBuildPipelineCreation;

    /*
     * Disable classic pipelines creation.
     */
    @JsonProperty(value = "disableClassicPipelineCreation")
    private Boolean disableClassicPipelineCreation;

    /*
     * Disable classic release pipelines creation.
     */
    @JsonProperty(value = "disableClassicReleasePipelineCreation")
    private Boolean disableClassicReleasePipelineCreation;

    /*
     * Disable implied pipeline CI triggers if the trigger section in YAML is missing.
     */
    @JsonProperty(value = "disableImpliedYAMLCiTrigger")
    private Boolean disableImpliedYamlCiTrigger;

    /*
     * Enable shell tasks args sanitizing.
     */
    @JsonProperty(value = "enableShellTasksArgsSanitizing")
    private Boolean enableShellTasksArgsSanitizing;

    /*
     * Enable shell tasks args sanitizing preview.
     */
    @JsonProperty(value = "enableShellTasksArgsSanitizingAudit")
    private Boolean enableShellTasksArgsSanitizingAudit;

    /*
     * If enabled, scope of access for all non-release pipelines reduces to the current project.
     */
    @JsonProperty(value = "enforceJobAuthScope")
    private Boolean enforceJobAuthScope;

    /*
     * Enforce job auth scope for builds of forked repositories.
     */
    @JsonProperty(value = "enforceJobAuthScopeForForks")
    private Boolean enforceJobAuthScopeForForks;

    /*
     * If enabled, scope of access for all release pipelines reduces to the current project.
     */
    @JsonProperty(value = "enforceJobAuthScopeForReleases")
    private Boolean enforceJobAuthScopeForReleases;

    /*
     * Enforce no access to secrets for builds of forked repositories.
     */
    @JsonProperty(value = "enforceNoAccessToSecretsFromForks")
    private Boolean enforceNoAccessToSecretsFromForks;

    /*
     * Restricts the scope of access for all pipelines to only repositories explicitly referenced by the pipeline.
     */
    @JsonProperty(value = "enforceReferencedRepoScopedToken")
    private Boolean enforceReferencedRepoScopedToken;

    /*
     * If enabled, only those variables that are explicitly marked as "Settable at queue time" can be set at queue time.
     */
    @JsonProperty(value = "enforceSettableVar")
    private Boolean enforceSettableVar;

    /*
     * Enable settings that enforce certain levels of protection for building pull requests from forks globally.
     */
    @JsonProperty(value = "forkProtectionEnabled")
    private Boolean forkProtectionEnabled;

    /*
     * Make comments required to have builds in all pull requests.
     */
    @JsonProperty(value = "isCommentRequiredForPullRequest")
    private Boolean isCommentRequiredForPullRequest;

    /*
     * Allows pipelines to record metadata.
     */
    @JsonProperty(value = "publishPipelineMetadata")
    private Boolean publishPipelineMetadata;

    /*
     * Make comments required to have builds in pull requests from non-team members and non-contributors.
     */
    @JsonProperty(value = "requireCommentsForNonTeamMemberAndNonContributors")
    private Boolean requireCommentsForNonTeamMemberAndNonContributors;

    /*
     * Make comments required to have builds in pull requests from non-team members.
     */
    @JsonProperty(value = "requireCommentsForNonTeamMembersOnly")
    private Boolean requireCommentsForNonTeamMembersOnly;

    /*
     * Anonymous users can access the status badge API for all pipelines unless this option is enabled.
     */
    @JsonProperty(value = "statusBadgesArePrivate")
    private Boolean statusBadgesArePrivate;

    /**
     * Creates an instance of PipelineGeneralSettingsInner class.
     */
    public PipelineGeneralSettingsInner() {
    }

    /**
     * Get the auditEnforceSettableVar property: If enabled, audit logs will be generated whenever someone queues a
     * pipeline run and defines variables that are not marked as "Settable at queue time".
     * 
     * @return the auditEnforceSettableVar value.
     */
    public Boolean auditEnforceSettableVar() {
        return this.auditEnforceSettableVar;
    }

    /**
     * Set the auditEnforceSettableVar property: If enabled, audit logs will be generated whenever someone queues a
     * pipeline run and defines variables that are not marked as "Settable at queue time".
     * 
     * @param auditEnforceSettableVar the auditEnforceSettableVar value to set.
     * @return the PipelineGeneralSettingsInner object itself.
     */
    public PipelineGeneralSettingsInner withAuditEnforceSettableVar(Boolean auditEnforceSettableVar) {
        this.auditEnforceSettableVar = auditEnforceSettableVar;
        return this;
    }

    /**
     * Get the buildsEnabledForForks property: Enable forked repositories to build pull requests.
     * 
     * @return the buildsEnabledForForks value.
     */
    public Boolean buildsEnabledForForks() {
        return this.buildsEnabledForForks;
    }

    /**
     * Set the buildsEnabledForForks property: Enable forked repositories to build pull requests.
     * 
     * @param buildsEnabledForForks the buildsEnabledForForks value to set.
     * @return the PipelineGeneralSettingsInner object itself.
     */
    public PipelineGeneralSettingsInner withBuildsEnabledForForks(Boolean buildsEnabledForForks) {
        this.buildsEnabledForForks = buildsEnabledForForks;
        return this;
    }

    /**
     * Get the disableClassicBuildPipelineCreation property: Disable classic build pipelines creation.
     * 
     * @return the disableClassicBuildPipelineCreation value.
     */
    public Boolean disableClassicBuildPipelineCreation() {
        return this.disableClassicBuildPipelineCreation;
    }

    /**
     * Set the disableClassicBuildPipelineCreation property: Disable classic build pipelines creation.
     * 
     * @param disableClassicBuildPipelineCreation the disableClassicBuildPipelineCreation value to set.
     * @return the PipelineGeneralSettingsInner object itself.
     */
    public PipelineGeneralSettingsInner
        withDisableClassicBuildPipelineCreation(Boolean disableClassicBuildPipelineCreation) {
        this.disableClassicBuildPipelineCreation = disableClassicBuildPipelineCreation;
        return this;
    }

    /**
     * Get the disableClassicPipelineCreation property: Disable classic pipelines creation.
     * 
     * @return the disableClassicPipelineCreation value.
     */
    public Boolean disableClassicPipelineCreation() {
        return this.disableClassicPipelineCreation;
    }

    /**
     * Set the disableClassicPipelineCreation property: Disable classic pipelines creation.
     * 
     * @param disableClassicPipelineCreation the disableClassicPipelineCreation value to set.
     * @return the PipelineGeneralSettingsInner object itself.
     */
    public PipelineGeneralSettingsInner withDisableClassicPipelineCreation(Boolean disableClassicPipelineCreation) {
        this.disableClassicPipelineCreation = disableClassicPipelineCreation;
        return this;
    }

    /**
     * Get the disableClassicReleasePipelineCreation property: Disable classic release pipelines creation.
     * 
     * @return the disableClassicReleasePipelineCreation value.
     */
    public Boolean disableClassicReleasePipelineCreation() {
        return this.disableClassicReleasePipelineCreation;
    }

    /**
     * Set the disableClassicReleasePipelineCreation property: Disable classic release pipelines creation.
     * 
     * @param disableClassicReleasePipelineCreation the disableClassicReleasePipelineCreation value to set.
     * @return the PipelineGeneralSettingsInner object itself.
     */
    public PipelineGeneralSettingsInner
        withDisableClassicReleasePipelineCreation(Boolean disableClassicReleasePipelineCreation) {
        this.disableClassicReleasePipelineCreation = disableClassicReleasePipelineCreation;
        return this;
    }

    /**
     * Get the disableImpliedYamlCiTrigger property: Disable implied pipeline CI triggers if the trigger section in YAML
     * is missing.
     * 
     * @return the disableImpliedYamlCiTrigger value.
     */
    public Boolean disableImpliedYamlCiTrigger() {
        return this.disableImpliedYamlCiTrigger;
    }

    /**
     * Set the disableImpliedYamlCiTrigger property: Disable implied pipeline CI triggers if the trigger section in YAML
     * is missing.
     * 
     * @param disableImpliedYamlCiTrigger the disableImpliedYamlCiTrigger value to set.
     * @return the PipelineGeneralSettingsInner object itself.
     */
    public PipelineGeneralSettingsInner withDisableImpliedYamlCiTrigger(Boolean disableImpliedYamlCiTrigger) {
        this.disableImpliedYamlCiTrigger = disableImpliedYamlCiTrigger;
        return this;
    }

    /**
     * Get the enableShellTasksArgsSanitizing property: Enable shell tasks args sanitizing.
     * 
     * @return the enableShellTasksArgsSanitizing value.
     */
    public Boolean enableShellTasksArgsSanitizing() {
        return this.enableShellTasksArgsSanitizing;
    }

    /**
     * Set the enableShellTasksArgsSanitizing property: Enable shell tasks args sanitizing.
     * 
     * @param enableShellTasksArgsSanitizing the enableShellTasksArgsSanitizing value to set.
     * @return the PipelineGeneralSettingsInner object itself.
     */
    public PipelineGeneralSettingsInner withEnableShellTasksArgsSanitizing(Boolean enableShellTasksArgsSanitizing) {
        this.enableShellTasksArgsSanitizing = enableShellTasksArgsSanitizing;
        return this;
    }

    /**
     * Get the enableShellTasksArgsSanitizingAudit property: Enable shell tasks args sanitizing preview.
     * 
     * @return the enableShellTasksArgsSanitizingAudit value.
     */
    public Boolean enableShellTasksArgsSanitizingAudit() {
        return this.enableShellTasksArgsSanitizingAudit;
    }

    /**
     * Set the enableShellTasksArgsSanitizingAudit property: Enable shell tasks args sanitizing preview.
     * 
     * @param enableShellTasksArgsSanitizingAudit the enableShellTasksArgsSanitizingAudit value to set.
     * @return the PipelineGeneralSettingsInner object itself.
     */
    public PipelineGeneralSettingsInner
        withEnableShellTasksArgsSanitizingAudit(Boolean enableShellTasksArgsSanitizingAudit) {
        this.enableShellTasksArgsSanitizingAudit = enableShellTasksArgsSanitizingAudit;
        return this;
    }

    /**
     * Get the enforceJobAuthScope property: If enabled, scope of access for all non-release pipelines reduces to the
     * current project.
     * 
     * @return the enforceJobAuthScope value.
     */
    public Boolean enforceJobAuthScope() {
        return this.enforceJobAuthScope;
    }

    /**
     * Set the enforceJobAuthScope property: If enabled, scope of access for all non-release pipelines reduces to the
     * current project.
     * 
     * @param enforceJobAuthScope the enforceJobAuthScope value to set.
     * @return the PipelineGeneralSettingsInner object itself.
     */
    public PipelineGeneralSettingsInner withEnforceJobAuthScope(Boolean enforceJobAuthScope) {
        this.enforceJobAuthScope = enforceJobAuthScope;
        return this;
    }

    /**
     * Get the enforceJobAuthScopeForForks property: Enforce job auth scope for builds of forked repositories.
     * 
     * @return the enforceJobAuthScopeForForks value.
     */
    public Boolean enforceJobAuthScopeForForks() {
        return this.enforceJobAuthScopeForForks;
    }

    /**
     * Set the enforceJobAuthScopeForForks property: Enforce job auth scope for builds of forked repositories.
     * 
     * @param enforceJobAuthScopeForForks the enforceJobAuthScopeForForks value to set.
     * @return the PipelineGeneralSettingsInner object itself.
     */
    public PipelineGeneralSettingsInner withEnforceJobAuthScopeForForks(Boolean enforceJobAuthScopeForForks) {
        this.enforceJobAuthScopeForForks = enforceJobAuthScopeForForks;
        return this;
    }

    /**
     * Get the enforceJobAuthScopeForReleases property: If enabled, scope of access for all release pipelines reduces to
     * the current project.
     * 
     * @return the enforceJobAuthScopeForReleases value.
     */
    public Boolean enforceJobAuthScopeForReleases() {
        return this.enforceJobAuthScopeForReleases;
    }

    /**
     * Set the enforceJobAuthScopeForReleases property: If enabled, scope of access for all release pipelines reduces to
     * the current project.
     * 
     * @param enforceJobAuthScopeForReleases the enforceJobAuthScopeForReleases value to set.
     * @return the PipelineGeneralSettingsInner object itself.
     */
    public PipelineGeneralSettingsInner withEnforceJobAuthScopeForReleases(Boolean enforceJobAuthScopeForReleases) {
        this.enforceJobAuthScopeForReleases = enforceJobAuthScopeForReleases;
        return this;
    }

    /**
     * Get the enforceNoAccessToSecretsFromForks property: Enforce no access to secrets for builds of forked
     * repositories.
     * 
     * @return the enforceNoAccessToSecretsFromForks value.
     */
    public Boolean enforceNoAccessToSecretsFromForks() {
        return this.enforceNoAccessToSecretsFromForks;
    }

    /**
     * Set the enforceNoAccessToSecretsFromForks property: Enforce no access to secrets for builds of forked
     * repositories.
     * 
     * @param enforceNoAccessToSecretsFromForks the enforceNoAccessToSecretsFromForks value to set.
     * @return the PipelineGeneralSettingsInner object itself.
     */
    public PipelineGeneralSettingsInner
        withEnforceNoAccessToSecretsFromForks(Boolean enforceNoAccessToSecretsFromForks) {
        this.enforceNoAccessToSecretsFromForks = enforceNoAccessToSecretsFromForks;
        return this;
    }

    /**
     * Get the enforceReferencedRepoScopedToken property: Restricts the scope of access for all pipelines to only
     * repositories explicitly referenced by the pipeline.
     * 
     * @return the enforceReferencedRepoScopedToken value.
     */
    public Boolean enforceReferencedRepoScopedToken() {
        return this.enforceReferencedRepoScopedToken;
    }

    /**
     * Set the enforceReferencedRepoScopedToken property: Restricts the scope of access for all pipelines to only
     * repositories explicitly referenced by the pipeline.
     * 
     * @param enforceReferencedRepoScopedToken the enforceReferencedRepoScopedToken value to set.
     * @return the PipelineGeneralSettingsInner object itself.
     */
    public PipelineGeneralSettingsInner withEnforceReferencedRepoScopedToken(Boolean enforceReferencedRepoScopedToken) {
        this.enforceReferencedRepoScopedToken = enforceReferencedRepoScopedToken;
        return this;
    }

    /**
     * Get the enforceSettableVar property: If enabled, only those variables that are explicitly marked as "Settable at
     * queue time" can be set at queue time.
     * 
     * @return the enforceSettableVar value.
     */
    public Boolean enforceSettableVar() {
        return this.enforceSettableVar;
    }

    /**
     * Set the enforceSettableVar property: If enabled, only those variables that are explicitly marked as "Settable at
     * queue time" can be set at queue time.
     * 
     * @param enforceSettableVar the enforceSettableVar value to set.
     * @return the PipelineGeneralSettingsInner object itself.
     */
    public PipelineGeneralSettingsInner withEnforceSettableVar(Boolean enforceSettableVar) {
        this.enforceSettableVar = enforceSettableVar;
        return this;
    }

    /**
     * Get the forkProtectionEnabled property: Enable settings that enforce certain levels of protection for building
     * pull requests from forks globally.
     * 
     * @return the forkProtectionEnabled value.
     */
    public Boolean forkProtectionEnabled() {
        return this.forkProtectionEnabled;
    }

    /**
     * Set the forkProtectionEnabled property: Enable settings that enforce certain levels of protection for building
     * pull requests from forks globally.
     * 
     * @param forkProtectionEnabled the forkProtectionEnabled value to set.
     * @return the PipelineGeneralSettingsInner object itself.
     */
    public PipelineGeneralSettingsInner withForkProtectionEnabled(Boolean forkProtectionEnabled) {
        this.forkProtectionEnabled = forkProtectionEnabled;
        return this;
    }

    /**
     * Get the isCommentRequiredForPullRequest property: Make comments required to have builds in all pull requests.
     * 
     * @return the isCommentRequiredForPullRequest value.
     */
    public Boolean isCommentRequiredForPullRequest() {
        return this.isCommentRequiredForPullRequest;
    }

    /**
     * Set the isCommentRequiredForPullRequest property: Make comments required to have builds in all pull requests.
     * 
     * @param isCommentRequiredForPullRequest the isCommentRequiredForPullRequest value to set.
     * @return the PipelineGeneralSettingsInner object itself.
     */
    public PipelineGeneralSettingsInner withIsCommentRequiredForPullRequest(Boolean isCommentRequiredForPullRequest) {
        this.isCommentRequiredForPullRequest = isCommentRequiredForPullRequest;
        return this;
    }

    /**
     * Get the publishPipelineMetadata property: Allows pipelines to record metadata.
     * 
     * @return the publishPipelineMetadata value.
     */
    public Boolean publishPipelineMetadata() {
        return this.publishPipelineMetadata;
    }

    /**
     * Set the publishPipelineMetadata property: Allows pipelines to record metadata.
     * 
     * @param publishPipelineMetadata the publishPipelineMetadata value to set.
     * @return the PipelineGeneralSettingsInner object itself.
     */
    public PipelineGeneralSettingsInner withPublishPipelineMetadata(Boolean publishPipelineMetadata) {
        this.publishPipelineMetadata = publishPipelineMetadata;
        return this;
    }

    /**
     * Get the requireCommentsForNonTeamMemberAndNonContributors property: Make comments required to have builds in pull
     * requests from non-team members and non-contributors.
     * 
     * @return the requireCommentsForNonTeamMemberAndNonContributors value.
     */
    public Boolean requireCommentsForNonTeamMemberAndNonContributors() {
        return this.requireCommentsForNonTeamMemberAndNonContributors;
    }

    /**
     * Set the requireCommentsForNonTeamMemberAndNonContributors property: Make comments required to have builds in pull
     * requests from non-team members and non-contributors.
     * 
     * @param requireCommentsForNonTeamMemberAndNonContributors the requireCommentsForNonTeamMemberAndNonContributors
     * value to set.
     * @return the PipelineGeneralSettingsInner object itself.
     */
    public PipelineGeneralSettingsInner withRequireCommentsForNonTeamMemberAndNonContributors(
        Boolean requireCommentsForNonTeamMemberAndNonContributors) {
        this.requireCommentsForNonTeamMemberAndNonContributors = requireCommentsForNonTeamMemberAndNonContributors;
        return this;
    }

    /**
     * Get the requireCommentsForNonTeamMembersOnly property: Make comments required to have builds in pull requests
     * from non-team members.
     * 
     * @return the requireCommentsForNonTeamMembersOnly value.
     */
    public Boolean requireCommentsForNonTeamMembersOnly() {
        return this.requireCommentsForNonTeamMembersOnly;
    }

    /**
     * Set the requireCommentsForNonTeamMembersOnly property: Make comments required to have builds in pull requests
     * from non-team members.
     * 
     * @param requireCommentsForNonTeamMembersOnly the requireCommentsForNonTeamMembersOnly value to set.
     * @return the PipelineGeneralSettingsInner object itself.
     */
    public PipelineGeneralSettingsInner
        withRequireCommentsForNonTeamMembersOnly(Boolean requireCommentsForNonTeamMembersOnly) {
        this.requireCommentsForNonTeamMembersOnly = requireCommentsForNonTeamMembersOnly;
        return this;
    }

    /**
     * Get the statusBadgesArePrivate property: Anonymous users can access the status badge API for all pipelines unless
     * this option is enabled.
     * 
     * @return the statusBadgesArePrivate value.
     */
    public Boolean statusBadgesArePrivate() {
        return this.statusBadgesArePrivate;
    }

    /**
     * Set the statusBadgesArePrivate property: Anonymous users can access the status badge API for all pipelines unless
     * this option is enabled.
     * 
     * @param statusBadgesArePrivate the statusBadgesArePrivate value to set.
     * @return the PipelineGeneralSettingsInner object itself.
     */
    public PipelineGeneralSettingsInner withStatusBadgesArePrivate(Boolean statusBadgesArePrivate) {
        this.statusBadgesArePrivate = statusBadgesArePrivate;
        return this;
    }

    /**
     * Validates the instance.
     * 
     * @throws IllegalArgumentException thrown if the instance is not valid.
     */
    public void validate() {
    }
}
