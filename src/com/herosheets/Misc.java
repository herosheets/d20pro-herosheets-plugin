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
    private final List<Skill> skills;

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
    public List<Skill> getSkills() { return skills; }

    @JsonCreator
    public Misc(@JsonProperty("speed") final Speed speed,
                @JsonProperty("levels") final List<Level> levels,
                @JsonProperty("feats") final List<Feat> feats,
                @JsonProperty("plugin_skills") final List<Skill> plugin_skills) {
        this.speed = speed;
        this.levels = levels;
        this.feats = feats;
        this.skills = plugin_skills;
    }
}
