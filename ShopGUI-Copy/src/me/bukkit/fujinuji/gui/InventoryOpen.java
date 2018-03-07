package me.bukkit.fujinuji.gui;

import java.util.ArrayList;
import java.util.Map;
import me.bukkit.fujinuji.store.Variables;
import me.bukkit.fujinuji.util.GetArray;
import me.bukkit.fujinuji.util.GetCode;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class InventoryOpen
  extends JavaPlugin
{
  public static void UpdateInventoryChoose(Player player, int page, String inventory_name, ArrayList<String> names)
  {
    StringBuilder sb = new StringBuilder();
    sb.append("");
    sb.append(page);
    Variables.page = page;
    
    Inventory inventory = null;
    
    ItemStack next_item = new ItemStack(Material.ARROW, 1);
    ItemStack previous_item = new ItemStack(Material.ARROW, 1);
    ItemStack back_item = new ItemStack(Material.STAINED_GLASS, 1, (short)13);
    
    ArrayList<String> Lore = new ArrayList();
    if (inventory_name.contains("Page")) {
      inventory = Bukkit.createInventory(null, 54, "Page " + sb.toString());
    } else {
      inventory = Bukkit.createInventory(null, 54, inventory_name);
    }
    ItemMeta next_meta = next_item.getItemMeta();
    next_meta.setDisplayName(ChatColor.GREEN + "Next");
    if (page < names.size() / 21 + 1)
    {
      next_item.setItemMeta(next_meta);
      inventory.setItem(50, next_item);
    }
    if (inventory_name == "Select items for category")
    {
      ItemStack done = new ItemStack(Material.STAINED_GLASS, 1, (short)13);
      ItemMeta done_meta = done.getItemMeta();
      done_meta.setDisplayName(ChatColor.GREEN + "Save");
      Lore.clear();
      Lore.add("");
      Lore.add(ChatColor.GRAY + "You've selected " + ChatColor.GOLD + Variables.items.size() + " item" + (Variables.items.size() == 1 ? "" : "s"));
      done_meta.setLore(Lore);
      done.setItemMeta(done_meta);
      inventory.setItem(49, done);
    }
    if ((inventory_name == "Add items") || (inventory_name == "Remove items"))
    {
      ItemStack done = new ItemStack(Material.STAINED_GLASS, 1, (short)13);
      ItemMeta done_meta = done.getItemMeta();
      done_meta.setDisplayName(ChatColor.GREEN + "Save");
      Lore.clear();
      Lore.add("");
      Lore.add(ChatColor.GRAY + "You've selected " + ChatColor.GOLD + Variables.final_items.size() + " item" + (Variables.final_items.size() == 1 ? "" : "s"));
      done_meta.setLore(Lore);
      done.setItemMeta(done_meta);
      inventory.setItem(49, done);
    }
    ItemMeta previous_meta = previous_item.getItemMeta();
    previous_meta.setDisplayName(ChatColor.GREEN + "Previous");
    sb.append("");
    if (page > 1)
    {
      previous_item.setItemMeta(previous_meta);
      inventory.setItem(48, previous_item);
    }
    ItemMeta back_meta = back_item.getItemMeta();
    back_meta.setDisplayName(ChatColor.GREEN + "Done");
    back_item.setItemMeta(back_meta);
    
    inventory.setItem(49, back_item);
    int current;
    if (inventory_name == "Items") {
      current = ((Integer)Variables.player_last_list.get(player.getName())).intValue();
    } else {
      current = Variables.last;
    }
    for (int i = 2; (i < 5) && (current < names.size()); i++) {
      for (int j = 8; (j > 1) && (current < names.size()); j--) {
        if (inventory_name == "Category items")
        {
          ItemStack item = GetCode.getItemStack(GetCode.getMinecraftCode((String)names.get(current)));
          ItemMeta item_meta = item.getItemMeta();
          
          item_meta.setLore(GetArray.getShopDetailsAdmin((String)names.get(current)));
          item.setItemMeta(item_meta);
          
          inventory.setItem(i * 9 - j, item);
          current++;
        }
        else if (inventory_name == "Items")
        {
          ItemStack item = GetCode.getItemStack(GetCode.getMinecraftCode((String)names.get(current)));
          ItemMeta item_meta = item.getItemMeta();
          
          item_meta.setLore(GetArray.getShopDetailsPlayer((String)names.get(current)));
          item.setItemMeta(item_meta);
          
          inventory.setItem(i * 9 - j, item);
          current++;
        }
        else
        {
          inventory.setItem(i * 9 - j, GetCode.getItemStack(GetCode.getMinecraftCode((String)names.get(current))));
          current++;
        }
      }
    }
    player.openInventory(inventory);
  }
}
