package com.herosheets;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public final class Spellbook implements java.io.Serializable {

    private final SpellLevel bard;
    private final SpellLevel cleric;
    private final SpellLevel druid;
    private final SpellLevel paladin;
    private final SpellLevel ranger;
    private final SpellLevel wizard;

    public SpellLevel getBard() {
        return bard;
    }

    public SpellLevel getCleric() {
        return cleric;
    }

    public SpellLevel getDruid() {
        return druid;
    }

    public SpellLevel getPaladin() {
        return paladin;
    }

    public SpellLevel getRanger() {
        return ranger;
    }

    public SpellLevel getWizard() {
        return wizard;
    }

    @JsonCreator
    public Spellbook(@JsonProperty("Bard") final SpellLevel bard,
                  @JsonProperty("Cleric") final SpellLevel cleric,
                  @JsonProperty("Druid") final SpellLevel druid,
                  @JsonProperty("Paladin") final SpellLevel paladin,
                  @JsonProperty("Ranger") final SpellLevel ranger,
                  @JsonProperty("Wizard") final SpellLevel wizard){

        this.bard = bard;
        this.cleric = cleric;
        this.druid = druid;
        this.paladin = paladin;
        this.ranger = ranger;
        this.wizard = wizard;
    }

    public Spell[][] getSpellsForClassName(String classname) {
        switch (classname) {
            case "bard":
                if (getBard() != null) {
                    return getBard().getSpellsInOrder();
                } else {
                    return new Spell[10][0];
                }
            case "cleric":
                if (getCleric() != null) {
                    return getCleric().getSpellsInOrder();

                }else {
                    return new Spell[10][0];
                }
            case "druid":
                if (getDruid() != null) {
                    return getDruid().getSpellsInOrder();

                }else {
                    return new Spell[10][0];
                }
            case "paladin":
                if (getPaladin() != null) {
                    return getPaladin().getSpellsInOrder();

                }else {
                    return new Spell[10][0];
                }
            case "ranger":
                if (getRanger() != null) {
                    return getRanger().getSpellsInOrder();

                }else {
                    return new Spell[10][0];
                }

            case "wizard":
                if (getWizard() != null) {
                    return getWizard().getSpellsInOrder();
                }else {
                    return new Spell[10][0];
                }

            default:
                return new Spell[10][0];
        }

    }

}
