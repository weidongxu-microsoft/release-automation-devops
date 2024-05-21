// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.fluent.models;

import com.azure.core.annotation.Fluent;
import com.azure.dev.models.PipelineReference;
import com.azure.dev.models.ReferenceLinks;
import com.azure.dev.models.RunReference;
import com.azure.dev.models.RunResources;
import com.azure.dev.models.RunResult;
import com.azure.dev.models.RunState;
import com.azure.dev.models.Variable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.Map;

/**
 * The Run model.
 */
@Fluent
public final class RunInner extends RunReference {
    /*
     * The class to represent a collection of REST reference links.
     */
    @JsonProperty(value = "_links")
    private ReferenceLinks links;

    /*
     * The createdDate property.
     */
    @JsonProperty(value = "createdDate")
    private OffsetDateTime createdDate;

    /*
     * The finalYaml property.
     */
    @JsonProperty(value = "finalYaml")
    private String finalYaml;

    /*
     * The finishedDate property.
     */
    @JsonProperty(value = "finishedDate")
    private OffsetDateTime finishedDate;

    /*
     * A reference to a Pipeline.
     */
    @JsonProperty(value = "pipeline")
    private PipelineReference pipeline;

    /*
     * The resources property.
     */
    @JsonProperty(value = "resources")
    private RunResources resources;

    /*
     * The result property.
     */
    @JsonProperty(value = "result")
    private RunResult result;

    /*
     * The state property.
     */
    @JsonProperty(value = "state")
    private RunState state;

    /*
     * Dictionary of <AnyObject>
     */
    @JsonProperty(value = "templateParameters")
    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.ALWAYS)
    private Map<String, Object> templateParameters;

    /*
     * The url property.
     */
    @JsonProperty(value = "url")
    private String url;

    /*
     * Dictionary of <Variable>
     */
    @JsonProperty(value = "variables")
    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.ALWAYS)
    private Map<String, Variable> variables;

    /**
     * Creates an instance of RunInner class.
     */
    public RunInner() {
    }

    /**
     * Get the links property: The class to represent a collection of REST reference links.
     * 
     * @return the links value.
     */
    public ReferenceLinks links() {
        return this.links;
    }

    /**
     * Set the links property: The class to represent a collection of REST reference links.
     * 
     * @param links the links value to set.
     * @return the RunInner object itself.
     */
    public RunInner withLinks(ReferenceLinks links) {
        this.links = links;
        return this;
    }

    /**
     * Get the createdDate property: The createdDate property.
     * 
     * @return the createdDate value.
     */
    public OffsetDateTime createdDate() {
        return this.createdDate;
    }

    /**
     * Set the createdDate property: The createdDate property.
     * 
     * @param createdDate the createdDate value to set.
     * @return the RunInner object itself.
     */
    public RunInner withCreatedDate(OffsetDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    /**
     * Get the finalYaml property: The finalYaml property.
     * 
     * @return the finalYaml value.
     */
    public String finalYaml() {
        return this.finalYaml;
    }

    /**
     * Set the finalYaml property: The finalYaml property.
     * 
     * @param finalYaml the finalYaml value to set.
     * @return the RunInner object itself.
     */
    public RunInner withFinalYaml(String finalYaml) {
        this.finalYaml = finalYaml;
        return this;
    }

    /**
     * Get the finishedDate property: The finishedDate property.
     * 
     * @return the finishedDate value.
     */
    public OffsetDateTime finishedDate() {
        return this.finishedDate;
    }

    /**
     * Set the finishedDate property: The finishedDate property.
     * 
     * @param finishedDate the finishedDate value to set.
     * @return the RunInner object itself.
     */
    public RunInner withFinishedDate(OffsetDateTime finishedDate) {
        this.finishedDate = finishedDate;
        return this;
    }

    /**
     * Get the pipeline property: A reference to a Pipeline.
     * 
     * @return the pipeline value.
     */
    public PipelineReference pipeline() {
        return this.pipeline;
    }

    /**
     * Set the pipeline property: A reference to a Pipeline.
     * 
     * @param pipeline the pipeline value to set.
     * @return the RunInner object itself.
     */
    public RunInner withPipeline(PipelineReference pipeline) {
        this.pipeline = pipeline;
        return this;
    }

    /**
     * Get the resources property: The resources property.
     * 
     * @return the resources value.
     */
    public RunResources resources() {
        return this.resources;
    }

    /**
     * Set the resources property: The resources property.
     * 
     * @param resources the resources value to set.
     * @return the RunInner object itself.
     */
    public RunInner withResources(RunResources resources) {
        this.resources = resources;
        return this;
    }

    /**
     * Get the result property: The result property.
     * 
     * @return the result value.
     */
    public RunResult result() {
        return this.result;
    }

    /**
     * Set the result property: The result property.
     * 
     * @param result the result value to set.
     * @return the RunInner object itself.
     */
    public RunInner withResult(RunResult result) {
        this.result = result;
        return this;
    }

    /**
     * Get the state property: The state property.
     * 
     * @return the state value.
     */
    public RunState state() {
        return this.state;
    }

    /**
     * Set the state property: The state property.
     * 
     * @param state the state value to set.
     * @return the RunInner object itself.
     */
    public RunInner withState(RunState state) {
        this.state = state;
        return this;
    }

    /**
     * Get the templateParameters property: Dictionary of &lt;AnyObject&gt;.
     * 
     * @return the templateParameters value.
     */
    public Map<String, Object> templateParameters() {
        return this.templateParameters;
    }

    /**
     * Set the templateParameters property: Dictionary of &lt;AnyObject&gt;.
     * 
     * @param templateParameters the templateParameters value to set.
     * @return the RunInner object itself.
     */
    public RunInner withTemplateParameters(Map<String, Object> templateParameters) {
        this.templateParameters = templateParameters;
        return this;
    }

    /**
     * Get the url property: The url property.
     * 
     * @return the url value.
     */
    public String url() {
        return this.url;
    }

    /**
     * Set the url property: The url property.
     * 
     * @param url the url value to set.
     * @return the RunInner object itself.
     */
    public RunInner withUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * Get the variables property: Dictionary of &lt;Variable&gt;.
     * 
     * @return the variables value.
     */
    public Map<String, Variable> variables() {
        return this.variables;
    }

    /**
     * Set the variables property: Dictionary of &lt;Variable&gt;.
     * 
     * @param variables the variables value to set.
     * @return the RunInner object itself.
     */
    public RunInner withVariables(Map<String, Variable> variables) {
        this.variables = variables;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RunInner withId(Integer id) {
        super.withId(id);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RunInner withName(String name) {
        super.withName(name);
        return this;
    }

    /**
     * Validates the instance.
     * 
     * @throws IllegalArgumentException thrown if the instance is not valid.
     */
    @Override
    public void validate() {
        super.validate();
        if (links() != null) {
            links().validate();
        }
        if (pipeline() != null) {
            pipeline().validate();
        }
        if (resources() != null) {
            resources().validate();
        }
        if (variables() != null) {
            variables().values().forEach(e -> {
                if (e != null) {
                    e.validate();
                }
            });
        }
    }
}
