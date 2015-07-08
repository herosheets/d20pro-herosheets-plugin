package com.herosheets;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public final class AttributeValues implements java.io.Serializable {
    public int getStrength() {
        return strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public int getWisdom() {
        return wisdom;
    }

    public int getCharisma() {
        return charisma;
    }

    public int getConstitution() {
        return constitution;
    }

    public int getIntelligence() {
        return intelligence;
    }

    private final int strength;
    private final int dexterity;
    private final int wisdom;
    private final int charisma;
    private final int constitution;
    private final int intelligence;

    @JsonCreator
    public AttributeValues(@JsonProperty("strength") final int strength,
                           @JsonProperty("dexterity") final int dexterity,
                           @JsonProperty("charisma") final int charisma,
                           @JsonProperty("intelligence") final int intelligence,
                           @JsonProperty("wisdom") final int wisdom,
                           @JsonProperty("constitution") final int constitution) {
        this.strength = strength;
        this.dexterity = dexterity;
        this.wisdom = wisdom;
        this.intelligence = intelligence;
        this.charisma = charisma;
        this.constitution = constitution;
    }

    public int getByAbbreviation(String abbreviation) {
        System.out.println("Looking up " + abbreviation);
        System.out.println("This.strength" + this.strength);
        switch (abbreviation) {
            case "STR":
                return strength;
            case "DEX":
                return dexterity;
            case "WIS":
                return wisdom;
            case "CHA":
                return charisma;
            case "CON":
                return constitution;
            case "INT":
                return intelligence;
            default:
                return 0;
        }

    }
}
