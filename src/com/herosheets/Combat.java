package com.herosheets;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public final class Combat implements java.io.Serializable {

    private final String bab;
    private final String strBonus;

    public String getBab() {
        return bab;
    }

    public String getStrBonus() {
        return strBonus;
    }

    public String getDexBonus() {
        return dexBonus;
    }

    private final String dexBonus;

    @JsonCreator
    public Combat(@JsonProperty("bab") final String bab,
                         @JsonProperty("str_bonus") final String str_bonus,
                         @JsonProperty("dex_bonus") final String dex_bonus) {
        this.bab = bab;
        this.strBonus = str_bonus;
        this.dexBonus = dex_bonus;
    }

}
