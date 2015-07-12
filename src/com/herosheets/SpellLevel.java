package com.herosheets;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public final class SpellLevel implements java.io.Serializable {

    public List<Spell> getZeroth() {
        return zeroth;
    }

    private final List<Spell> zeroth;
    private final List<Spell> first;
    private final List<Spell> second;
    private final List<Spell> third;
    private final List<Spell> fourth;
    private final List<Spell> fifth;
    private final List<Spell> sixth;
    private final List<Spell> seventh;
    private final List<Spell> eighth;
    private final List<Spell> ninth;

    public List<Spell> getNinth() {
        return ninth;
    }

    public List<Spell> getFirst() {
        return first;
    }

    public List<Spell> getSecond() {
        return second;
    }

    public List<Spell> getSeventh() {
        return seventh;
    }

    public List<Spell> getEighth() {
        return eighth;
    }

    public List<Spell> getThird() {
        return third;
    }

    public List<Spell> getFourth() {
        return fourth;
    }

    public List<Spell> getFifth() {
        return fifth;
    }

    public List<Spell> getSixth() {
        return sixth;
    }

    @JsonCreator
    public SpellLevel(@JsonProperty("0") final List<Spell> zeroth,
                     @JsonProperty("1") final List<Spell> first,
                     @JsonProperty("2") final List<Spell> second,
                     @JsonProperty("3") final List<Spell> third,
                     @JsonProperty("4") final List<Spell> fourth,
                     @JsonProperty("5") final List<Spell> fifth,
                     @JsonProperty("6") final List<Spell> sixth,
                     @JsonProperty("7") final List<Spell> seventh,
                     @JsonProperty("8") final List<Spell> eighth,
                     @JsonProperty("9") final List<Spell> ninth) {

        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
        this.fifth = fifth;
        this.sixth = sixth;
        this.seventh = seventh;
        this.eighth = eighth;
        this.ninth = ninth;
        this.zeroth = zeroth;
    }

    public Spell[][] getSpellsInOrder() {
        Spell[][] spells = new Spell[10][10];
        spells[0] = safeArray(getZeroth());
        spells[1] = safeArray(getFirst());
        spells[2] = safeArray(getSecond());
        spells[3] = safeArray(getThird());
        spells[4] = safeArray(getFourth());
        spells[5] = safeArray(getFifth());
        spells[6] = safeArray(getSixth());
        spells[7] = safeArray(getSeventh());
        spells[8] = safeArray(getEighth());
        spells[9] = safeArray(getNinth());
        return spells;
    }

    public Spell[] safeArray(List<Spell> l) {
        if (l == null) {
            return new Spell[0];
        } else {
            return l.toArray(new Spell[l.size()]);
        }
    }
}
