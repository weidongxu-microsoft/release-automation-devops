// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.implementation;

import com.azure.dev.fluent.models.YamlBuildInner;
import com.azure.dev.models.YamlBuild;

public final class YamlBuildImpl implements YamlBuild {
    private YamlBuildInner innerObject;

    private final com.azure.dev.DevManager serviceManager;

    YamlBuildImpl(YamlBuildInner innerObject, com.azure.dev.DevManager serviceManager) {
        this.innerObject = innerObject;
        this.serviceManager = serviceManager;
    }

    public String yaml() {
        return this.innerModel().yaml();
    }

    public YamlBuildInner innerModel() {
        return this.innerObject;
    }

    private com.azure.dev.DevManager manager() {
        return this.serviceManager;
    }
}