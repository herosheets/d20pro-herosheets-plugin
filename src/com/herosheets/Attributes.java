package com.herosheets;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Attributes {

    private final AttributeValues values;
    public AttributeValues getValues() {
        return values;
    }


    @JsonCreator
    public Attributes(@JsonProperty("values") final AttributeValues values) {
        this.values = values;
    }
}
