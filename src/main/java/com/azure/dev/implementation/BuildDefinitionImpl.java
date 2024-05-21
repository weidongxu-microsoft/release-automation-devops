// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.implementation;

import com.azure.dev.fluent.models.BuildDefinitionInner;
import com.azure.dev.fluent.models.BuildInner;
import com.azure.dev.fluent.models.BuildMetricInner;
import com.azure.dev.fluent.models.PropertiesCollectionInner;
import com.azure.dev.models.AgentPoolQueue;
import com.azure.dev.models.Build;
import com.azure.dev.models.BuildAuthorizationScope;
import com.azure.dev.models.BuildDefinition;
import com.azure.dev.models.BuildDefinitionVariable;
import com.azure.dev.models.BuildMetric;
import com.azure.dev.models.BuildOption;
import com.azure.dev.models.BuildProcess;
import com.azure.dev.models.BuildRepository;
import com.azure.dev.models.BuildTrigger;
import com.azure.dev.models.DefinitionQuality;
import com.azure.dev.models.DefinitionQueueStatus;
import com.azure.dev.models.DefinitionReference;
import com.azure.dev.models.DefinitionType;
import com.azure.dev.models.Demand;
import com.azure.dev.models.IdentityRef;
import com.azure.dev.models.ProcessParameters;
import com.azure.dev.models.PropertiesCollection;
import com.azure.dev.models.ReferenceLinks;
import com.azure.dev.models.RetentionPolicy;
import com.azure.dev.models.TeamProjectReference;
import com.azure.dev.models.VariableGroup;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class BuildDefinitionImpl implements BuildDefinition {
    private BuildDefinitionInner innerObject;

    private final com.azure.dev.DevManager serviceManager;

    BuildDefinitionImpl(BuildDefinitionInner innerObject, com.azure.dev.DevManager serviceManager) {
        this.innerObject = innerObject;
        this.serviceManager = serviceManager;
    }

    public OffsetDateTime createdDate() {
        return this.innerModel().createdDate();
    }

    public Integer id() {
        return this.innerModel().id();
    }

    public String name() {
        return this.innerModel().name();
    }

    public String path() {
        return this.innerModel().path();
    }

    public TeamProjectReference project() {
        return this.innerModel().project();
    }

    public DefinitionQueueStatus queueStatus() {
        return this.innerModel().queueStatus();
    }

    public Integer revision() {
        return this.innerModel().revision();
    }

    public DefinitionType type() {
        return this.innerModel().type();
    }

    public String uri() {
        return this.innerModel().uri();
    }

    public String url() {
        return this.innerModel().url();
    }

    public ReferenceLinks links() {
        return this.innerModel().links();
    }

    public IdentityRef authoredBy() {
        return this.innerModel().authoredBy();
    }

    public DefinitionReference draftOf() {
        return this.innerModel().draftOf();
    }

    public List<DefinitionReference> drafts() {
        List<DefinitionReference> inner = this.innerModel().drafts();
        if (inner != null) {
            return Collections.unmodifiableList(inner);
        } else {
            return Collections.emptyList();
        }
    }

    public Build latestBuild() {
        BuildInner inner = this.innerModel().latestBuild();
        if (inner != null) {
            return new BuildImpl(inner, this.manager());
        } else {
            return null;
        }
    }

    public Build latestCompletedBuild() {
        BuildInner inner = this.innerModel().latestCompletedBuild();
        if (inner != null) {
            return new BuildImpl(inner, this.manager());
        } else {
            return null;
        }
    }

    public List<BuildMetric> metrics() {
        List<BuildMetricInner> inner = this.innerModel().metrics();
        if (inner != null) {
            return Collections.unmodifiableList(
                inner.stream().map(inner1 -> new BuildMetricImpl(inner1, this.manager())).collect(Collectors.toList()));
        } else {
            return Collections.emptyList();
        }
    }

    public DefinitionQuality quality() {
        return this.innerModel().quality();
    }

    public AgentPoolQueue queue() {
        return this.innerModel().queue();
    }

    public Boolean badgeEnabled() {
        return this.innerModel().badgeEnabled();
    }

    public String buildNumberFormat() {
        return this.innerModel().buildNumberFormat();
    }

    public String comment() {
        return this.innerModel().comment();
    }

    public List<Demand> demands() {
        List<Demand> inner = this.innerModel().demands();
        if (inner != null) {
            return Collections.unmodifiableList(inner);
        } else {
            return Collections.emptyList();
        }
    }

    public String description() {
        return this.innerModel().description();
    }

    public String dropLocation() {
        return this.innerModel().dropLocation();
    }

    public BuildAuthorizationScope jobAuthorizationScope() {
        return this.innerModel().jobAuthorizationScope();
    }

    public Integer jobCancelTimeoutInMinutes() {
        return this.innerModel().jobCancelTimeoutInMinutes();
    }

    public Integer jobTimeoutInMinutes() {
        return this.innerModel().jobTimeoutInMinutes();
    }

    public List<BuildOption> options() {
        List<BuildOption> inner = this.innerModel().options();
        if (inner != null) {
            return Collections.unmodifiableList(inner);
        } else {
            return Collections.emptyList();
        }
    }

    public BuildProcess process() {
        return this.innerModel().process();
    }

    public ProcessParameters processParameters() {
        return this.innerModel().processParameters();
    }

    public PropertiesCollection properties() {
        PropertiesCollectionInner inner = this.innerModel().properties();
        if (inner != null) {
            return new PropertiesCollectionImpl(inner, this.manager());
        } else {
            return null;
        }
    }

    public BuildRepository repository() {
        return this.innerModel().repository();
    }

    public List<RetentionPolicy> retentionRules() {
        List<RetentionPolicy> inner = this.innerModel().retentionRules();
        if (inner != null) {
            return Collections.unmodifiableList(inner);
        } else {
            return Collections.emptyList();
        }
    }

    public List<String> tags() {
        List<String> inner = this.innerModel().tags();
        if (inner != null) {
            return Collections.unmodifiableList(inner);
        } else {
            return Collections.emptyList();
        }
    }

    public List<BuildTrigger> triggers() {
        List<BuildTrigger> inner = this.innerModel().triggers();
        if (inner != null) {
            return Collections.unmodifiableList(inner);
        } else {
            return Collections.emptyList();
        }
    }

    public List<VariableGroup> variableGroups() {
        List<VariableGroup> inner = this.innerModel().variableGroups();
        if (inner != null) {
            return Collections.unmodifiableList(inner);
        } else {
            return Collections.emptyList();
        }
    }

    public Map<String, BuildDefinitionVariable> variables() {
        Map<String, BuildDefinitionVariable> inner = this.innerModel().variables();
        if (inner != null) {
            return Collections.unmodifiableMap(inner);
        } else {
            return Collections.emptyMap();
        }
    }

    public BuildDefinitionInner innerModel() {
        return this.innerObject;
    }

    private com.azure.dev.DevManager manager() {
        return this.serviceManager;
    }
}
