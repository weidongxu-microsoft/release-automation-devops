// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.implementation;

import com.azure.dev.fluent.models.BuildDefinitionInner;
import com.azure.dev.fluent.models.BuildDefinitionTemplateInner;
import com.azure.dev.models.BuildDefinition;
import com.azure.dev.models.BuildDefinitionTemplate;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public final class BuildDefinitionTemplateImpl implements BuildDefinitionTemplate {
    private BuildDefinitionTemplateInner innerObject;

    private final com.azure.dev.DevManager serviceManager;

    BuildDefinitionTemplateImpl(BuildDefinitionTemplateInner innerObject, com.azure.dev.DevManager serviceManager) {
        this.innerObject = innerObject;
        this.serviceManager = serviceManager;
    }

    public Boolean canDelete() {
        return this.innerModel().canDelete();
    }

    public String category() {
        return this.innerModel().category();
    }

    public String defaultHostedQueue() {
        return this.innerModel().defaultHostedQueue();
    }

    public String description() {
        return this.innerModel().description();
    }

    public Map<String, String> icons() {
        Map<String, String> inner = this.innerModel().icons();
        if (inner != null) {
            return Collections.unmodifiableMap(inner);
        } else {
            return Collections.emptyMap();
        }
    }

    public UUID iconTaskId() {
        return this.innerModel().iconTaskId();
    }

    public String id() {
        return this.innerModel().id();
    }

    public String name() {
        return this.innerModel().name();
    }

    public BuildDefinition template() {
        BuildDefinitionInner inner = this.innerModel().template();
        if (inner != null) {
            return new BuildDefinitionImpl(inner, this.manager());
        } else {
            return null;
        }
    }

    public BuildDefinitionTemplateInner innerModel() {
        return this.innerObject;
    }

    private com.azure.dev.DevManager manager() {
        return this.serviceManager;
    }
}
