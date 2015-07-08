package com.herosheets;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Speed {
    public int getLandSpeed() {
        return landSpeed;
    }

    public int getFlySpeed() {
        return flySpeed;
    }

    public int getClimbSpeed() {
        return climbSpeed;
    }

    public int getSwimSpeed() {
        return swimSpeed;
    }

    public int getSpeed() {
        return speed;
    }

    private final int landSpeed;
    private final int flySpeed;
    private final int climbSpeed;
    private final int swimSpeed;
    private final int speed;

    @JsonCreator
    public Speed(@JsonProperty("land_speed") final int land_speed,
                 @JsonProperty("fly_speed") final int fly_speed,
                 @JsonProperty("climb_speed") final int climb_speed,
                 @JsonProperty("swim_speed") final int swim_speed,
                 @JsonProperty("speed") final int speed) {
        this.landSpeed = land_speed;
        this.flySpeed = fly_speed;
        this.climbSpeed = climb_speed;
        this.swimSpeed = swim_speed;
        this.speed = speed;
    }
}
