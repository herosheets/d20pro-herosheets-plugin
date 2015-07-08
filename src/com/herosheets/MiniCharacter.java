package com.herosheets;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public final class MiniCharacter implements java.io.Serializable {

    private final String alignment;

    public int getHitPoints() {
        return hitPoints;
    }

    private final int hitPoints;

    public String getRace() {
        return race;
    }

    public String getAlignment() {

        return alignment;
    }

    private final String race;

    public String getDeity() {
        return deity;
    }

    public String getCharacterName() {

        return characterName;
    }

    private final String characterName;
    private final String deity;

    // I'm sorry that size contains the level string
    public String getSize() {
        return size;
    }
    private final String size;

    public String getPlayerName() {
        return playerName;
    }

    private final String playerName;

    public String getHitDice() {
        String[] tokens = getSize().split("/");
        String[] levelTokens = tokens[1].split("/*");
        return levelTokens[0];
    }

    /* Constructor. With the @JsonCreator annotation, Jackson will use this
         * method to generate new JacksonImmutableExample objects. */
    @JsonCreator
    public MiniCharacter(@JsonProperty("alignment") final String alignment,
                         @JsonProperty("race_str") final String race,
                         @JsonProperty("name") final String characterName,
                         @JsonProperty("deity") final String deity,
                         @JsonProperty("hit_points") final String hitPoints,
                         @JsonProperty("size") final String size,
                         @JsonProperty("player_name") final String playerName) {
        this.alignment = alignment;
        this.race = race;
        this.characterName = characterName;
        this.deity = deity;
        this.hitPoints = Integer.parseInt(hitPoints);
        this.size = size;
        this.playerName = playerName;
    }


}