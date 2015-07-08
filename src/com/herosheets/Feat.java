package com.herosheets;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

@JsonIgnoreProperties(ignoreUnknown=true)
public final class Feat implements java.io.Serializable {

    private final String name;

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    private final String description;


    @JsonCreator
    public Feat(@JsonProperty("name") final String name,
                @JsonProperty("description") final String description) {
        this.name = name;
        this.description = description;
    }
}
