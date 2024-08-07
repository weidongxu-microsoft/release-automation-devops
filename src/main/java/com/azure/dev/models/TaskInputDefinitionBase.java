// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

/**
 * The TaskInputDefinitionBase model.
 */
@Fluent
public final class TaskInputDefinitionBase {
    /*
     * The aliases property.
     */
    @JsonProperty(value = "aliases")
    private List<String> aliases;

    /*
     * The defaultValue property.
     */
    @JsonProperty(value = "defaultValue")
    private String defaultValue;

    /*
     * The groupName property.
     */
    @JsonProperty(value = "groupName")
    private String groupName;

    /*
     * The helpMarkDown property.
     */
    @JsonProperty(value = "helpMarkDown")
    private String helpMarkDown;

    /*
     * The label property.
     */
    @JsonProperty(value = "label")
    private String label;

    /*
     * The name property.
     */
    @JsonProperty(value = "name")
    private String name;

    /*
     * Dictionary of <string>
     */
    @JsonProperty(value = "options")
    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.ALWAYS)
    private Map<String, String> options;

    /*
     * Dictionary of <string>
     */
    @JsonProperty(value = "properties")
    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.ALWAYS)
    private Map<String, String> properties;

    /*
     * The required property.
     */
    @JsonProperty(value = "required")
    private Boolean required;

    /*
     * The type property.
     */
    @JsonProperty(value = "type")
    private String type;

    /*
     * The validation property.
     */
    @JsonProperty(value = "validation")
    private TaskInputValidation validation;

    /*
     * The visibleRule property.
     */
    @JsonProperty(value = "visibleRule")
    private String visibleRule;

    /**
     * Creates an instance of TaskInputDefinitionBase class.
     */
    public TaskInputDefinitionBase() {
    }

    /**
     * Get the aliases property: The aliases property.
     * 
     * @return the aliases value.
     */
    public List<String> aliases() {
        return this.aliases;
    }

    /**
     * Set the aliases property: The aliases property.
     * 
     * @param aliases the aliases value to set.
     * @return the TaskInputDefinitionBase object itself.
     */
    public TaskInputDefinitionBase withAliases(List<String> aliases) {
        this.aliases = aliases;
        return this;
    }

    /**
     * Get the defaultValue property: The defaultValue property.
     * 
     * @return the defaultValue value.
     */
    public String defaultValue() {
        return this.defaultValue;
    }

    /**
     * Set the defaultValue property: The defaultValue property.
     * 
     * @param defaultValue the defaultValue value to set.
     * @return the TaskInputDefinitionBase object itself.
     */
    public TaskInputDefinitionBase withDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    /**
     * Get the groupName property: The groupName property.
     * 
     * @return the groupName value.
     */
    public String groupName() {
        return this.groupName;
    }

    /**
     * Set the groupName property: The groupName property.
     * 
     * @param groupName the groupName value to set.
     * @return the TaskInputDefinitionBase object itself.
     */
    public TaskInputDefinitionBase withGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    /**
     * Get the helpMarkDown property: The helpMarkDown property.
     * 
     * @return the helpMarkDown value.
     */
    public String helpMarkDown() {
        return this.helpMarkDown;
    }

    /**
     * Set the helpMarkDown property: The helpMarkDown property.
     * 
     * @param helpMarkDown the helpMarkDown value to set.
     * @return the TaskInputDefinitionBase object itself.
     */
    public TaskInputDefinitionBase withHelpMarkDown(String helpMarkDown) {
        this.helpMarkDown = helpMarkDown;
        return this;
    }

    /**
     * Get the label property: The label property.
     * 
     * @return the label value.
     */
    public String label() {
        return this.label;
    }

    /**
     * Set the label property: The label property.
     * 
     * @param label the label value to set.
     * @return the TaskInputDefinitionBase object itself.
     */
    public TaskInputDefinitionBase withLabel(String label) {
        this.label = label;
        return this;
    }

    /**
     * Get the name property: The name property.
     * 
     * @return the name value.
     */
    public String name() {
        return this.name;
    }

    /**
     * Set the name property: The name property.
     * 
     * @param name the name value to set.
     * @return the TaskInputDefinitionBase object itself.
     */
    public TaskInputDefinitionBase withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get the options property: Dictionary of &lt;string&gt;.
     * 
     * @return the options value.
     */
    public Map<String, String> options() {
        return this.options;
    }

    /**
     * Set the options property: Dictionary of &lt;string&gt;.
     * 
     * @param options the options value to set.
     * @return the TaskInputDefinitionBase object itself.
     */
    public TaskInputDefinitionBase withOptions(Map<String, String> options) {
        this.options = options;
        return this;
    }

    /**
     * Get the properties property: Dictionary of &lt;string&gt;.
     * 
     * @return the properties value.
     */
    public Map<String, String> properties() {
        return this.properties;
    }

    /**
     * Set the properties property: Dictionary of &lt;string&gt;.
     * 
     * @param properties the properties value to set.
     * @return the TaskInputDefinitionBase object itself.
     */
    public TaskInputDefinitionBase withProperties(Map<String, String> properties) {
        this.properties = properties;
        return this;
    }

    /**
     * Get the required property: The required property.
     * 
     * @return the required value.
     */
    public Boolean required() {
        return this.required;
    }

    /**
     * Set the required property: The required property.
     * 
     * @param required the required value to set.
     * @return the TaskInputDefinitionBase object itself.
     */
    public TaskInputDefinitionBase withRequired(Boolean required) {
        this.required = required;
        return this;
    }

    /**
     * Get the type property: The type property.
     * 
     * @return the type value.
     */
    public String type() {
        return this.type;
    }

    /**
     * Set the type property: The type property.
     * 
     * @param type the type value to set.
     * @return the TaskInputDefinitionBase object itself.
     */
    public TaskInputDefinitionBase withType(String type) {
        this.type = type;
        return this;
    }

    /**
     * Get the validation property: The validation property.
     * 
     * @return the validation value.
     */
    public TaskInputValidation validation() {
        return this.validation;
    }

    /**
     * Set the validation property: The validation property.
     * 
     * @param validation the validation value to set.
     * @return the TaskInputDefinitionBase object itself.
     */
    public TaskInputDefinitionBase withValidation(TaskInputValidation validation) {
        this.validation = validation;
        return this;
    }

    /**
     * Get the visibleRule property: The visibleRule property.
     * 
     * @return the visibleRule value.
     */
    public String visibleRule() {
        return this.visibleRule;
    }

    /**
     * Set the visibleRule property: The visibleRule property.
     * 
     * @param visibleRule the visibleRule value to set.
     * @return the TaskInputDefinitionBase object itself.
     */
    public TaskInputDefinitionBase withVisibleRule(String visibleRule) {
        this.visibleRule = visibleRule;
        return this;
    }

    /**
     * Validates the instance.
     * 
     * @throws IllegalArgumentException thrown if the instance is not valid.
     */
    public void validate() {
        if (validation() != null) {
            validation().validate();
        }
    }
}
