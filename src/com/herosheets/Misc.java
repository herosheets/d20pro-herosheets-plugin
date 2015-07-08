package com.herosheets;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public final class Misc implements java.io.Serializable {

    private final Speed speed;
    private final List<Level> levels;
    private final List<Feat> feats;

    public Speed getSpeed() {
        return speed;
    }

    public int calculateWalkSpeed() {
        return speed.getLandSpeed();
    }

    public List<Level> getLevels() {
        return levels;
    }
    public List<Feat> getFeats() {
        return feats;
    }


    @JsonCreator
    public Misc(@JsonProperty("speed") final Speed speed,
                @JsonProperty("levels") final List<Level> levels,
                @JsonProperty("feats") final List<Feat> feats) {
        this.speed = speed;
        this.levels = levels;
        this.feats = feats;
    }
}
