package com.herosheets;

import com.d20pro.plugin.api.CreatureImportServices;
import com.d20pro.plugin.api.ImageImportService;
import com.d20pro.plugin.api.ImportCreaturePlugin;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.herosheets.HeroSheetsCharacter;
import com.mindgene.d20.common.creature.CreatureTemplate;
import com.mindgene.d20.common.game.creatureclass.CreatureClassBinder;
import com.mindgene.d20.common.game.skill.SkillBinder;
import com.mindgene.d20.common.game.spell.SpellBinder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.List;


public class HeroSheetsCharacterTest {

    private static ObjectMapper mapper;
    private static File file;
    private static CreatureImportServices svc;

    @BeforeClass
    public static void oneTimeSetUp() {
        // one-time initialization code
        mapper = new ObjectMapper();
        file = new File("character.json");
        svc = new CreatureImportServices() {
            @Override
            public List<File> chooseFiles(ImportCreaturePlugin importCreaturePlugin) {
                return null;
            }

            @Override
            public JComponent accessAnchor() {
                return null;
            }

            @Override
            public CreatureClassBinder accessClasses() {
                return new CreatureClassBinder(accessSpells());

            }

            @Override
            public SkillBinder accessSkills() {
                return null;
            }

            @Override
            public SpellBinder accessSpells() {
                return null;
            }

            @Override
            public ImageImportService accessImageService() {
                return null;
            }
        };
    }

    @Test
    public void testCharacter() {
        try {
            HeroSheetsCharacter character = mapper.readValue(file, HeroSheetsCharacter.class);
            Assert.assertEquals("uuid equals", character.getuuid(), "1d1030fb-b0d3-4213-b35f-6c98e86e6ec8");
            Assert.assertEquals("character name", character.getCharacter().getCharacterName(), "asdfasdf");
            Assert.assertEquals("hit die", character.getCharacter().getHitDice(), "3");
            Assert.assertEquals("combat dex bonus", character.getCombat().getBab(), 3);
            Assert.assertEquals("levels", character.getMisc().getLevels().size(), 1);
            character.toCreatureTemplate(svc);
        } catch (IOException e) {
            System.out.println(e);
            assert false;
        }
    }

    @Test
    public void testConversion() {
        try {
            HeroSheetsCharacter character = mapper.readValue(file, HeroSheetsCharacter.class);
            CreatureTemplate creature = character.toCreatureTemplate(svc);
            Assert.assertEquals(creature.getMaxDexBonus(), 0);
            Assert.assertEquals(creature.getHitDice(), "3");
            System.out.println(creature.toString());
        } catch (IOException e) {
            System.out.println(e);
            assert false;
        }
    }
}