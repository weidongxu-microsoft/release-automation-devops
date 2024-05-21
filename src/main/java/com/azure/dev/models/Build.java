// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.models;

import com.azure.dev.fluent.models.BuildInner;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

/**
 * An immutable client-side representation of Build.
 */
public interface Build {
    /**
     * Gets the links property: The class to represent a collection of REST reference links.
     * 
     * @return the links value.
     */
    ReferenceLinks links();

    /**
     * Gets the agentSpecification property: The agent specification for the build.
     * 
     * @return the agentSpecification value.
     */
    AgentSpecification agentSpecification();

    /**
     * Gets the appendCommitMessageToRunName property: Append Commit Message To BuildNumber in UI.
     * 
     * @return the appendCommitMessageToRunName value.
     */
    Boolean appendCommitMessageToRunName();

    /**
     * Gets the buildNumber property: The build number/name of the build.
     * 
     * @return the buildNumber value.
     */
    String buildNumber();

    /**
     * Gets the buildNumberRevision property: The build number revision.
     * 
     * @return the buildNumberRevision value.
     */
    Integer buildNumberRevision();

    /**
     * Gets the controller property: The build controller. This is only set if the definition type is Xaml.
     * 
     * @return the controller value.
     */
    BuildController controller();

    /**
     * Gets the definition property: The definition associated with the build.
     * 
     * @return the definition value.
     */
    DefinitionReference definition();

    /**
     * Gets the deleted property: Indicates whether the build has been deleted.
     * 
     * @return the deleted value.
     */
    Boolean deleted();

    /**
     * Gets the deletedBy property: The identity of the process or person that deleted the build.
     * 
     * @return the deletedBy value.
     */
    IdentityRef deletedBy();

    /**
     * Gets the deletedDate property: The date the build was deleted.
     * 
     * @return the deletedDate value.
     */
    OffsetDateTime deletedDate();

    /**
     * Gets the deletedReason property: The description of how the build was deleted.
     * 
     * @return the deletedReason value.
     */
    String deletedReason();

    /**
     * Gets the demands property: A list of demands that represents the agent capabilities required by this build.
     * 
     * @return the demands value.
     */
    List<Demand> demands();

    /**
     * Gets the finishTime property: The time that the build was completed.
     * 
     * @return the finishTime value.
     */
    OffsetDateTime finishTime();

    /**
     * Gets the id property: The ID of the build.
     * 
     * @return the id value.
     */
    Integer id();

    /**
     * Gets the lastChangedBy property: The identity representing the process or person that last changed the build.
     * 
     * @return the lastChangedBy value.
     */
    IdentityRef lastChangedBy();

    /**
     * Gets the lastChangedDate property: The date the build was last changed.
     * 
     * @return the lastChangedDate value.
     */
    OffsetDateTime lastChangedDate();

    /**
     * Gets the logs property: Information about the build logs.
     * 
     * @return the logs value.
     */
    BuildLogReference logs();

    /**
     * Gets the orchestrationPlan property: The orchestration plan for the build.
     * 
     * @return the orchestrationPlan value.
     */
    TaskOrchestrationPlanReference orchestrationPlan();

    /**
     * Gets the parameters property: The parameters for the build.
     * 
     * @return the parameters value.
     */
    String parameters();

    /**
     * Gets the plans property: Orchestration plans associated with the build (build, cleanup).
     * 
     * @return the plans value.
     */
    List<TaskOrchestrationPlanReference> plans();

    /**
     * Gets the priority property: Azure Pipelines does not support job priority. This field is deprecated.
     * 
     * @return the priority value.
     */
    QueuePriority priority();

    /**
     * Gets the project property: The team project.
     * 
     * @return the project value.
     */
    TeamProjectReference project();

    /**
     * Gets the properties property: The class represents a property bag as a collection of key-value pairs. Values of
     * all primitive types (any type with a `TypeCode != TypeCode.Object`) except for `DBNull` are accepted. Values of
     * type Byte[], Int32, Double, DateType and String preserve their type, other primitives are retuned as a String.
     * Byte[] expected as base64 encoded string.
     * 
     * @return the properties value.
     */
    PropertiesCollection properties();

    /**
     * Gets the quality property: The quality of the xaml build (good, bad, etc.).
     * 
     * @return the quality value.
     */
    String quality();

    /**
     * Gets the queue property: The queue. This is only set if the definition type is Build. WARNING: this field is
     * deprecated and does not corresponds to the jobs queues.
     * 
     * @return the queue value.
     */
    AgentPoolQueue queue();

    /**
     * Gets the queueOptions property: Additional options for queueing the build.
     * 
     * @return the queueOptions value.
     */
    QueueOptions queueOptions();

    /**
     * Gets the queuePosition property: The current position of the build in the queue.
     * 
     * @return the queuePosition value.
     */
    Integer queuePosition();

    /**
     * Gets the queueTime property: The time that the build was queued.
     * 
     * @return the queueTime value.
     */
    OffsetDateTime queueTime();

    /**
     * Gets the reason property: The reason that the build was created.
     * 
     * @return the reason value.
     */
    BuildReason reason();

    /**
     * Gets the repository property: The repository.
     * 
     * @return the repository value.
     */
    BuildRepository repository();

    /**
     * Gets the requestedBy property: The identity that queued the build.
     * 
     * @return the requestedBy value.
     */
    IdentityRef requestedBy();

    /**
     * Gets the requestedFor property: The identity on whose behalf the build was queued.
     * 
     * @return the requestedFor value.
     */
    IdentityRef requestedFor();

    /**
     * Gets the result property: The build result.
     * 
     * @return the result value.
     */
    BuildResult result();

    /**
     * Gets the retainedByRelease property: Indicates whether the build is retained by a release.
     * 
     * @return the retainedByRelease value.
     */
    Boolean retainedByRelease();

    /**
     * Gets the sourceBranch property: The source branch.
     * 
     * @return the sourceBranch value.
     */
    String sourceBranch();

    /**
     * Gets the sourceVersion property: The source version.
     * 
     * @return the sourceVersion value.
     */
    String sourceVersion();

    /**
     * Gets the startTime property: The time that the build was started.
     * 
     * @return the startTime value.
     */
    OffsetDateTime startTime();

    /**
     * Gets the status property: The status of the build.
     * 
     * @return the status value.
     */
    BuildStatus status();

    /**
     * Gets the tags property: The tags property.
     * 
     * @return the tags value.
     */
    List<String> tags();

    /**
     * Gets the templateParameters property: Parameters to template expression evaluation.
     * 
     * @return the templateParameters value.
     */
    Map<String, String> templateParameters();

    /**
     * Gets the triggeredByBuild property: The build that triggered this build via a Build completion trigger.
     * 
     * @return the triggeredByBuild value.
     */
    Build triggeredByBuild();

    /**
     * Gets the triggerInfo property: Sourceprovider-specific information about what triggered the build.
     * 
     * @return the triggerInfo value.
     */
    Map<String, String> triggerInfo();

    /**
     * Gets the uri property: The URI of the build.
     * 
     * @return the uri value.
     */
    String uri();

    /**
     * Gets the url property: The REST URL of the build.
     * 
     * @return the url value.
     */
    String url();

    /**
     * Gets the validationResults property: The validationResults property.
     * 
     * @return the validationResults value.
     */
    List<BuildRequestValidationResult> validationResults();

    /**
     * Gets the inner com.azure.dev.fluent.models.BuildInner object.
     * 
     * @return the inner object.
     */
    BuildInner innerModel();
}
