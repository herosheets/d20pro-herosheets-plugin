package com.herosheets;

import com.d20pro.plugin.api.CreatureImportServices;
import com.d20pro.plugin.api.ImportCreatureException;
import com.d20pro.plugin.api.ImportCreaturePlugin;
import com.d20pro.plugin.api.ImportMessageLog;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindgene.common.util.FileFilterForExtension;
import com.mindgene.d20.common.creature.CreatureTemplate;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class CreatureImportPlugin_HeroSheets implements ImportCreaturePlugin {

	public FileFilterForExtension getPluginFileFilter() {
		return new FileFilterForExtension( "json", "HeroSheets output" );
	 }

	public String getPluginName() {
		return "HeroSheets";
	}

	public java.util.List<CreatureTemplate> parseCreatures(
			CreatureImportServices svc,
			ImportMessageLog log,
			java.util.List<File> files) throws ImportCreatureException{

		ArrayList<CreatureTemplate> creatures = new ArrayList<CreatureTemplate>();

		for (File file: files) {
			// Do something with values[i], such as print it
			creatures.add(parseCreature(svc, log, file));
		}

		return creatures;
	}

	public CreatureTemplate parseCreature(
			CreatureImportServices svc,
			ImportMessageLog log,
			File file) throws ImportCreatureException {

		ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
		try {
			HeroSheetsCharacter character = mapper.readValue(file, HeroSheetsCharacter.class);
			CreatureTemplate creature = character.toCreatureTemplate();
			return creature;
		} catch (IOException e) {
			return null;
		}

	}
	
	public java.util.List<CreatureTemplate> importCreatures( CreatureImportServices svc, ImportMessageLog log ) throws ImportCreatureException
	{
		java.util.List<File> files = svc.chooseFiles( this );
	    java.util.List<CreatureTemplate> creatures = parseCreatures(svc, log, files);
	    return creatures;
	}	

	public File chooseFile() {
		JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("herosheets json files", "JSON");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(chooser);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	       System.out.println("You chose to open this file: " +
	            chooser.getSelectedFile().getName());
	    }
	    File file = chooser.getSelectedFile();
		return file;
	}

}