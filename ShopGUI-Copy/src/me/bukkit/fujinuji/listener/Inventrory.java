package me.bukkit.fujinuji.listener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import me.bukkit.fujinuji.ShopPlus;
import me.bukkit.fujinuji.gui.CategoryManager;
import me.bukkit.fujinuji.gui.InventoryOpen;
import me.bukkit.fujinuji.gui.MainMenu;
import me.bukkit.fujinuji.gui.PlayerGUI;
import me.bukkit.fujinuji.gui.PriceManager;
import me.bukkit.fujinuji.store.Items;
import me.bukkit.fujinuji.store.Restore;
import me.bukkit.fujinuji.store.Variables;
import me.bukkit.fujinuji.store.YamlConfig;
import me.bukkit.fujinuji.util.Comp;
import me.bukkit.fujinuji.util.GetCode;
import me.bukkit.fujinuji.util.MaxItems;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConfigurationOptions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class Inventrory
  implements Listener
{
  public static Player player;
  String name;
  
  @EventHandler
  public void invInteract(InventoryClickEvent e)
  {
    if (!(e.getWhoClicked() instanceof Player)) {
      return;
    }
    if (e.getCurrentItem() == null) {
      return;
    }
    if (e.getCurrentItem().getType() == Material.AIR) {
      return;
    }
    player = (Player)e.getWhoClicked();
    Inventory inventory = e.getInventory();
    ItemStack item_clicked = e.getCurrentItem();
    if (inventory.getName().contains("Page"))
    {
      e.setCancelled(true);
      if ((e.getSlot() == 48) && (item_clicked.getType() == Material.ARROW))
      {
        Variables.page -= 1;
        Variables.last -= 21;
        InventoryOpen.UpdateInventoryChoose(player, Variables.page, "Page", Items.getShopItems());
      }
      if ((e.getSlot() == 50) && (item_clicked.getType() == Material.ARROW))
      {
        Variables.page += 1;
        Variables.last += 21;
        InventoryOpen.UpdateInventoryChoose(player, Variables.page, "Page", Items.getShopItems());
      }
    }
    if (inventory.getName() == "Main Menu")
    {
      e.setCancelled(true);
      
      Variables.items.clear();
      Variables.available_items = new ArrayList(Items.getShopItems());
      if ((e.getSlot() == 11) && (item_clicked.getType() == Material.COMPASS))
      {
        Variables.last_category_index = 0;
        Variables.category_page = 1;
        PlayerGUI.categorySelect(player);
      }
      if ((e.getSlot() == 13) && (item_clicked.getType() == Material.getMaterial(356)))
      {
        Variables.category_page = 1;
        Variables.last_category_index = 0;
        PriceManager.priceMangerOpen(player);
      }
      if ((e.getSlot() == 15) && (item_clicked.getType() == Material.BOOKSHELF)) {
        if (Items.getCategoryItems() == null)
        {
          CategoryManager.noItemFound(player);
        }
        else if (Items.getCategoryItems().size() != 0)
        {
          Variables.last_category_index = 0;
          Variables.category_page = 1;
          CategoryManager.categoryMenu(player);
        }
        else
        {
          CategoryManager.noItemFound(player);
        }
      }
    }
    if (inventory.getName() == "Manage categories")
    {
      e.setCancelled(true);
      if ((e.getSlot() == 28) && (item_clicked.getType() == Material.ARROW))
      {
        Variables.category_page -= 1;
        Variables.last_category_index -= 19;
        CategoryManager.categoryMenu(player);
      }
      if ((e.getSlot() == 34) && (item_clicked.getType() == Material.ARROW))
      {
        Variables.category_page += 1;
        Variables.last_category_index += 19;
        CategoryManager.categoryMenu(player);
      }
      if ((e.getSlot() == 48) && (item_clicked.getType() == Material.STAINED_GLASS))
      {
        Variables.available_items = (ArrayList)Items.getShopItems().clone();
        CategoryManager.addCategory(player, null, null, null);
      }
      if ((e.getSlot() == 49) && (item_clicked.getType() == Material.ARROW)) {
        MainMenu.open(player);
      }
      if ((e.getSlot() == 50) && (item_clicked.getType() == Material.STAINED_GLASS))
      {
        Variables.remove_index = 0;
        Variables.remove_page = 1;
        CategoryManager.removeCategory(player);
      }
      if ((e.getSlot() >= 10) && (e.getSlot() <= 33) && (e.getSlot() != 28) && (item_clicked.getTypeId() != 0))
      {
        this.name = item_clicked.getItemMeta().getDisplayName();
        
        Variables.final_items = new ArrayList(YamlConfig.getCategoryConfiguration().getList("Category." + this.name.substring(2, this.name.length()) + ".items"));
        Variables.items_edit = new ArrayList((Collection)Items.getShopItems().clone());
        
        CategoryManager.manageCategory(player, YamlConfig.getCategoryConfiguration().getString("Category." + this.name.substring(2, this.name.length()) + ".name"), GetCode.getItemStack(GetCode.getMinecraftCode(YamlConfig.getCategoryConfiguration().getString("Category." + this.name.substring(2, this.name.length()) + ".head_item"))), (ArrayList)YamlConfig.getCategoryConfiguration().getList("Category." + this.name.substring(2, this.name.length()) + ".items"), YamlConfig.getCategoryConfiguration().getString("Category." + this.name.substring(2, this.name.length()) + ".visibility"));
      }
    }
    if (inventory.getName() == "Remove category")
    {
      e.setCancelled(true);
      if ((e.getSlot() >= 10) && (e.getSlot() <= 33) && (e.getSlot() != 28))
      {
        StringBuilder sb = new StringBuilder(item_clicked.getItemMeta().hasDisplayName() ? item_clicked.getItemMeta().getDisplayName().trim() : null);
        
        YamlConfig.getCategoryConfiguration().set("Category." + sb.toString().substring(2, sb.toString().length()), null);
        YamlConfig.getCategoryConfiguration().options().copyDefaults(true);
        try
        {
          YamlConfig.getCategoryConfiguration().save(ShopPlus.CategoryFile);
        }
        catch (IOException localIOException) {}
        Items.loadItems();
        if (Items.getCategoryItems() != null)
        {
          if (Items.getCategoryItems().size() > 0) {
            CategoryManager.removeCategory(player);
          } else {
            CategoryManager.noItemFound(player);
          }
        }
        else {
          CategoryManager.noItemFound(player);
        }
      }
      if ((e.getSlot() == 28) && (item_clicked.getType() == Material.ARROW))
      {
        Variables.remove_page -= 1;
        Variables.remove_index -= 19;
        CategoryManager.removeCategory(player);
      }
      if ((e.getSlot() == 34) && (item_clicked.getType() == Material.ARROW))
      {
        Variables.remove_page += 1;
        Variables.remove_index += 19;
        CategoryManager.removeCategory(player);
      }
      if ((e.getSlot() == 49) && (item_clicked.getType() == Material.STAINED_GLASS))
      {
        Variables.last_category_index = 0;
        Variables.category_page = 1;
        CategoryManager.categoryMenu(player);
      }
    }
    if (inventory.getName() == "Create a new category")
    {
      e.setCancelled(true);
      if ((e.getSlot() == 20) && (item_clicked.getType() == Material.BOOK))
      {
        ChatInput.done = true;
        ChatInput.player = player;
        ChatInput.edit_mode = false;
        
        player.closeInventory();
        player.sendMessage(ChatColor.GREEN + "\nType in chat a name for your catgory or CANCEL to cancel");
      }
      if (e.getSlot() == 22)
      {
        Variables.last = 0;
        InventoryOpen.UpdateInventoryChoose(player, 1, "Choose item for category head", Items.getShopItems());
      }
      if ((e.getSlot() == 24) && (item_clicked.getType() == Material.DIAMOND_PICKAXE))
      {
        Variables.last = 0;
        InventoryOpen.UpdateInventoryChoose(player, 1, "Select items for category", Variables.available_items);
      }
      if ((e.getSlot() == 39) && (item_clicked.getType() == Material.STAINED_GLASS))
      {
        Boolean can = Boolean.valueOf(false);
        if ((Variables.items != null) && 
          (CategoryManager.category_name != null) && (Variables.items.size() > 0) && (CategoryManager.head_item != null)) {
          can = Boolean.valueOf(true);
        }
        if (can.booleanValue())
        {
          ArrayList<String> items = new ArrayList();
          for (String item : Variables.items) {
            items.add(item);
          }
          YamlConfig.getCategoryConfiguration().set("Category." + CategoryManager.category_name + ".name", CategoryManager.category_name);
          YamlConfig.getCategoryConfiguration().set("Category." + CategoryManager.category_name + ".head_item", "code_" + CategoryManager.head_item.getTypeId() + (CategoryManager.head_item.getData().getData() != 0 ? "/" + CategoryManager.head_item.getData().getData() : ""));
          YamlConfig.getCategoryConfiguration().set("Category." + CategoryManager.category_name + ".items", items);
          YamlConfig.getCategoryConfiguration().set("Category." + CategoryManager.category_name + ".visibility", "False");
          
          YamlConfig.getCategoryConfiguration().options().copyDefaults(true);
          try
          {
            YamlConfig.getCategoryConfiguration().save(ShopPlus.CategoryFile);
          }
          catch (IOException localIOException1) {}
          Items.loadItems();
          Restore.restoreCategoryManager();
          
          CategoryManager.categoryMenu(player);
        }
      }
      if ((e.getSlot() == 41) && (item_clicked.getType() == Material.STAINED_GLASS))
      {
        Restore.restoreCategoryManager();
        Variables.last_category_index = 0;
        Variables.category_page = 1;
        if (Items.getCategoryItems() != null)
        {
          if (Items.getCategoryItems().size() > 0) {
            CategoryManager.categoryMenu(player);
          } else {
            CategoryManager.noItemFound(player);
          }
        }
        else {
          CategoryManager.noItemFound(player);
        }
      }
    }
    if (inventory.getName() == "Choose item for category head")
    {
      e.setCancelled(true);
      if ((e.getSlot() == 48) && (item_clicked.getType() == Material.ARROW))
      {
        Variables.page -= 1;
        Variables.last -= 21;
        InventoryOpen.UpdateInventoryChoose(player, Variables.page, "Choose item for category head", Items.getShopItems());
      }
      if ((e.getSlot() == 50) && (item_clicked.getType() == Material.ARROW))
      {
        Variables.page += 1;
        Variables.last += 21;
        InventoryOpen.UpdateInventoryChoose(player, Variables.page, "Choose item for category head", Items.getShopItems());
      }
      if ((e.getSlot() >= 10) && (e.getSlot() <= 34))
      {
        CategoryManager.head_item = e.getCurrentItem();
        CategoryManager.addCategory(player, CategoryManager.category_name, e.getCurrentItem(), Variables.items);
      }
    }
    if ((inventory.getName() != "Create a new category") && (inventory.getName() != "Choose item for category head") && (!ChatInput.done) && (inventory.getName() != "Select items for category"))
    {
      CategoryManager.category_name = null;
      CategoryManager.head_item = null;
      CategoryManager.items = null;
    }
    if (inventory.getName() == "Select items for category")
    {
      e.setCancelled(true);
      if ((e.getSlot() == 48) && (item_clicked.getType() == Material.ARROW))
      {
        Variables.page -= 1;
        Variables.last -= 21;
        InventoryOpen.UpdateInventoryChoose(player, Variables.page, "Select items for category", Variables.available_items);
      }
      if ((e.getSlot() == 50) && (item_clicked.getType() == Material.ARROW))
      {
        Variables.page += 1;
        Variables.last += 21;
        InventoryOpen.UpdateInventoryChoose(player, Variables.page, "Select items for category", Variables.available_items);
      }
      if ((e.getSlot() >= 10) && (e.getSlot() <= 34))
      {
        String item = "code_" + item_clicked.getTypeId() + "/" + item_clicked.getData().getData();
        
        Variables.items.add(item);
        Variables.available_items.remove(item);
        InventoryOpen.UpdateInventoryChoose(player, Variables.page, "Select items for category", Variables.available_items);
      }
      if ((e.getSlot() == 49) && (item_clicked.getType() == Material.STAINED_GLASS))
      {
        CategoryManager.items = Variables.items;
        CategoryManager.addCategory(player, CategoryManager.category_name, CategoryManager.head_item, Variables.items);
      }
    }
    if (inventory.getName() == "Manage category")
    {
      e.setCancelled(true);
      if ((e.getSlot() == 10) && (item_clicked.getType() == Material.BOOK_AND_QUILL))
      {
        ChatInput.done = true;
        ChatInput.player = player;
        ChatInput.edit_mode = true;
        
        player.closeInventory();
        player.sendMessage(ChatColor.GREEN + "\nType in chat a name for your catgory or CANCEL to cancel");
      }
      if ((e.getSlot() == 12) && (item_clicked.getType() != Material.BARRIER))
      {
        Variables.last = 0;
        InventoryOpen.UpdateInventoryChoose(player, 1, "Edit item for category's head", Items.getShopItems());
      }
      if ((e.getSlot() == 14) && (item_clicked.getType() == Material.WOOL))
      {
        for (String s : Variables.final_items) {
          Variables.items_edit.remove(s);
        }
        Variables.last = 0;
        Variables.adder_page = 1;
        
        Collections.sort(Variables.final_items, new Comp());
        
        InventoryOpen.UpdateInventoryChoose(player, Variables.adder_page, "Add items", Variables.items_edit);
      }
      if ((e.getSlot() == 16) && (item_clicked.getType() == Material.WOOL))
      {
        Variables.last = 0;
        Variables.adder_page = 1;
        
        InventoryOpen.UpdateInventoryChoose(player, Variables.adder_page, "Remove items", Variables.final_items);
      }
      if ((e.getSlot() == 31) && (item_clicked.getType() == Material.getMaterial(351))) {
        if (Variables.visibility_edit.contains("False")) {
          CategoryManager.manageCategory(player, null, null, null, "True");
        } else if ((Variables.visibility_edit != null) || (Variables.visibility_edit != "")) {
          CategoryManager.manageCategory(player, null, null, null, "False");
        }
      }
      if ((e.getSlot() == 48) && (item_clicked.getType() == Material.STAINED_GLASS)) {
        if ((Variables.name_edit.toLowerCase().contains(this.name.substring(2, this.name.length()).toLowerCase())) && (Variables.name_edit.length() == this.name.substring(2, this.name.length()).length()) && (Variables.final_items.size() > 0))
        {
          YamlConfig.getCategoryConfiguration().set("Category." + this.name.substring(2, this.name.length()) + ".head_item", "code_" + Variables.head_edit.getTypeId() + (Variables.head_edit.getData().getData() != 0 ? "/" + Variables.head_edit.getData().getData() : ""));
          YamlConfig.getCategoryConfiguration().set("Category." + this.name.substring(2, this.name.length()) + ".items", Variables.final_items);
          YamlConfig.getCategoryConfiguration().set("Category." + this.name.substring(2, this.name.length()) + ".visibility", Variables.visibility_edit);
          
          YamlConfig.getCategoryConfiguration().options().copyDefaults(true);
          try
          {
            YamlConfig.getCategoryConfiguration().save(ShopPlus.CategoryFile);
          }
          catch (IOException localIOException2) {}
          Items.loadItems();
          if (Items.getCategoryItems() == null)
          {
            CategoryManager.noItemFound(player);
          }
          else if (Items.getCategoryItems().size() != 0)
          {
            Variables.last_category_index = 0;
            Variables.category_page = 1;
            CategoryManager.categoryMenu(player);
          }
          else
          {
            CategoryManager.noItemFound(player);
          }
        }
        else if (Variables.final_items.size() > 0)
        {
          YamlConfig.getCategoryConfiguration().set("Category." + this.name.substring(2, this.name.length()), null);
          
          YamlConfig.getCategoryConfiguration().set("Category." + Variables.name_edit + ".name", Variables.name_edit);
          YamlConfig.getCategoryConfiguration().set("Category." + Variables.name_edit + ".head_item", "code_" + Variables.head_edit.getTypeId() + (Variables.head_edit.getData().getData() != 0 ? "/" + Variables.head_edit.getData().getData() : ""));
          YamlConfig.getCategoryConfiguration().set("Category." + Variables.name_edit + ".items", Variables.final_items);
          YamlConfig.getCategoryConfiguration().set("Category." + Variables.name_edit + ".visibility", Variables.visibility_edit);
          
          YamlConfig.getCategoryConfiguration().options().copyDefaults(true);
          try
          {
            YamlConfig.getCategoryConfiguration().save(ShopPlus.CategoryFile);
          }
          catch (IOException localIOException3) {}
          Items.loadItems();
          if (Items.getCategoryItems() == null)
          {
            CategoryManager.noItemFound(player);
          }
          else if (Items.getCategoryItems().size() != 0)
          {
            Variables.last_category_index = 0;
            Variables.category_page = 1;
            CategoryManager.categoryMenu(player);
          }
          else
          {
            CategoryManager.noItemFound(player);
          }
        }
      }
      if ((e.getSlot() == 50) && (item_clicked.getType() == Material.STAINED_GLASS))
      {
        Variables.name_edit = null;
        Variables.head_edit = null;
        Variables.items_edit = null;
        Variables.visibility_edit = null;
        if (Items.getCategoryItems() != null)
        {
          if (Items.getCategoryItems().size() > 0) {
            CategoryManager.categoryMenu(player);
          } else {
            CategoryManager.noItemFound(player);
          }
        }
        else {
          CategoryManager.noItemFound(player);
        }
      }
    }
    if (inventory.getName() == "Edit item for category's head")
    {
      e.setCancelled(true);
      if ((e.getSlot() == 48) && (item_clicked.getType() == Material.ARROW))
      {
        Variables.page -= 1;
        Variables.last -= 21;
        InventoryOpen.UpdateInventoryChoose(player, Variables.page, "Edit item for category's head", Items.getShopItems());
      }
      if ((e.getSlot() == 50) && (item_clicked.getType() == Material.ARROW))
      {
        Variables.page += 1;
        Variables.last += 21;
        InventoryOpen.UpdateInventoryChoose(player, Variables.page, "Edit item for category's head", Items.getShopItems());
      }
      if ((e.getSlot() >= 10) && (e.getSlot() <= 34) && (item_clicked.getType() != Material.AIR)) {
        CategoryManager.manageCategory(player, null, e.getCurrentItem(), null, null);
      }
    }
    if (inventory.getName() == "Add items")
    {
      e.setCancelled(true);
      if ((e.getSlot() == 48) && (item_clicked.getType() == Material.ARROW))
      {
        Variables.adder_page -= 1;
        Variables.last -= 21;
        InventoryOpen.UpdateInventoryChoose(player, Variables.adder_page, "Add items", Variables.items_edit);
      }
      if ((e.getSlot() == 50) && (item_clicked.getType() == Material.ARROW))
      {
        Variables.adder_page += 1;
        Variables.last += 21;
        InventoryOpen.UpdateInventoryChoose(player, Variables.adder_page, "Add items", Variables.items_edit);
      }
      if ((e.getSlot() >= 10) && (e.getSlot() <= 34) && (item_clicked.getType() != Material.AIR))
      {
        String item = "code_" + item_clicked.getTypeId() + "/" + item_clicked.getData().getData();
        
        Variables.final_items.add(item);
        Variables.items_edit.remove(item);
        
        Variables.final_items.sort(new Comp());
        InventoryOpen.UpdateInventoryChoose(player, Variables.adder_page, "Add items", Variables.items_edit);
      }
      if ((e.getSlot() == 49) && (item_clicked.getType() == Material.STAINED_GLASS)) {
        CategoryManager.manageCategory(player, null, null, Variables.final_items, null);
      }
    }
    if (inventory.getName() == "Remove items")
    {
      e.setCancelled(true);
      if ((e.getSlot() == 48) && (item_clicked.getType() == Material.ARROW))
      {
        Variables.adder_page -= 1;
        Variables.last -= 21;
        InventoryOpen.UpdateInventoryChoose(player, Variables.adder_page, "Remove items", Variables.final_items);
      }
      if ((e.getSlot() == 50) && (item_clicked.getType() == Material.ARROW))
      {
        Variables.adder_page += 1;
        Variables.last += 21;
        InventoryOpen.UpdateInventoryChoose(player, Variables.adder_page, "Remove items", Variables.final_items);
      }
      if ((e.getSlot() >= 10) && (e.getSlot() <= 34) && (item_clicked.getType() != Material.AIR))
      {
        String item = "code_" + item_clicked.getTypeId() + "/" + item_clicked.getData().getData();
        for (String s : Variables.final_items) {
          Variables.items_edit.remove(s);
        }
        Variables.final_items.remove(item);
        Variables.items_edit.add(item);
        
        Variables.items_edit.sort(new Comp());
        
        InventoryOpen.UpdateInventoryChoose(player, Variables.adder_page, "Remove items", Variables.final_items);
      }
      if ((e.getSlot() == 49) && (item_clicked.getType() == Material.STAINED_GLASS)) {
        CategoryManager.manageCategory(player, null, null, Variables.final_items, null);
      }
    }
    if (inventory.getName() == "Categories list")
    {
      e.setCancelled(true);
      if ((e.getSlot() == 28) && (item_clicked.getType() == Material.ARROW))
      {
        Variables.category_page -= 1;
        Variables.last_category_index -= 19;
        PriceManager.priceMangerOpen(player);
      }
      if ((e.getSlot() == 34) && (item_clicked.getType() == Material.ARROW))
      {
        Variables.category_page += 1;
        Variables.last_category_index += 19;
        PriceManager.priceMangerOpen(player);
      }
      if ((e.getSlot() == 49) && (item_clicked.getType() == Material.ARROW)) {
        MainMenu.open(player);
      }
      if ((e.getSlot() >= 10) && (e.getSlot() <= 33) && (e.getSlot() != 28))
      {
        Variables.last = 0;
        Variables.page = 1;
        Variables.category_name = item_clicked.getItemMeta().getDisplayName().substring(2, item_clicked.getItemMeta().getDisplayName().length());
        InventoryOpen.UpdateInventoryChoose(player, 1, "Category items", (ArrayList)YamlConfig.getCategoryConfiguration().getList("Category." + Variables.category_name + ".items"));
      }
    }
    if (inventory.getName() == "Category items")
    {
      e.setCancelled(true);
      if ((e.getSlot() >= 10) && (e.getSlot() <= 33) && (e.getClick() == ClickType.LEFT) && (item_clicked.getType() != Material.AIR))
      {
        String buy_price = YamlConfig.getShopConfiguration().getString("Items.code_" + item_clicked.getTypeId() + "/" + item_clicked.getData().getData() + ".buy");
        Variables.price = Double.parseDouble(buy_price.length() == 0 ? "0" : buy_price);
        Variables.amount = Integer.parseInt(YamlConfig.getShopConfiguration().getString("Items.code_" + item_clicked.getTypeId() + "/" + item_clicked.getData().getData() + ".buy_amount"));
        Variables.item_code = "code_" + item_clicked.getTypeId() + "/" + item_clicked.getData().getData();
        Variables.action = "Buy";
        PriceManager.openPriceSet("Buy", player, Variables.price);
      }
      if ((e.getSlot() >= 10) && (e.getSlot() <= 33) && (e.getClick() == ClickType.RIGHT) && (item_clicked.getType() != Material.AIR))
      {
        String sell_price = YamlConfig.getShopConfiguration().getString("Items.code_" + item_clicked.getTypeId() + "/" + item_clicked.getData().getData() + ".sell");
        Variables.price = Double.parseDouble(sell_price.length() == 0 ? "0" : sell_price);
        Variables.amount = Integer.parseInt(YamlConfig.getShopConfiguration().getString("Items.code_" + item_clicked.getTypeId() + "/" + item_clicked.getData().getData() + ".sell_amount"));
        Variables.item_code = "code_" + item_clicked.getTypeId() + "/" + item_clicked.getData().getData();
        Variables.action = "Sell";
        PriceManager.openPriceSet("Sell", player, Variables.price);
      }
      if ((e.getSlot() == 48) && (item_clicked.getType() == Material.ARROW))
      {
        Variables.page -= 1;
        Variables.last -= 21;
        InventoryOpen.UpdateInventoryChoose(player, Variables.page, "Category items", (ArrayList)YamlConfig.getCategoryConfiguration().getList("Category." + Variables.category_name + ".items"));
      }
      if ((e.getSlot() == 49) && (item_clicked.getType() == Material.STAINED_GLASS)) {
        PriceManager.priceMangerOpen(player);
      }
      if ((e.getSlot() == 50) && (item_clicked.getType() == Material.ARROW))
      {
        Variables.page += 1;
        Variables.last += 21;
        InventoryOpen.UpdateInventoryChoose(player, Variables.page, "Category items", (ArrayList)YamlConfig.getCategoryConfiguration().getList("Category." + Variables.category_name + ".items"));
      }
    }
    if ((inventory.getName() == "Set buy price") || (inventory.getName() == "Set sell price"))
    {
      e.setCancelled(true);
      switch (e.getSlot())
      {
      case 19: 
        Variables.price -= 1;
        PriceManager.openPriceSet(Variables.action, player, Variables.price);
        break;
      case 20: 
        Variables.price -= 10;
        PriceManager.openPriceSet(Variables.action, player, Variables.price);
        break;
      case 21: 
        Variables.price -= 50;
        PriceManager.openPriceSet(Variables.action, player, Variables.price);
        break;
      case 23: 
        Variables.price += 1;
        PriceManager.openPriceSet(Variables.action, player, Variables.price);
        break;
      case 24: 
        Variables.price += 10;
        PriceManager.openPriceSet(Variables.action, player, Variables.price);
        break;
      case 25: 
        Variables.price += 50;
        PriceManager.openPriceSet(Variables.action, player, Variables.price);
        break;
      case 28: 
        Variables.price -= 100;
        PriceManager.openPriceSet(Variables.action, player, Variables.price);
        break;
      case 29: 
        Variables.price -= 1000;
        PriceManager.openPriceSet(Variables.action, player, Variables.price);
        break;
      case 31: 
        Variables.temp_amount = Variables.amount;
        PriceManager.amountSet(player, Variables.temp_amount);
        break;
      case 33: 
        Variables.price += 100;
        PriceManager.openPriceSet(Variables.action, player, Variables.price);
        break;
      case 34: 
        Variables.price += 1000;
        PriceManager.openPriceSet(Variables.action, player, Variables.price);
      }
      if (e.getSlot() == 48)
      {
        YamlConfig.getShopConfiguration().set("Items." + Variables.item_code + "." + Variables.action.toLowerCase(), Variables.price == 0 ? "" : String.valueOf(Variables.price));
        YamlConfig.getShopConfiguration().set("Items." + Variables.item_code + "." + Variables.action.toLowerCase() + "_amount", String.valueOf(Variables.amount));
        YamlConfig.getShopConfiguration().options().copyDefaults(true);
        try
        {
          YamlConfig.getShopConfiguration().save(ShopPlus.ShopItemsFile);
        }
        catch (IOException localIOException4) {}
        InventoryOpen.UpdateInventoryChoose(player, 1, "Category items", (ArrayList)YamlConfig.getCategoryConfiguration().getList("Category." + Variables.category_name + ".items"));
      }
      if (e.getSlot() == 50)
      {
        Variables.price = 0.0;
        Variables.action = "";
        InventoryOpen.UpdateInventoryChoose(player, Variables.page, "Category items", (ArrayList)YamlConfig.getCategoryConfiguration().getList("Category." + Variables.category_name + ".items"));
      }
    }
    if (inventory.getName() == "Set amount")
    {
      e.setCancelled(true);
      switch (e.getSlot())
      {
      case 19: 
        Variables.temp_amount -= 1;
        PriceManager.amountSet(player, Variables.temp_amount);
        break;
      case 20: 
        Variables.temp_amount -= 4;
        PriceManager.amountSet(player, Variables.temp_amount);
        break;
      case 21: 
        Variables.temp_amount -= 8;
        PriceManager.amountSet(player, Variables.temp_amount);
        break;
      case 23: 
        Variables.temp_amount += 1;
        PriceManager.amountSet(player, Variables.temp_amount);
        break;
      case 24: 
        Variables.temp_amount += 4;
        PriceManager.amountSet(player, Variables.temp_amount);
        break;
      case 25: 
        Variables.temp_amount += 8;
        PriceManager.amountSet(player, Variables.temp_amount);
        break;
      case 28: 
        Variables.temp_amount -= 16;
        PriceManager.amountSet(player, Variables.temp_amount);
        break;
      case 29: 
        Variables.temp_amount -= 32;
        PriceManager.amountSet(player, Variables.temp_amount);
        break;
      case 33: 
        Variables.temp_amount += 16;
        PriceManager.amountSet(player, Variables.temp_amount);
        break;
      case 34: 
        Variables.temp_amount += 32;
        PriceManager.amountSet(player, Variables.temp_amount);
      }
      if (e.getSlot() == 48)
      {
        Variables.amount = Variables.temp_amount;
        PriceManager.openPriceSet(Variables.action, player, Variables.price);
      }
      if (e.getSlot() == 50) {
        PriceManager.openPriceSet(Variables.action, player, Variables.price);
      }
    }
    if (inventory.getName() == "Categories")
    {
      e.setCancelled(true);
      if ((e.getSlot() == 28) && (item_clicked.getType() == Material.ARROW))
      {
        Variables.player_page_index.put(player.getName(), Integer.valueOf(((Integer)Variables.player_page_index.get(player.getName())).intValue() - 1));
        Variables.player_last_index.put(player.getName(), Integer.valueOf(((Integer)Variables.player_last_index.get(player.getName())).intValue() - 19));
        
        PlayerGUI.categorySelect(player);
      }
      if ((e.getSlot() == 34) && (item_clicked.getType() == Material.ARROW))
      {
        Variables.player_page_index.put(player.getName(), Integer.valueOf(((Integer)Variables.player_page_index.get(player.getName())).intValue() + 1));
        Variables.player_last_index.put(player.getName(), Integer.valueOf(((Integer)Variables.player_last_index.get(player.getName())).intValue() + 19));
        PlayerGUI.categorySelect(player);
      }
      if ((e.getSlot() == 49) && (item_clicked.getType() == Material.STAINED_GLASS)) {
        player.closeInventory();
      }
      if ((e.getSlot() >= 10) && (e.getSlot() <= 33) && (e.getSlot() != 28))
      {
        Variables.player_last_list.put(player.getName(), Integer.valueOf(0));
        Variables.player_page_list.put(player.getName(), Integer.valueOf(1));
        
        Variables.category_name = item_clicked.getItemMeta().getDisplayName().substring(2, item_clicked.getItemMeta().getDisplayName().length());
        Variables.player_category.put(player.getName(), Variables.category_name);
        
        ArrayList<String> itemss = (ArrayList)YamlConfig.getCategoryConfiguration().getList("Category." + Variables.category_name + ".items");
        if ((itemss != null) && 
          (itemss.size() > 0)) {
          InventoryOpen.UpdateInventoryChoose(player, 1, "Items", (ArrayList)YamlConfig.getCategoryConfiguration().getList("Category." + Variables.category_name + ".items"));
        }
      }
    }
    if (inventory.getName() == "Items")
    {
      e.setCancelled(true);
      if ((e.getSlot() == 49) && (item_clicked.getType() == Material.STAINED_GLASS)) {
        PlayerGUI.categorySelect(player);
      }
      if ((e.getSlot() >= 10) && (e.getSlot() <= 33) && (e.getClick() == ClickType.LEFT) && (item_clicked.getType() != Material.AIR))
      {
        String code = "code_" + item_clicked.getTypeId() + "/" + item_clicked.getData().getData();
        
        String buy_price = YamlConfig.getShopConfiguration().getString("Items." + code + ".buy");
        int buy_amount = Integer.parseInt(YamlConfig.getShopConfiguration().getString("Items." + code + ".buy_amount"));
        if ((buy_price.length() > 0) && (buy_amount > 0)) {
          if (ShopPlus.getEconomy().getBalance(player) > Double.parseDouble(buy_price))
          {
            if (MaxItems.getMaxAmount(player, code) >= buy_amount)
            {
              EconomyResponse process = ShopPlus.getEconomy().withdrawPlayer(player, Double.parseDouble(buy_price));
              if (process.transactionSuccess())
              {
                player.getInventory().addItem(new ItemStack[] { GetCode.getStackPlayer(code, buy_amount) });
                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.GREEN + "Shop" + ChatColor.GRAY + "] " + ChatColor.GREEN + "You've bought " + ChatColor.GOLD + buy_amount + " item" + (buy_amount == 1 ? "" : "s") + ChatColor.GREEN + " for " + ChatColor.GOLD + buy_price);
              }
            }
            else
            {
              player.sendMessage(ChatColor.GRAY + "[" + ChatColor.GREEN + "Shop" + ChatColor.GRAY + "] " + ChatColor.RED + "You have not enough space in your inventory");
            }
          }
          else {
            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.GREEN + "Shop" + ChatColor.GRAY + "] " + ChatColor.RED + "You have not enough founds to buy this");
          }
        }
      }
      if ((e.getSlot() >= 10) && (e.getSlot() <= 33) && (e.getClick() == ClickType.RIGHT || e.getClick() == ClickType.SHIFT_RIGHT) && (item_clicked.getType() != Material.AIR))
      {
        String code = "code_" + item_clicked.getTypeId() + "/" + item_clicked.getData().getData();
        
        String sell_price = YamlConfig.getShopConfiguration().getString("Items." + code + ".sell");
        int sell_amount = Integer.parseInt(YamlConfig.getShopConfiguration().getString("Items." + code + ".sell_amount"));
        
        // Check if was shift clicked, change amount to one stack
        
        
        int player_has = MaxItems.getPlayerAmount(code, player);
        if ((sell_price.length() > 0) && (sell_amount > 0)) {
          if (sell_amount <= player_has)
          {
            EconomyResponse process = ShopPlus.getEconomy().depositPlayer(player, Double.parseDouble(sell_price));
            if (process.transactionSuccess())
            {
              player.getInventory().removeItem(new ItemStack[] { GetCode.getStackPlayer(code, sell_amount) });
              player.sendMessage(ChatColor.GRAY + "[" + ChatColor.GREEN + "Shop" + ChatColor.GRAY + "] " + ChatColor.GREEN + "You've sold " + ChatColor.GOLD + sell_amount + " item" + (sell_amount == 1 ? "" : "s") + ChatColor.GREEN + " for " + ChatColor.GOLD + sell_price);
            }
          }
          else
          {
            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.GREEN + "Shop" + ChatColor.GRAY + "] " + ChatColor.RED + "You have not enough items to sell");
          }
        }
      }
      if ((e.getSlot() == 48) && (item_clicked.getType() == Material.ARROW))
      {
        Variables.player_page_list.put(player.getName(), Integer.valueOf(((Integer)Variables.player_page_list.get(player.getName())).intValue() - 1));
        Variables.player_last_list.put(player.getName(), Integer.valueOf(((Integer)Variables.player_last_list.get(player.getName())).intValue() - 19));
        
        ArrayList<String> itemss = (ArrayList)YamlConfig.getCategoryConfiguration().getList("Category." + (String)Variables.player_category.get(player.getName()) + ".items");
        if ((itemss != null) && 
          (itemss.size() > 0)) {
          InventoryOpen.UpdateInventoryChoose(player, ((Integer)Variables.player_page_list.get(player.getName())).intValue(), "Items", itemss);
        }
      }
      if ((e.getSlot() == 50) && (item_clicked.getType() == Material.ARROW))
      {
        Variables.player_page_list.put(player.getName(), Integer.valueOf(((Integer)Variables.player_page_list.get(player.getName())).intValue() + 1));
        Variables.player_last_list.put(player.getName(), Integer.valueOf(((Integer)Variables.player_last_list.get(player.getName())).intValue() + 19));
        
        ArrayList<String> itemss = (ArrayList)YamlConfig.getCategoryConfiguration().getList("Category." + (String)Variables.player_category.get(player.getName()) + ".items");
        if ((itemss != null) && (itemss.size() > 0)) {
          InventoryOpen.UpdateInventoryChoose(player, ((Integer)Variables.player_page_list.get(player.getName())).intValue(), "Items", itemss);
        }
      }
    }
  }
}
