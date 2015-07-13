package com.herosheets;

import com.d20pro.plugin.api.CreatureImportServices;
import com.d20pro.plugin.api.ImportCreatureException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

import com.mindgene.d20.common.D20Rules;
import com.mindgene.d20.common.creature.CreatureSpeeds;
import com.mindgene.d20.common.creature.CreatureTemplate;
import com.mindgene.d20.common.creature.attack.*;
import com.mindgene.d20.common.creature.capability.CreatureCapability_SpellCaster;
import com.mindgene.d20.common.game.creatureclass.CreatureClassBinder;
import com.mindgene.d20.common.game.creatureclass.CreatureClassNotInstalledException;
import com.mindgene.d20.common.game.creatureclass.GenericCreatureClass;
import com.mindgene.d20.common.game.feat.Feat_InitModifier;
import com.mindgene.d20.common.game.feat.GenericFeat;
import com.mindgene.d20.common.game.skill.GenericSkill;
import com.mindgene.d20.common.game.skill.GenericSkillTemplate;
import com.mindgene.d20.common.game.skill.MalformedSkillException;
import com.mindgene.d20.common.game.skill.SkillBinder;
import com.mindgene.d20.common.game.spell.SpellBinder;
import com.mindgene.d20.common.importer.ImportedSpell;
import com.mindgene.d20.common.dice.Dice;

import java.util.ArrayList;
import java.util.List;

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

    public Spells getSpells() {
        return spells;
    }

    public Spellbook getSpellbook() {
        return spellbooks;
    }
    private final Combat combat;
    private final Misc misc;
    private final int id;
    private final String uuid;
    private final Attributes attributes;
    private final Spells spells;
    private final Spellbook spellbooks;
    private final SavingThrows saves;

    @JsonCreator
    public HeroSheetsCharacter(
            @JsonProperty("id") final int id,
            @JsonProperty("uuid") final String uuid,
            @JsonProperty("character") final MiniCharacter character,
            @JsonProperty("combat") final Combat combat,
            @JsonProperty("misc") final Misc misc,
            @JsonProperty("attributes") final Attributes attributes,
            @JsonProperty("saves") final SavingThrows saves,
            @JsonProperty("spells") final Spells spells,
            @JsonProperty("spellbooks") final Spellbook spellbooks) {
        this.id = id;
        this.uuid = uuid;
        this.character = character;
        this.combat = combat;
        this.misc = misc;
        this.attributes = attributes;
        this.saves = saves;
        this.spells = spells;
        this.spellbooks = spellbooks;
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
        parseSkills(ct, svc);
        parseSpells(ct, svc);
        parseAttacks(ct);
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
        // TODO : class & misc are missing
        _ac[4] += (byte) getCombat().getClassBonus();
        _ac[4] += (byte) getCombat().getMisc();
        _ac[5] += (byte) getCombat().getDodgeBonus();

        ct.setAC(_ac);
    }

    public void parseFeats(CreatureTemplate ct) {

        ArrayList<GenericFeat> feats = new ArrayList<>();
        List<Feat> herosheetsFeats = getMisc().getFeats();
        for (Feat feat: herosheetsFeats) {
            String nameOfFeat = feat.getName();
            if (Feat_InitModifier.IMPROVED_INIT.equalsIgnoreCase(nameOfFeat)) {
                feats.add(Feat_InitModifier.buildStandard());
            } else {
                feats.add(new GenericFeat(nameOfFeat));
            }
        }

        ct.getFeats().setFeats(feats.toArray(new GenericFeat[0]));
    }

    public void parseSkills(CreatureTemplate ct, CreatureImportServices svc) {

        SkillBinder binder = svc.accessSkills();
        ArrayList<GenericSkill> skillList = new ArrayList<GenericSkill>();
        List<Skill> skills = getMisc().getSkills();

        for(Skill skill: skills) {
            String skillName = skill.getName();
            Short skillRanks = (short) skill.getRanks();
            Short skillMisc = (short) skill.getBonus();
            GenericSkillTemplate skillTemplate = binder.accessSkill(skillName);

            if (null != skillTemplate) {
                skillList.add(new GenericSkill(skillTemplate, skillRanks, skillMisc));
            } else {
                try {
                    skillTemplate = new GenericSkillTemplate(skillName);
                    String abilityName = skill.getAttribute();
                    for (byte i = 0; i < D20Rules.Ability.NAMES.length; i++) {
                        if (abilityName.equals(D20Rules.Ability.NAMES[i])) {
                            skillTemplate.setAbility(i);
                        }
                    }
                    skillList.add(new GenericSkill(skillTemplate, skillRanks, skillMisc));
                    ct.addToNotes("skill not found: " + skillName);
                } catch (MalformedSkillException mse) {
                    ct.addToNotes("skill not found: " + skillName);
                }
            }

        }
        ct.getSkills().setSkills(skillList.toArray(new GenericSkill[skillList.size()]));
    }

    public void parseSpells(CreatureTemplate ct, CreatureImportServices svc)  {

        String[] classes = {"bard", "cleric", "druid", "paladin", "ranger", "wizard"};

        for (String spellListClass : classes) {
            CreatureCapability_SpellCaster casting = ct.extractSpellCasting(spellListClass);

            if (getSpellbook() != null) {
                Spell[][] spells = getSpellbook().getSpellsForClassName(spellListClass);
                ArrayList<ImportedSpell> spellList = getSpellsForClass(svc, ct, spells, spellListClass, null);

                if (casting != null && spellList.size() > 0) {
                    casting.importSpellsKnown(spellList.toArray(new ImportedSpell[spellList.size()]));
                    casting.importSpellsMemorized(spellList.toArray(new ImportedSpell[spellList.size()]));
                }
            }
        }
    }

    public void parseAttacks(CreatureTemplate ct) {

        List<Attack> attacks = getCombat().getAttacks();
        for (Attack attack : attacks) {
            CreatureAttack a = new CreatureAttack();
            a.setName(attack.getWeaponName());
            a.setToHit((short) attack.getAttackBonus()[0]);

            ArrayList<CreatureAttackDamage> damages = getDamagesForAttack(attack);

            a.setDamages(damages);
            a.setCritMinThreat(attack.calculateMinCritRange());
            a.setCritMultiplier(attack.calculateCritMultiplier());
            ct.getAttacks().add(a);
        }
    }

    public ArrayList<CreatureAttackDamage> getDamagesForAttack(Attack attack) {

        ArrayList<CreatureAttackDamage> damages = new ArrayList<CreatureAttackDamage>();
        String damageType = attack.getDamageType();
        CreatureAttackDamage attackDamage = new CreatureAttackDamage();

        if(damageType.contains("S"))
            attackDamage.addQuality( new CreatureAttackQuality_Slash() );
        if(damageType.contains("B"))
            attackDamage.addQuality( new CreatureAttackQuality_Bash() );
        if(damageType.contains("P"))
            attackDamage.addQuality( new CreatureAttackQuality_Pierce() );

        try {
            String diceString = attack.cleanDiceString();
            attackDamage.setDice(new Dice(diceString));
        } catch (Exception e) {
            try {
                attackDamage.setDice(new Dice("1d0"));
            } catch (Exception ee) {
            } finally {
            }
        }

        damages.add(attackDamage);
        return damages;
    }


    public static ArrayList<ImportedSpell> getSpellsForClass(CreatureImportServices svc, CreatureTemplate ct, Spell[][] spells,
                                                             String spellClassName, String domainName) {

        SpellBinder binder = svc.accessSpells();
        ArrayList<ImportedSpell> spellList = new ArrayList<>();

        for( int i = 0; i < 10; i++) {
            for (Spell spell: spells[i]) {
                spellList.add(new ImportedSpell(spell.getName(), i, 1));
            }
        }

        return spellList;
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