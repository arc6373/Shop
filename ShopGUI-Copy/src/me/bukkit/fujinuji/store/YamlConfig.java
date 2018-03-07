package me.bukkit.fujinuji.store;

import me.bukkit.fujinuji.ShopPlus;
import org.bukkit.configuration.file.YamlConfiguration;

public class YamlConfig
  extends ShopPlus
{
  public static YamlConfiguration getShopConfiguration()
  {
    return ShopItemsYml;
  }
  
  public static YamlConfiguration getCategoryConfiguration()
  {
    return CategoryItemsYml;
  }
}
