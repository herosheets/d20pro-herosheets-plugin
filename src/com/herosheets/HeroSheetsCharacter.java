package com.herosheets;

import com.d20pro.plugin.api.CreatureImportServices;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

import com.mindgene.d20.common.D20Rules;
import com.mindgene.d20.common.creature.CreatureSpeeds;
import com.mindgene.d20.common.creature.CreatureTemplate;
import com.mindgene.d20.common.game.creatureclass.CreatureClassBinder;
import com.mindgene.d20.common.game.creatureclass.CreatureClassNotInstalledException;
import com.mindgene.d20.common.game.creatureclass.GenericCreatureClass;

import java.util.ArrayList;

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

    public CreatureTemplate toCreatureTemplate(CreatureImportServices svc) {
        CreatureTemplate ct = new CreatureTemplate();
        parseBasics(ct);
        parseAttributes(ct);
        parseSaves(ct);
        parseClasses(ct, svc);
        parseArmor(ct);
        parseFeats(ct);
        parseSkills(ct);
        return ct;
    }

    public void parseBasics(CreatureTemplate ct) {
        ct.setGameSystem("PFRPG");
        ct.setName(character.getCharacterName());
        ct.setAlignment(character.getAlignment());
        ct.setHP((short) character.getHitPoints());
        ct.setHPMax((short) character.getHitPoints());
        ct.setHitDice(character.getHitDice());
        ct.setMaxDexBonus((short) getCombat().getDexBonus());
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

    public void parseClasses(CreatureTemplate ct, CreatureImportServices svc) {
        CreatureClassBinder binder = svc.accessClasses();
        ArrayList<GenericCreatureClass> classList = new ArrayList<GenericCreatureClass>();
        for (Level l: misc.getLevels()) {
            byte level = (byte) l.getRank();
            String nameOfClass = l.getClassname();
            try {
                GenericCreatureClass aClass = new GenericCreatureClass(binder.accessClass(nameOfClass));
                aClass.setCreature(ct);
                aClass.setLevel(level);
                classList.add(aClass);
            } catch (CreatureClassNotInstalledException cclnie) {
                ct.addToErrorLog("Unable to import: " + nameOfClass + " " + level + " :" + cclnie.getMessage());
                defaultToFighter1(ct, classList, binder, level);
            }
        }

        ct.getClasses().assignClasses(classList);
    }

    public void parseArmor(CreatureTemplate ct) {
        byte[] _ac = new byte[6];
        for (int i = 0; i < 6; i++) {
            _ac[i] = 0;
        }

        _ac[0] += getCombat().getNaturalArmor();
        _ac[1] = (byte) getCombat().getArmorBonus();
        _ac[2] = (byte) getCombat().getShieldBonus();
        _ac[3] += (byte) getCombat().getDeflection();
        _ac[4] += (byte) getCombat().getClassBonus();
        _ac[4] += (byte) getCombat().getMisc();
        _ac[5] += (byte) getCombat().getDodgeBonus();

        ct.setAC(_ac);
    }

    private static void defaultToFighter1(CreatureTemplate ctr, ArrayList<GenericCreatureClass> classes,
                                          CreatureClassBinder binder, int level) {
        try {
            ctr.addToErrorLog("Defaulting to Fighter");
            GenericCreatureClass fighter = new GenericCreatureClass(binder.accessClass("Fighter"));
            fighter.setLevel((byte) level);
            fighter.setCreature(ctr);
            classes.add(fighter);
        } catch (CreatureClassNotInstalledException cclnie) {
            ctr.addToErrorLog("Fighter class not found, skipping class");
        }
    }

    public int getAttribute(String attributeName) {
        return getAttributes().getValues().getByAbbreviation(attributeName);
    }

    public int getSave(String saveName) {
        return getSaves().getByAbbreviation(saveName);
    }

}