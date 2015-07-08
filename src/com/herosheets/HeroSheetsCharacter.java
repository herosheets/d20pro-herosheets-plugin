package com.herosheets;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

import com.mindgene.d20.common.D20Rules;
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

    public Attributes getAttributes() {
        return attributes;
    }

    public SavingThrows getSaves() {
        return saves;
    }

    private final Combat combat;
    private final Misc misc;
    private final int id;
    private final String uuid;
    private final Attributes attributes;


    private final SavingThrows saves;

    @JsonCreator
    public HeroSheetsCharacter(
            @JsonProperty("id") final int id,
            @JsonProperty("uuid") final String uuid,
            @JsonProperty("character") final MiniCharacter character,
            @JsonProperty("combat") final Combat combat,
            @JsonProperty("misc") final Misc misc,
            @JsonProperty("attributes") final Attributes attributes,
            @JsonProperty("saves") final SavingThrows saves) {
        this.id = id;
        this.uuid = uuid;
        this.character = character;
        this.combat = combat;
        this.misc = misc;
        this.attributes = attributes;
        this.saves = saves;
    }

    /* Get the integer value. Jackson will use this during serialization. */
    public int getId() {return this.id;}
    public String getuuid() {
        return this.uuid;
    }

    public CreatureTemplate toCreatureTemplate() {
        CreatureTemplate ct = new CreatureTemplate();
        parseBasics(ct);
        parseAttributes(ct);
        parseSaves(ct);
        return ct;
    }

    public void parseBasics(CreatureTemplate ct) {
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
        // TODO : reach is based on weapon, and is this a square?
        ct.setReach((byte) 1);
        // TODO : what is type
        // TODO : how to set size
    }

    public void parseAttributes(CreatureTemplate ct) {
        for (byte i = 0; i < D20Rules.Ability.NAMES.length; i++) {
            String name = D20Rules.Ability.NAMES[i];
            int score = getAttribute(name);
            ct.setAbility(i, (byte) score);
        }
    }

    public void parseSaves(CreatureTemplate ct) {
        for (byte i = 0; i < D20Rules.Save.NAMES.length; i++) {
            String name = D20Rules.Save.NAMES[i];
            int score = getSave(name);
            ct.setSave(i, (byte) score);
        }
    }

    public int getAttribute(String attributeName) {
        return getAttributes().getValues().getByAbbreviation(attributeName);
    }

    public int getSave(String saveName) {
        return getSaves().getByAbbreviation(saveName);
    }

}