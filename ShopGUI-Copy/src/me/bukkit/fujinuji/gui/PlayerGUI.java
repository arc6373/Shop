package me.bukkit.fujinuji.gui;

import java.util.ArrayList;
import java.util.Map;
import me.bukkit.fujinuji.store.Variables;
import me.bukkit.fujinuji.store.YamlConfig;
import me.bukkit.fujinuji.util.GetArray;
import me.bukkit.fujinuji.util.GetCode;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerGUI
  extends JavaPlugin
{
  private static ItemStack back_item = new ItemStack(Material.ARROW, 1);
  private static ItemStack next_item = new ItemStack(Material.ARROW, 1);
  private static ItemStack exit_item = new ItemStack(Material.STAINED_GLASS, 1, (short)13);
  
  public static void categorySelect(Player player)
  {
    setNames();
    
    Inventory inventory = Bukkit.createInventory(null, 54, "Categories");
    
    ArrayList<String> items = GetArray.getAvailableCategory();
    
    int index = ((Integer)Variables.player_last_index.get(player.getName())).intValue();
    
    int length = items.size();
    if (items.size() > 0)
    {
      for (int i = 2; (i < 5) && (index < length); i++) {
        for (int j = 9; (j > 2) && (index < length); j--) {
          if ((i * 9 - j + 1 != 34) && (i * 9 - j + 1 != 28))
          {
            ItemStack item_now = new ItemStack(GetCode.getItemStack(GetCode.getMinecraftCode(YamlConfig.getCategoryConfiguration().getString("Category." + (String)items.get(index) + ".head_item"))));
            ItemMeta meta = item_now.getItemMeta();
            meta.setLore(GetArray.getCategoryDetalis((String)items.get(index)));
            meta.setDisplayName(ChatColor.YELLOW + (String)items.get(index));
            item_now.setItemMeta(meta);
            inventory.setItem(i * 9 - j + 1, item_now);
            index++;
          }
        }
      }
      if (((Integer)Variables.player_page_index.get(player.getName())).intValue() < items.size() / 19 + 1)
      {
        ItemMeta item_meta = next_item.getItemMeta();
        ArrayList<String> lore = new ArrayList();
        lore.add("");
        lore.add(ChatColor.GRAY + "Page " + (((Integer)Variables.player_page_index.get(player.getName())).intValue() + 1));
        item_meta.setLore(lore);
        next_item.setItemMeta(item_meta);
        inventory.setItem(34, next_item);
      }
      if (((Integer)Variables.player_page_index.get(player.getName())).intValue() > 1)
      {
        ItemMeta item_meta = back_item.getItemMeta();
        ArrayList<String> lore = new ArrayList();
        lore.add("");
        lore.add(ChatColor.GRAY + "Page " + (((Integer)Variables.player_page_index.get(player.getName())).intValue() - 1));
        item_meta.setLore(lore);
        back_item.setItemMeta(item_meta);
        inventory.setItem(28, back_item);
      }
      inventory.setItem(49, exit_item);
      
      player.openInventory(inventory);
    }
    else
    {
      noCategoryFound(player);
    }
  }
  
  public static void noCategoryFound(Player player)
  {
    ArrayList<String> lore = new ArrayList();
    Inventory inventory = Bukkit.createInventory(null, 54, "Categories");
    
    lore.add("");
    lore.add(ChatColor.GRAY + "If the problem persists, please contact an admin");
    
    ItemStack no_found = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)14);
    ItemStack back = new ItemStack(Material.STAINED_GLASS, 1, (short)13);
    
    ItemMeta found_meta = no_found.getItemMeta();
    ItemMeta back_meta = back.getItemMeta();
    
    found_meta.setDisplayName(ChatColor.RED + "No category found");
    found_meta.setLore(lore);
    
    back_meta.setDisplayName(ChatColor.GREEN + "Exit");
    
    no_found.setItemMeta(found_meta);
    back.setItemMeta(back_meta);
    
    inventory.setItem(22, no_found);
    inventory.setItem(49, back);
    
    player.openInventory(inventory);
  }
  
  private static void setNames()
  {
    ItemMeta back_meta = back_item.getItemMeta();
    ItemMeta next_meta = next_item.getItemMeta();
    ItemMeta exit_meta = exit_item.getItemMeta();
    
    back_meta.setDisplayName(ChatColor.GREEN + "Back page");
    next_meta.setDisplayName(ChatColor.GREEN + "Next page");
    exit_meta.setDisplayName(ChatColor.GREEN + "Exit");
    
    back_item.setItemMeta(back_meta);
    next_item.setItemMeta(next_meta);
    exit_item.setItemMeta(exit_meta);
  }
}
