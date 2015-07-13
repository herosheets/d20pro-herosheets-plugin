package com.herosheets;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public final class Attack implements java.io.Serializable {

    private final String weaponName;
    private final String range;
    private final String weightClass;
    private final String damageString;
    private final String criticalRange;
    private final int weaponBonus;

    public String getWeaponName() {
        return weaponName;
    }

    public String getRange() {
        return range;
    }

    public String getWeightClass() {
        return weightClass;
    }

    public String getDamageString() {
        return damageString;
    }

    public String getCriticalRange() {
        return criticalRange;
    }

    public int getWeaponBonus() {
        return weaponBonus;
    }

    public int getStrengthBonus() {
        return strengthBonus;
    }

    public int[] getAttackBonus() {
        return attackBonus;
    }

    public String getCriticalDamageString() {
        return criticalDamageString;
    }

    public String getWeaponType() {
        return weaponType;
    }

    public int getOtherBonus() {
        return otherBonus;
    }

    private final int strengthBonus;
    private final int[] attackBonus;
    private final String criticalDamageString;
    private final String weaponType;

    public String getDamageType() {
        return damageType;
    }

    private final String damageType;
    private final int otherBonus;

    public Attack(@JsonProperty("weapon_name") final String weapon_name,
                  @JsonProperty("range") final String range,
                  @JsonProperty("weight_class") final String weight_class,
                  @JsonProperty("damage") final String damage,
                  @JsonProperty("weapon_type") final String weapon_type,
                  @JsonProperty("damage_type") final String damage_type,
                  @JsonProperty("critical_range") final String critical_range,
                  @JsonProperty("critical_dmg") final String critical_dmg,
                  @JsonProperty("weapon_bonus") final int weapon_bonus,
                  @JsonProperty("strength_bonus") final int strength_bonus,
                  @JsonProperty("other_bonus") final int other_bonus,
                  @JsonProperty("attack_bonus") final int[] attack_bonus) {
        this.weaponName = weapon_name;
        this.range = range;
        this.damageString = damage;
        this.weaponType = weapon_type;
        this.damageType = damage_type;
        this.weightClass = weight_class;
        this.criticalRange = critical_range;
        this.weaponBonus = weapon_bonus;
        this.strengthBonus = strength_bonus;
        this.attackBonus = attack_bonus;
        this.criticalDamageString = critical_dmg;
        this.otherBonus = other_bonus;
    }

    public byte calculateCritMultiplier() {
        try {
            String[] tokens = getCriticalDamageString().split("x");
            return Byte.parseByte(tokens[1]);
        } catch (Exception e) {
            return 2;
        }
    }

    public byte calculateMinCritRange() {
        try {
            String[] tokens = getCriticalRange().split("-");
            return Byte.parseByte(tokens[0]);
        } catch (Exception e) {
            return 20;
        }
    }

    public String cleanDiceString() {
        try {
            return getDamageString().replaceAll("\\[", "").replaceAll("\\]","");
        } catch (Exception e) {
            return "1d0";
        }
    }
}
