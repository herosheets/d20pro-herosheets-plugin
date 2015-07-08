package com.herosheets;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

@JsonIgnoreProperties(ignoreUnknown=true)
public final class Skill implements java.io.Serializable {

    private final String name;
    private final String attribute;
    private final int bonus;
    private final int ranks;

    public int getRanks() {
        return ranks;
    }

    public String getName() {
        return name;
    }

    public String getAttribute() {
        return attribute;
    }

    public int getBonus() {
        return bonus;
    }

    @JsonCreator
    public Skill(@JsonProperty("name") final String name,
                 @JsonProperty("attribute") final String attribute,
                 @JsonProperty("bonus") final int bonus,
                 @JsonProperty("ranks") final int ranks) {
        this.name = name;
        this.attribute = attribute;
        this.bonus = bonus;
        this.ranks = ranks;
    }
}
