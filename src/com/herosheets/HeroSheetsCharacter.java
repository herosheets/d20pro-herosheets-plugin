package com.herosheets;

import com.herosheets.MiniCharacter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

import com.mindgene.d20.common.creature.CreatureTemplate;

public final class HeroSheetsCharacter implements java.io.Serializable {

    /* Constructor. With the @JsonCreator annotation, Jackson will use this
     * method to generate new JacksonImmutableExample objects. */
    @JsonCreator
    public HeroSheetsCharacter(
            @JsonProperty("id") final int id,
            @JsonProperty("uuid") final String uuid ) {
        this.id = id;
        this.uuid = uuid;
    }

    private final int id;
    private final String uuid;

    /* Get the integer value. Jackson will use this during serialization. */
    public int getId() {return this.id;}
    public String getuuid() {
        return this.uuid;
    }

    public CreatureTemplate toCreatureTemplate() {
        return new CreatureTemplate();

    }

}