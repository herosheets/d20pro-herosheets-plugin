package com.herosheets;

import com.d20pro.plugin.api.ImportCreaturePlugin;
import com.mindgene.common.plugin.Factory;
import java.util.*;

public class CommandFactoryImpl implements Factory<ImportCreaturePlugin>
{
  public List<ImportCreaturePlugin> getPlugins()
  {
    ArrayList<ImportCreaturePlugin> plugins = new ArrayList<ImportCreaturePlugin>();
    plugins.add( new CreatureImportPlugin_HeroSheets() );
    return plugins;
  }
}
