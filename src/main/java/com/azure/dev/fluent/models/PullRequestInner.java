// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.fluent.models;

import com.azure.core.annotation.Fluent;
import com.azure.dev.models.IdentityRef;
import com.azure.dev.models.ReferenceLinks;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a pull request object. These are retrieved from Source Providers.
 */
@Fluent
public final class PullRequestInner {
    /*
     * The links to other objects related to this object.
     */
    @JsonProperty(value = "_links")
    private ReferenceLinks links;

    /*
     * Author of the pull request.
     */
    @JsonProperty(value = "author")
    private IdentityRef author;

    /*
     * Current state of the pull request, e.g. open, merged, closed, conflicts, etc.
     */
    @JsonProperty(value = "currentState")
    private String currentState;

    /*
     * Description for the pull request.
     */
    @JsonProperty(value = "description")
    private String description;

    /*
     * Returns if pull request is draft
     */
    @JsonProperty(value = "draft")
    private Boolean draft;

    /*
     * Unique identifier for the pull request
     */
    @JsonProperty(value = "id")
    private String id;

    /*
     * The name of the provider this pull request is associated with.
     */
    @JsonProperty(value = "providerName")
    private String providerName;

    /*
     * Source branch ref of this pull request
     */
    @JsonProperty(value = "sourceBranchRef")
    private String sourceBranchRef;

    /*
     * Owner of the source repository of this pull request
     */
    @JsonProperty(value = "sourceRepositoryOwner")
    private String sourceRepositoryOwner;

    /*
     * Target branch ref of this pull request
     */
    @JsonProperty(value = "targetBranchRef")
    private String targetBranchRef;

    /*
     * Owner of the target repository of this pull request
     */
    @JsonProperty(value = "targetRepositoryOwner")
    private String targetRepositoryOwner;

    /*
     * Title of the pull request.
     */
    @JsonProperty(value = "title")
    private String title;

    /**
     * Creates an instance of PullRequestInner class.
     */
    public PullRequestInner() {
    }

    /**
     * Get the links property: The links to other objects related to this object.
     * 
     * @return the links value.
     */
    public ReferenceLinks links() {
        return this.links;
    }

    /**
     * Set the links property: The links to other objects related to this object.
     * 
     * @param links the links value to set.
     * @return the PullRequestInner object itself.
     */
    public PullRequestInner withLinks(ReferenceLinks links) {
        this.links = links;
        return this;
    }

    /**
     * Get the author property: Author of the pull request.
     * 
     * @return the author value.
     */
    public IdentityRef author() {
        return this.author;
    }

    /**
     * Set the author property: Author of the pull request.
     * 
     * @param author the author value to set.
     * @return the PullRequestInner object itself.
     */
    public PullRequestInner withAuthor(IdentityRef author) {
        this.author = author;
        return this;
    }

    /**
     * Get the currentState property: Current state of the pull request, e.g. open, merged, closed, conflicts, etc.
     * 
     * @return the currentState value.
     */
    public String currentState() {
        return this.currentState;
    }

    /**
     * Set the currentState property: Current state of the pull request, e.g. open, merged, closed, conflicts, etc.
     * 
     * @param currentState the currentState value to set.
     * @return the PullRequestInner object itself.
     */
    public PullRequestInner withCurrentState(String currentState) {
        this.currentState = currentState;
        return this;
    }

    /**
     * Get the description property: Description for the pull request.
     * 
     * @return the description value.
     */
    public String description() {
        return this.description;
    }

    /**
     * Set the description property: Description for the pull request.
     * 
     * @param description the description value to set.
     * @return the PullRequestInner object itself.
     */
    public PullRequestInner withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Get the draft property: Returns if pull request is draft.
     * 
     * @return the draft value.
     */
    public Boolean draft() {
        return this.draft;
    }

    /**
     * Set the draft property: Returns if pull request is draft.
     * 
     * @param draft the draft value to set.
     * @return the PullRequestInner object itself.
     */
    public PullRequestInner withDraft(Boolean draft) {
        this.draft = draft;
        return this;
    }

    /**
     * Get the id property: Unique identifier for the pull request.
     * 
     * @return the id value.
     */
    public String id() {
        return this.id;
    }

    /**
     * Set the id property: Unique identifier for the pull request.
     * 
     * @param id the id value to set.
     * @return the PullRequestInner object itself.
     */
    public PullRequestInner withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * Get the providerName property: The name of the provider this pull request is associated with.
     * 
     * @return the providerName value.
     */
    public String providerName() {
        return this.providerName;
    }

    /**
     * Set the providerName property: The name of the provider this pull request is associated with.
     * 
     * @param providerName the providerName value to set.
     * @return the PullRequestInner object itself.
     */
    public PullRequestInner withProviderName(String providerName) {
        this.providerName = providerName;
        return this;
    }

    /**
     * Get the sourceBranchRef property: Source branch ref of this pull request.
     * 
     * @return the sourceBranchRef value.
     */
    public String sourceBranchRef() {
        return this.sourceBranchRef;
    }

    /**
     * Set the sourceBranchRef property: Source branch ref of this pull request.
     * 
     * @param sourceBranchRef the sourceBranchRef value to set.
     * @return the PullRequestInner object itself.
     */
    public PullRequestInner withSourceBranchRef(String sourceBranchRef) {
        this.sourceBranchRef = sourceBranchRef;
        return this;
    }

    /**
     * Get the sourceRepositoryOwner property: Owner of the source repository of this pull request.
     * 
     * @return the sourceRepositoryOwner value.
     */
    public String sourceRepositoryOwner() {
        return this.sourceRepositoryOwner;
    }

    /**
     * Set the sourceRepositoryOwner property: Owner of the source repository of this pull request.
     * 
     * @param sourceRepositoryOwner the sourceRepositoryOwner value to set.
     * @return the PullRequestInner object itself.
     */
    public PullRequestInner withSourceRepositoryOwner(String sourceRepositoryOwner) {
        this.sourceRepositoryOwner = sourceRepositoryOwner;
        return this;
    }

    /**
     * Get the targetBranchRef property: Target branch ref of this pull request.
     * 
     * @return the targetBranchRef value.
     */
    public String targetBranchRef() {
        return this.targetBranchRef;
    }

    /**
     * Set the targetBranchRef property: Target branch ref of this pull request.
     * 
     * @param targetBranchRef the targetBranchRef value to set.
     * @return the PullRequestInner object itself.
     */
    public PullRequestInner withTargetBranchRef(String targetBranchRef) {
        this.targetBranchRef = targetBranchRef;
        return this;
    }

    /**
     * Get the targetRepositoryOwner property: Owner of the target repository of this pull request.
     * 
     * @return the targetRepositoryOwner value.
     */
    public String targetRepositoryOwner() {
        return this.targetRepositoryOwner;
    }

    /**
     * Set the targetRepositoryOwner property: Owner of the target repository of this pull request.
     * 
     * @param targetRepositoryOwner the targetRepositoryOwner value to set.
     * @return the PullRequestInner object itself.
     */
    public PullRequestInner withTargetRepositoryOwner(String targetRepositoryOwner) {
        this.targetRepositoryOwner = targetRepositoryOwner;
        return this;
    }

    /**
     * Get the title property: Title of the pull request.
     * 
     * @return the title value.
     */
    public String title() {
        return this.title;
    }

    /**
     * Set the title property: Title of the pull request.
     * 
     * @param title the title value to set.
     * @return the PullRequestInner object itself.
     */
    public PullRequestInner withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Validates the instance.
     * 
     * @throws IllegalArgumentException thrown if the instance is not valid.
     */
    public void validate() {
        if (links() != null) {
            links().validate();
        }
        if (author() != null) {
            author().validate();
        }
    }
}
