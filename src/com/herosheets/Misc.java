package com.herosheets;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Misc {

    private final Speed speed;
    public Speed getSpeed() {
        return speed;
    }

    public int calculateWalkSpeed() {
        return speed.getLandSpeed();
    }

    @JsonCreator
    public Misc(@JsonProperty("speed") final Speed speed) {
        this.speed = speed;
    }
}
