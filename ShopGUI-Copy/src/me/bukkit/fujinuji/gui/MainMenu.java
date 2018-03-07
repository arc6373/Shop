package me.bukkit.fujinuji.gui;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MainMenu
{
  public static void open(Player player)
  {
    Inventory inventory = Bukkit.createInventory(null, 27, "Main Menu");
    ArrayList<String> Lore = new ArrayList();
    
    ItemStack shop = new ItemStack(Material.COMPASS, 1);
    ItemMeta shop_meta = shop.getItemMeta();
    shop_meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Shop");
    Lore.clear();
    Lore.add(null);
    Lore.add(ChatColor.GRAY + "View shop like a player");
    shop_meta.setLore(Lore);
    shop.setItemMeta(shop_meta);
    inventory.setItem(11, shop);
    
    ItemStack settings = new ItemStack(356, 1);
    ItemMeta settings_meta = settings.getItemMeta();
    settings_meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Price settings");
    Lore.clear();
    Lore.add(null);
    Lore.add(ChatColor.GRAY + "Set up your shop");
    settings_meta.setLore(Lore);
    settings.setItemMeta(settings_meta);
    inventory.setItem(13, settings);
    
    ItemStack manager = new ItemStack(Material.BOOKSHELF, 1);
    ItemMeta manager_meta = manager.getItemMeta();
    manager_meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Categories Manager");
    Lore.clear();
    Lore.add(null);
    Lore.add(ChatColor.GRAY + "Manage categories and items");
    manager_meta.setLore(Lore);
    manager.setItemMeta(manager_meta);
    inventory.setItem(15, manager);
    
    player.openInventory(inventory);
  }
}
