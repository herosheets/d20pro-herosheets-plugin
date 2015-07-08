package com.herosheets;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public final class Misc implements java.io.Serializable {

    private final Speed speed;
    private final List<Level> levels;

    public Speed getSpeed() {
        return speed;
    }

    public int calculateWalkSpeed() {
        return speed.getLandSpeed();
    }

    public List<Level> getLevels() {
        return levels;
    }

    @JsonCreator
    public Misc(@JsonProperty("speed") final Speed speed,
                @JsonProperty("levels") final List<Level> levels) {
        this.speed = speed;
        this.levels = levels;
    }
}
