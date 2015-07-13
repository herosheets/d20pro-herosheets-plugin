package com.herosheets;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public final class Combat implements java.io.Serializable {

    private final int bab;
    private final int strBonus;

    public int getBab() {
        return bab;
    }

    public int getStrBonus() {
        return strBonus;
    }

    public int getDexBonus() {
        return dexBonus;
    }

    public int getArmorClass() {
        return armorClass;
    }

    // TODO: where is class bonus?
    public int getClassBonus() {
        return classBonus;
    }

    // TODO : where is misc bonus?
    public int getMisc() {
        return misc;
    }

    private final int dexBonus;
    private final int armorClass;
    private final int naturalArmor;
    private final int shieldBonus;
    private final int deflection;
    private final int misc;
    private final int classBonus;

    public int getArmorBonus() {
        return armorBonus;
    }

    private final int armorBonus;

    public int getNaturalArmor() {
        return naturalArmor;
    }

    public int getShieldBonus() {
        return shieldBonus;
    }

    public int getDeflection() {
        return deflection;
    }

    public int getDodgeBonus() {
        return dodgeBonus;
    }

    private final int dodgeBonus;

    public List<Attack> getAttacks() {
        return attacks;
    }

    private final List<Attack> attacks;

    @JsonCreator
    public Combat(@JsonProperty("bab") final int bab,
                  @JsonProperty("str_bonus") final int str_bonus,
                  @JsonProperty("dex_bonus") final int dex_bonus,
                  @JsonProperty("dodge_modified") final int dodge_modifier,
                  @JsonProperty("armor_class") final int armor_class,
                  @JsonProperty("shield_ac") final int shield_ac,
                  @JsonProperty("misc") final int misc,
                  @JsonProperty("deflection_modifier") final int deflection_modifier,
                  @JsonProperty("natural_armor") final int natural_armor,
                  @JsonProperty("armor_ac") final int armor_ac,
                  @JsonProperty("attacks") final List<Attack> attacks) {
        this.bab = bab;
        this.strBonus = str_bonus;
        this.dexBonus = dex_bonus;
        this.armorClass = armor_class;
        this.naturalArmor = natural_armor;
        this.dodgeBonus = dodge_modifier;
        this.shieldBonus = shield_ac;
        this.deflection = deflection_modifier;
        this.armorBonus = armor_ac;
        this.misc = 0;
        this.classBonus = 0;
        this.attacks = attacks;
    }

}
