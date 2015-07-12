package com.herosheets;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public final class Spells implements java.io.Serializable{

    private final int[] bard;

    public int[] getBard() {
        return bard;
    }

    public int[] getCleric() {
        return cleric;
    }

    public int[] getDruid() {
        return druid;
    }

    public int[] getPaladin() {
        return paladin;
    }

    public int[] getRanger() {
        return ranger;
    }

    public int[] getWizard() {
        return wizard;
    }

    public List<Spell> getSorcererSpells() {
        return sorcererSpells;
    }

    private final int[] cleric;
    private final int[] druid;
    private final int[] paladin;
    private final int[] ranger;
    private final int[] wizard;
    private final List<Spell> sorcererSpells;

    @JsonCreator
    public Spells(@JsonProperty("Bard") final int[] bard,
                  @JsonProperty("Cleric") final int[] cleric,
                  @JsonProperty("Druid") final int[] druid,
                  @JsonProperty("Paladin") final int[] paladin,
                  @JsonProperty("Ranger") final int[] ranger,
                  @JsonProperty("Wizard") final int[] wizard,
                  @JsonProperty("sorcerer_spells") final List<Spell> sorcerer_spells){
        this.bard = bard;
        this.cleric = cleric;
        this.druid = druid;
        this.paladin = paladin;
        this.ranger = ranger;
        this.wizard = wizard;
        this.sorcererSpells = sorcerer_spells;
    }

}
