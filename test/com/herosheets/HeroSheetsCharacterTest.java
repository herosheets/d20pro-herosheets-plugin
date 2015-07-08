package com.herosheets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.herosheets.HeroSheetsCharacter;
import com.mindgene.d20.common.creature.CreatureTemplate;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;


public class HeroSheetsCharacterTest {

    private static ObjectMapper mapper;
    private static File file;

    @BeforeClass
    public static void oneTimeSetUp() {
        // one-time initialization code
        mapper = new ObjectMapper();
        file = new File("character.json");
    }

    @Test
    public void testCharacter() {
        try {
            HeroSheetsCharacter character = mapper.readValue(file, HeroSheetsCharacter.class);
            Assert.assertEquals("uuid equals", character.getuuid(), "1d1030fb-b0d3-4213-b35f-6c98e86e6ec8");
            Assert.assertEquals("character name", character.getCharacter().getCharacterName(), "asdfasdf");
            Assert.assertEquals("hit die", character.getCharacter().getHitDice(), "3");
            Assert.assertEquals("combat dex bonus", character.getCombat().getBab(), "3");

            character.toCreatureTemplate();
        } catch (IOException e) {
            System.out.println(e);
            assert false;
        }
    }

    @Test
    public void testConversion() {
        try {
            HeroSheetsCharacter character = mapper.readValue(file, HeroSheetsCharacter.class);
            CreatureTemplate creature = character.toCreatureTemplate();
            Assert.assertEquals(creature.getMaxDexBonus(), 0);
            Assert.assertEquals(creature.getHitDice(), "3");
            System.out.println(creature.toString());
        } catch (IOException e) {
            System.out.println(e);
            assert false;
        }
    }
}