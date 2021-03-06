// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.implementation;

import com.azure.dev.fluent.models.BuildInner;
import com.azure.dev.fluent.models.BuildUpdatedEventInner;
import com.azure.dev.models.Build;
import com.azure.dev.models.BuildUpdatedEvent;

public final class BuildUpdatedEventImpl implements BuildUpdatedEvent {
    private BuildUpdatedEventInner innerObject;

    private final com.azure.dev.DevManager serviceManager;

    BuildUpdatedEventImpl(BuildUpdatedEventInner innerObject, com.azure.dev.DevManager serviceManager) {
        this.innerObject = innerObject;
        this.serviceManager = serviceManager;
    }

    public Integer buildId() {
        return this.innerModel().buildId();
    }

    public Build build() {
        BuildInner inner = this.innerModel().build();
        if (inner != null) {
            return new BuildImpl(inner, this.manager());
        } else {
            return null;
        }
    }

    public BuildUpdatedEventInner innerModel() {
        return this.innerObject;
    }

    private com.azure.dev.DevManager manager() {
        return this.serviceManager;
    }
}
