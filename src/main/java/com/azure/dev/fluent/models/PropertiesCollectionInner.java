// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.dev.fluent.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The class represents a property bag as a collection of key-value pairs. Values of all primitive types (any type with
 * a `TypeCode != TypeCode.Object`) except for `DBNull` are accepted. Values of type Byte[], Int32, Double, DateType and
 * String preserve their type, other primitives are retuned as a String. Byte[] expected as base64 encoded string.
 */
@Fluent
public final class PropertiesCollectionInner {
    /*
     * The count of properties in the collection.
     */
    @JsonProperty(value = "count")
    private Integer count;

    /*
     * Any object
     */
    @JsonProperty(value = "item")
    private Object item;

    /*
     * The set of keys in the collection.
     */
    @JsonProperty(value = "keys")
    private List<String> keys;

    /*
     * The set of values in the collection.
     */
    @JsonProperty(value = "values")
    private List<String> values;

    /**
     * Creates an instance of PropertiesCollectionInner class.
     */
    public PropertiesCollectionInner() {
    }

    /**
     * Get the count property: The count of properties in the collection.
     * 
     * @return the count value.
     */
    public Integer count() {
        return this.count;
    }

    /**
     * Set the count property: The count of properties in the collection.
     * 
     * @param count the count value to set.
     * @return the PropertiesCollectionInner object itself.
     */
    public PropertiesCollectionInner withCount(Integer count) {
        this.count = count;
        return this;
    }

    /**
     * Get the item property: Any object.
     * 
     * @return the item value.
     */
    public Object item() {
        return this.item;
    }

    /**
     * Set the item property: Any object.
     * 
     * @param item the item value to set.
     * @return the PropertiesCollectionInner object itself.
     */
    public PropertiesCollectionInner withItem(Object item) {
        this.item = item;
        return this;
    }

    /**
     * Get the keys property: The set of keys in the collection.
     * 
     * @return the keys value.
     */
    public List<String> keys() {
        return this.keys;
    }

    /**
     * Set the keys property: The set of keys in the collection.
     * 
     * @param keys the keys value to set.
     * @return the PropertiesCollectionInner object itself.
     */
    public PropertiesCollectionInner withKeys(List<String> keys) {
        this.keys = keys;
        return this;
    }

    /**
     * Get the values property: The set of values in the collection.
     * 
     * @return the values value.
     */
    public List<String> values() {
        return this.values;
    }

    /**
     * Set the values property: The set of values in the collection.
     * 
     * @param values the values value to set.
     * @return the PropertiesCollectionInner object itself.
     */
    public PropertiesCollectionInner withValues(List<String> values) {
        this.values = values;
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
