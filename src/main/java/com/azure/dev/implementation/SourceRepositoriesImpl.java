// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.implementation;

import com.azure.dev.fluent.models.SourceRepositoriesInner;
import com.azure.dev.models.SourceRepositories;
import com.azure.dev.models.SourceRepository;
import java.util.Collections;
import java.util.List;

public final class SourceRepositoriesImpl implements SourceRepositories {
    private SourceRepositoriesInner innerObject;

    private final com.azure.dev.DevManager serviceManager;

    SourceRepositoriesImpl(SourceRepositoriesInner innerObject, com.azure.dev.DevManager serviceManager) {
        this.innerObject = innerObject;
        this.serviceManager = serviceManager;
    }

    public String continuationToken() {
        return this.innerModel().continuationToken();
    }

    public Integer pageLength() {
        return this.innerModel().pageLength();
    }

    public List<SourceRepository> repositories() {
        List<SourceRepository> inner = this.innerModel().repositories();
        if (inner != null) {
            return Collections.unmodifiableList(inner);
        } else {
            return Collections.emptyList();
        }
    }

    public Integer totalPageCount() {
        return this.innerModel().totalPageCount();
    }

    public SourceRepositoriesInner innerModel() {
        return this.innerObject;
    }

    private com.azure.dev.DevManager manager() {
        return this.serviceManager;
    }
}
