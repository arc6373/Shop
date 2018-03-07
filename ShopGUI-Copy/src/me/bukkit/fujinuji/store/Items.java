package me.bukkit.fujinuji.store;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.Logger;
import me.bukkit.fujinuji.util.GetArray;
import org.bukkit.Bukkit;
import org.bukkit.Server;

public class Items
{
  private static ArrayList<String> shopItems = new ArrayList();
  private static ArrayList<String> categoryItems = new ArrayList();
  
  public static void loadItems()
  {
    try
    {
      shopItems = GetArray.getArray(YamlConfig.getShopConfiguration());
    }
    catch (FileNotFoundException e)
    {
      Bukkit.getServer().getLogger().info("An error ocured while getting items configuration");
    }
    try
    {
      categoryItems = GetArray.getCategory(YamlConfig.getCategoryConfiguration());
    }
    catch (FileNotFoundException e)
    {
      Bukkit.getLogger().info("An error occurred while getting category configuration");
    }
  }
  
  public static ArrayList<String> getShopItems()
  {
    return shopItems;
  }
  
  public static ArrayList<String> getCategoryItems()
  {
    return categoryItems;
  }
}
