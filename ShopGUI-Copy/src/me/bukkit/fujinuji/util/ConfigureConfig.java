package me.bukkit.fujinuji.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.bukkit.fujinuji.ShopPlus;
import me.bukkit.fujinuji.store.YamlConfig;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConfigurationOptions;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigureConfig
  extends JavaPlugin
{
  private static int last = 0;
  private static int pages = 21;
  public static ShopPlus shop;
  
  public static void setUp(ArrayList<String> items, Logger log)
  {
    for (int i = 0; i < items.size(); i++)
    {
      Inventory inv = Bukkit.createInventory(null, 9);
      inv.setItem(1, GetCode.getItemStack(GetCode.getMinecraftCode((String)items.get(i))));
      if (inv.getItem(1) == null) {
        YamlConfig.getShopConfiguration().set("Items." + (String)items.get(i), null);
      }
    }
    YamlConfig.getShopConfiguration().options().copyDefaults(true);
    try
    {
      YamlConfig.getShopConfiguration().save(ShopPlus.ShopItemsFile);
    }
    catch (IOException e1)
    {
      log.log(Level.WARNING, "Can't save file. Please remove 'ShopItems.yml' file from plugin folder");
    }
  }
}
