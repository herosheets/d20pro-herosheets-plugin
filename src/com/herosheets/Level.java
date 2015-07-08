package com.herosheets;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

@JsonIgnoreProperties(ignoreUnknown=true)
public final class Level implements java.io.Serializable{
    public int getRank() {
        return rank;
    }

    public String getClassname() {
        return classname;
    }

    private final int rank;
    private final String classname;

    @JsonCreator
    public Level(@JsonProperty("rank") final int rank,
                 @JsonProperty("classname") final String classname) {
        this.rank = rank;
        this.classname = classname;
    }
}
