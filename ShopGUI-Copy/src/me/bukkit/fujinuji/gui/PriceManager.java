package me.bukkit.fujinuji.gui;

import java.util.ArrayList;
import java.util.List;
import me.bukkit.fujinuji.store.Buttons;
import me.bukkit.fujinuji.store.Items;
import me.bukkit.fujinuji.store.Variables;
import me.bukkit.fujinuji.store.YamlConfig;
import me.bukkit.fujinuji.util.GetCode;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class PriceManager
  extends JavaPlugin
{
  private static ItemStack next_item = new ItemStack(Material.ARROW, 1);
  private static ItemStack previous_item = new ItemStack(Material.ARROW, 1);
  private static ItemStack back_item = new ItemStack(Material.ARROW, 1);
  private static ItemMeta next_item_meta = next_item.getItemMeta();
  private static ItemMeta previous_item_meta = previous_item.getItemMeta();
  private static ItemMeta back_meta = back_item.getItemMeta();
  
  public static void priceMangerOpen(Player player)
  {
    Inventory inventory = Bukkit.createInventory(null, 54, "Categories list");
    
    int index = Variables.last_category_index;
    int length = Items.getCategoryItems().size();
    for (int i = 2; (i < 5) && (index < length); i++) {
      for (int j = 9; (j > 2) && (index < length); j--) {
        if ((i * 9 - j + 1 != 34) && (i * 9 - j + 1 != 28))
        {
          ItemStack item_now = new ItemStack(GetCode.getItemStack(GetCode.getMinecraftCode(YamlConfig.getCategoryConfiguration().getString("Category." + (String)Items.getCategoryItems().get(index) + ".head_item"))));
          ItemMeta item_meta = item_now.getItemMeta();
          ArrayList<String> lore = new ArrayList();
          
          item_meta.setDisplayName(ChatColor.YELLOW + (String)Items.getCategoryItems().get(index));
          lore.add("");
          lore.add(ChatColor.GRAY + "Category name: " + ChatColor.GOLD + YamlConfig.getCategoryConfiguration().getString(new StringBuilder("Category.").append((String)Items.getCategoryItems().get(index)).append(".name").toString()));
          lore.add("");
          lore.add(ChatColor.GRAY + "Items: " + ChatColor.GOLD + YamlConfig.getCategoryConfiguration().getList(new StringBuilder("Category.").append((String)Items.getCategoryItems().get(index)).append(".items").toString()).size());
          lore.add("");
          lore.add(ChatColor.GREEN + "Click to edit items prices");
          
          item_meta.setLore(lore);
          item_now.setItemMeta(item_meta);
          
          inventory.setItem(i * 9 - j + 1, item_now);
          index++;
        }
      }
    }
    next_item_meta.setDisplayName(ChatColor.GREEN + "Next page");
    previous_item_meta.setDisplayName(ChatColor.GREEN + "Prevoius page");
    back_meta.setDisplayName(ChatColor.GREEN + "Back to main menu");
    
    next_item.setItemMeta(next_item_meta);
    previous_item.setItemMeta(previous_item_meta);
    back_item.setItemMeta(back_meta);
    if (Variables.category_page < Items.getCategoryItems().size() / 19 + 1) {
      inventory.setItem(34, next_item);
    }
    if (Variables.category_page > 1) {
      inventory.setItem(28, previous_item);
    }
    inventory.setItem(49, back_item);
    player.openInventory(inventory);
  }
  
  public static void openPriceSet(String action, Player player, int price)
  {
    if (price < 0)
    {
      price = 0;
      Variables.price = 0;
    }
    Inventory inventory = null;
    if (action == "Sell") {
      inventory = Bukkit.createInventory(null, 54, "Set sell price");
    } else if (action == "Buy") {
      inventory = Bukkit.createInventory(null, 54, "Set buy price");
    }
    ItemStack price_view = new ItemStack(Material.PAPER, 1);
    ItemStack done_item = new ItemStack(Material.STAINED_GLASS, 1, (short)13);
    ItemStack cancel_item = new ItemStack(Material.STAINED_GLASS, 1, (short)14);
    ItemStack amount_edit = new ItemStack(Material.ENCHANTED_BOOK, 1);
    
    ItemMeta view_meta = price_view.getItemMeta();
    ItemMeta done_meta = done_item.getItemMeta();
    ItemMeta cancel_meta = cancel_item.getItemMeta();
    ItemMeta amount_meta = amount_edit.getItemMeta();
    
    view_meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
    view_meta.setDisplayName(ChatColor.GREEN + "Price");
    
    ArrayList<String> lore = new ArrayList();
    lore.add("");
    lore.add(ChatColor.GRAY + "Your price is: " + ChatColor.GOLD + price);
    view_meta.setLore(lore);
    
    done_meta.setDisplayName(ChatColor.GREEN + "Save");
    done_meta.setLore(lore);
    
    cancel_meta.setDisplayName(ChatColor.RED + "Cancel");
    
    amount_meta.setDisplayName(ChatColor.GREEN + "Edit items amount");
    lore.clear();
    lore.add("");
    lore.add(ChatColor.GRAY + "Players " + action.toLowerCase() + " " + ChatColor.GOLD + Variables.amount + " item" + (Variables.amount == 1 ? "" : "s") + ChatColor.GRAY + " for " + ChatColor.GOLD + price);
    amount_meta.setLore(lore);
    
    price_view.setItemMeta(view_meta);
    done_item.setItemMeta(done_meta);
    cancel_item.setItemMeta(cancel_meta);
    amount_edit.setItemMeta(amount_meta);
    
    inventory.setItem(4, price_view);
    inventory.setItem(19, Buttons.REMOVE_1);
    inventory.setItem(20, Buttons.REMOVE_10);
    inventory.setItem(21, Buttons.REMOVE_50);
    inventory.setItem(28, Buttons.REMOVE_100);
    inventory.setItem(29, Buttons.REMOVE_1000);
    
    inventory.setItem(23, Buttons.ADD_1);
    inventory.setItem(24, Buttons.ADD_10);
    inventory.setItem(25, Buttons.ADD_50);
    inventory.setItem(33, Buttons.ADD_100);
    inventory.setItem(34, Buttons.ADD_1000);
    
    inventory.setItem(31, amount_edit);
    inventory.setItem(48, done_item);
    inventory.setItem(50, cancel_item);
    
    player.openInventory(inventory);
  }
  
  public static void amountSet(Player player, int amount)
  {
    Inventory inventory = Bukkit.createInventory(null, 54, "Set amount");
    if (amount < 1)
    {
      amount = 1;
      Variables.temp_amount = 1;
    }
    ItemStack view = new ItemStack(Material.PAPER, 1);
    ItemStack save = new ItemStack(Material.STAINED_GLASS, 1, (short)13);
    ItemStack cancel = new ItemStack(Material.STAINED_GLASS, 1, (short)14);
    
    ItemMeta view_meta = view.getItemMeta();
    ItemMeta save_meta = save.getItemMeta();
    ItemMeta cancel_meta = cancel.getItemMeta();
    
    ArrayList<String> lore = new ArrayList();
    lore.add("");
    lore.add(ChatColor.GRAY + "You set " + ChatColor.GOLD + amount + " item" + (amount == 1 ? "" : "s"));
    
    view_meta.setDisplayName(ChatColor.GREEN + "Amount");
    view_meta.setLore(lore);
    save_meta.setDisplayName(ChatColor.GREEN + "Save");
    cancel_meta.setDisplayName(ChatColor.RED + "Cancel");
    
    view_meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
    
    view.setItemMeta(view_meta);
    save.setItemMeta(save_meta);
    cancel.setItemMeta(cancel_meta);
    
    inventory.setItem(4, view);
    inventory.setItem(19, Buttons.REMOVE_1);
    inventory.setItem(20, Buttons.REMOVE_4);
    inventory.setItem(21, Buttons.REMOVE_8);
    inventory.setItem(28, Buttons.REMOVE_16);
    inventory.setItem(29, Buttons.REMOVE_32);
    
    inventory.setItem(23, Buttons.ADD_1);
    inventory.setItem(24, Buttons.ADD_4);
    inventory.setItem(25, Buttons.ADD_8);
    inventory.setItem(33, Buttons.ADD_16);
    inventory.setItem(34, Buttons.ADD_32);
    
    inventory.setItem(48, save);
    inventory.setItem(50, cancel);
    
    player.openInventory(inventory);
  }
}
