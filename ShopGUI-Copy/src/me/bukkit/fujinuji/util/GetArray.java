package me.bukkit.fujinuji.util;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import me.bukkit.fujinuji.store.Items;
import me.bukkit.fujinuji.store.YamlConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

public class GetArray
{
  public static ArrayList<String> getArray(YamlConfiguration ShopItems)
    throws FileNotFoundException
  {
    Set<String> items = ShopItems.getConfigurationSection("Items").getKeys(false);
    
    return (ArrayList)items.stream().collect(Collectors.toList());
  }
  
  public static ArrayList<String> getCategory(YamlConfiguration CategoryItems)
    throws FileNotFoundException
  {
    Set<String> category = null;
    try
    {
      category = CategoryItems.getConfigurationSection("Category").getKeys(false);
    }
    catch (NullPointerException ex)
    {
      Bukkit.getServer().getLogger().warning("[ShopPlus] No category found... Skip this path");
    }
    if (category != null) {
      return (ArrayList)category.stream().collect(Collectors.toList());
    }
    return null;
  }
  
  public static ArrayList<String> getLore(String code)
  {
    ArrayList<String> lore = new ArrayList();
    
    lore.add("");
    lore.add(ChatColor.GRAY + "Category name: " + ChatColor.GOLD + YamlConfig.getCategoryConfiguration().getString(new StringBuilder("Category.").append(code).append(".name").toString()));
    lore.add("");
    lore.add(ChatColor.GRAY + "Head Item: " + ChatColor.GOLD + GetCode.getItemStack(GetCode.getMinecraftCode(YamlConfig.getCategoryConfiguration().getString(new StringBuilder("Category.").append(code).append(".head_item").toString()))).getType().name());
    lore.add("");
    lore.add(ChatColor.GRAY + "Items: " + ChatColor.GOLD + YamlConfig.getCategoryConfiguration().getList(new StringBuilder("Category.").append(code).append(".items").toString()).size());
    lore.add("");
    lore.add(ChatColor.GRAY + "Visibility: " + ChatColor.GOLD + YamlConfig.getCategoryConfiguration().getString(new StringBuilder("Category.").append(code).append(".visibility").toString()));
    
    return lore;
  }
  
  public static ArrayList<String> getShopDetailsAdmin(String code)
  {
    ArrayList<String> lore = new ArrayList();
    
    String sell_price = YamlConfig.getShopConfiguration().getString("Items." + code + ".sell");
    String buy_price = YamlConfig.getShopConfiguration().getString("Items." + code + ".buy");
    int sell_amount = Integer.parseInt(YamlConfig.getShopConfiguration().getString("Items." + code + ".sell_amount"));
    int buy_amount = Integer.parseInt(YamlConfig.getShopConfiguration().getString("Items." + code + ".buy_amount"));
    
    lore.add("");
    lore.add(ChatColor.GRAY + "Sell price: " + (sell_price.length() == 0 ? ChatColor.RED + "Not set" : new StringBuilder().append(ChatColor.GOLD).append(sell_price).append(ChatColor.GRAY).append(" (price for ").append(ChatColor.GOLD).append(sell_amount).append(" item").append(sell_amount == 1 ? "" : "s").append(ChatColor.GRAY).append(")").toString()));
    lore.add("");
    lore.add(ChatColor.GRAY + "Buy price: " + (buy_price.length() == 0 ? ChatColor.RED + "Not set" : new StringBuilder().append(ChatColor.GOLD).append(buy_price).append(ChatColor.GRAY).append(" (price for ").append(ChatColor.GOLD).append(buy_amount).append(" item").append(buy_amount == 1 ? "" : "s").append(ChatColor.GRAY).append(")").toString()));
    lore.add("");
    lore.add(ChatColor.GREEN + "Right click to edit sell price and sell items amount");
    lore.add(ChatColor.GREEN + "Left click to edit buy price and buy items amount");
    
    return lore;
  }
  
  public static ArrayList<String> getCategoryDetalis(String category_name)
  {
    ArrayList<String> lore = new ArrayList();
    List<String> items = (List<String>) YamlConfig.getCategoryConfiguration().getList("Category." + category_name + ".items");
    
    lore.add("");
    lore.add(ChatColor.GRAY + "Itmes to buy/sell: " + (items == null ? ChatColor.RED + "No items" : items.size() == 0 ? ChatColor.RED + "No items" : new StringBuilder().append(ChatColor.GOLD).append(items.size()).toString()));
    lore.add("");
    lore.add(ChatColor.GREEN + "Click to view items for this category");
    return lore;
  }
  
  public static ArrayList<String> getShopDetailsPlayer(String code)
  {
    ArrayList<String> lore = new ArrayList();
    
    String sell_price = YamlConfig.getShopConfiguration().getString("Items." + code + ".sell");
    String buy_price = YamlConfig.getShopConfiguration().getString("Items." + code + ".buy");
    int sell_amount = Integer.parseInt(YamlConfig.getShopConfiguration().getString("Items." + code + ".sell_amount"));
    int buy_amount = Integer.parseInt(YamlConfig.getShopConfiguration().getString("Items." + code + ".buy_amount"));
    
    lore.add("");
    if ((sell_price.length() == 0) || (sell_amount == 0)) {
      lore.add(ChatColor.RED + "No sell details found");
    } else {
      lore.add(ChatColor.GREEN + "Right click " + ChatColor.GRAY + "to sell " + ChatColor.GOLD + sell_amount + " item" + (sell_amount == 1 ? "" : "s") + ChatColor.GRAY + " for " + ChatColor.GOLD + sell_price);
    }
    lore.add("");
    if ((buy_price.length() == 0) || (buy_amount == 0)) {
      lore.add(ChatColor.RED + "No buy details found");
    } else {
      lore.add(ChatColor.GREEN + "Left click " + ChatColor.GRAY + "to buy " + ChatColor.GOLD + buy_amount + " item" + (buy_amount == 1 ? "" : "s") + ChatColor.GRAY + " for " + ChatColor.GOLD + buy_price);
    }
    return lore;
  }
  
  public static ArrayList<String> getAvailableCategory()
  {
    ArrayList<String> all_category = Items.getCategoryItems();
    ArrayList<String> done_category = new ArrayList();
    for (int index = 0; index < all_category.size(); index++) {
      if (YamlConfig.getCategoryConfiguration().getString("Category." + (String)all_category.get(index) + ".visibility").contains("True")) {
        done_category.add((String)all_category.get(index));
      }
    }
    return done_category;
  }
}
