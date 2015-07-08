package com.herosheets;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

import com.mindgene.d20.common.creature.CreatureSpeeds;
import com.mindgene.d20.common.creature.CreatureTemplate;

@JsonIgnoreProperties(ignoreUnknown=true)
public final class HeroSheetsCharacter implements java.io.Serializable {

    public MiniCharacter getCharacter() {
        return character;
    }

    private final MiniCharacter character;

    public Combat getCombat() {
        return combat;
    }

    public Misc getMisc() {
        return misc;
    }

    private final Combat combat;
    private final Misc misc;
    private final int id;
    private final String uuid;

    @JsonCreator
    public HeroSheetsCharacter(
            @JsonProperty("id") final int id,
            @JsonProperty("uuid") final String uuid,
            @JsonProperty("character") final MiniCharacter character,
            @JsonProperty("combat") final Combat combat,
            @JsonProperty("misc") final Misc misc) {
        this.id = id;
        this.uuid = uuid;
        this.character = character;
        this.combat = combat;
        this.misc = misc;
    }

    /* Get the integer value. Jackson will use this during serialization. */
    public int getId() {return this.id;}
    public String getuuid() {
        return this.uuid;
    }

    public CreatureTemplate toCreatureTemplate() {
        CreatureTemplate ct = new CreatureTemplate();
        ct.setGameSystem("PFRPG");
        ct.setName(character.getCharacterName());
        ct.setAlignment(character.getAlignment());
        ct.setHP((short) character.getHitPoints());
        ct.setHPMax((short) character.getHitPoints());
        ct.setHitDice(character.getHitDice());
        ct.setMaxDexBonus((short) Integer.parseInt(getCombat().getDexBonus()));
        // TODO : figure out saves, weird API (two bytes)
        int speed = misc.calculateWalkSpeed();
        ct.accessSpeeds().assignLegacySpeed(CreatureSpeeds.feetToSquares(speed));

        
        return ct;
    }

}