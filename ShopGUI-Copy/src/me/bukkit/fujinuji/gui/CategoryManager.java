package me.bukkit.fujinuji.gui;

import java.util.ArrayList;
import java.util.List;
import me.bukkit.fujinuji.store.Items;
import me.bukkit.fujinuji.store.Variables;
import me.bukkit.fujinuji.store.YamlConfig;
import me.bukkit.fujinuji.util.GetCode;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;

public class CategoryManager
  extends JavaPlugin
{
  private static ItemStack add_item = new ItemStack(Material.STAINED_GLASS, 1, (short)13);
  private static ItemStack remove_item = new ItemStack(Material.STAINED_GLASS, 1, (short)14);
  private static ItemStack next_item = new ItemStack(Material.ARROW, 1);
  private static ItemStack previous_item = new ItemStack(Material.ARROW, 1);
  private static ItemStack back_item = new ItemStack(Material.ARROW, 1);
  private static ItemStack remove_category = new ItemStack(Material.STAINED_GLASS, 1, (short)13);
  private static ItemMeta add_item_meta = add_item.getItemMeta();
  private static ItemMeta remove_item_meta = remove_item.getItemMeta();
  private static ItemMeta next_item_meta = next_item.getItemMeta();
  private static ItemMeta previous_item_meta = previous_item.getItemMeta();
  private static ItemMeta back_meta = back_item.getItemMeta();
  private static ItemMeta remove_category_meta = remove_category.getItemMeta();
  private static Inventory inventory = Bukkit.createInventory(null, 54, "Manage categories");
  public static String category_name;
  public static ItemStack head_item;
  public static ArrayList<String> items;
  public static String visibility;
  public static String name = null;
  
  public static void noItemFound(Player player)
  {
    setNames();
    
    ArrayList<String> Lore = new ArrayList();
    
    ItemStack error = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)14);
    ItemMeta error_meta = error.getItemMeta();
    
    error_meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "ERROR");
    Lore.add(ChatColor.GRAY + "No category found");
    error_meta.setLore(Lore);
    error.setItemMeta(error_meta);
    
    inventory.setItem(22, error);
    inventory.setItem(48, add_item);
    inventory.setItem(49, back_item);
    player.openInventory(inventory);
    
    Lore.clear();
  }
  
  public static void categoryMenu(Player player)
  {
    setNames();
    
    Inventory inventory = Bukkit.createInventory(null, 54, "Manage categories");
    
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
          lore.add(ChatColor.GRAY + "Head Item: " + ChatColor.GOLD + GetCode.getItemStack(GetCode.getMinecraftCode(YamlConfig.getCategoryConfiguration().getString(new StringBuilder("Category.").append((String)Items.getCategoryItems().get(index)).append(".head_item").toString()))).getType().name());
          lore.add("");
          lore.add(ChatColor.GRAY + "Items: " + ChatColor.GOLD + YamlConfig.getCategoryConfiguration().getList(new StringBuilder("Category.").append((String)Items.getCategoryItems().get(index)).append(".items").toString()).size());
          lore.add("");
          lore.add(ChatColor.GRAY + "Visibility: " + ChatColor.GOLD + YamlConfig.getCategoryConfiguration().getString(new StringBuilder("Category.").append((String)Items.getCategoryItems().get(index)).append(".visibility").toString()));
          lore.add("");
          lore.add(ChatColor.GREEN + "Click to edit this category");
          
          item_meta.setLore(lore);
          item_now.setItemMeta(item_meta);
          
          inventory.setItem(i * 9 - j + 1, item_now);
          index++;
        }
      }
    }
    if (Variables.category_page < Items.getCategoryItems().size() / 19 + 1) {
      inventory.setItem(34, next_item);
    }
    if (Variables.category_page > 1) {
      inventory.setItem(28, previous_item);
    }
    inventory.setItem(48, add_item);
    inventory.setItem(49, back_item);
    inventory.setItem(50, remove_item);
    player.openInventory(inventory);
  }
  
  private static void setNames()
  {
    add_item_meta.setDisplayName(ChatColor.GREEN + "Add category");
    remove_item_meta.setDisplayName(ChatColor.RED + "Remove Category");
    next_item_meta.setDisplayName(ChatColor.GREEN + "Next page");
    previous_item_meta.setDisplayName(ChatColor.GREEN + "Prevoius page");
    back_meta.setDisplayName(ChatColor.GREEN + "Back");
    remove_category_meta.setDisplayName(ChatColor.GREEN + "Done");
    
    add_item.setItemMeta(add_item_meta);
    remove_item.setItemMeta(remove_item_meta);
    next_item.setItemMeta(next_item_meta);
    previous_item.setItemMeta(previous_item_meta);
    back_item.setItemMeta(back_meta);
    remove_category.setItemMeta(remove_category_meta);
  }
  
  public static void addCategory(Player player, String name, ItemStack item, ArrayList<String> comming_items)
  {
    Inventory adder = Bukkit.createInventory(null, 45, "Create a new category");
    if (items == null) {
      items = new ArrayList();
    }
    ItemStack head_item = null;
    if (comming_items != null) {
      items = comming_items;
    }
    ArrayList<String> lore = new ArrayList();
    
    category_name = name;
    if (item != null)
    {
      head_item = item;
      ItemMeta head_meta = head_item.getItemMeta();
      head_meta.setDisplayName(ChatColor.YELLOW + "Head Item");
      lore.clear();
      lore.add("");
      lore.add(ChatColor.GRAY + "You've selected " + ChatColor.GOLD + head_item.getType().toString() + " (" + item.getTypeId() + ":" + item.getData().getData() + ")" + ChatColor.GRAY + " as head item");
      lore.add("");
      lore.add(ChatColor.GRAY + "Click to change head item");
      head_meta.setLore(lore);
      head_item.setItemMeta(head_meta);
    }
    else
    {
      head_item = new ItemStack(Material.BARRIER, 1);
      ItemMeta head_meta = head_item.getItemMeta();
      head_meta.setDisplayName(ChatColor.YELLOW + "Head Item");
      lore.add("");
      lore.add(ChatColor.GRAY + "You haven't select any item for this section.");
      lore.add(ChatColor.GRAY + "Click on this item to add one.");
      head_meta.setLore(lore);
      head_item.setItemMeta(head_meta);
    }
    ItemStack create_name = new ItemStack(Material.BOOK, 1);
    ItemMeta create_meta = create_name.getItemMeta();
    create_meta.setDisplayName(ChatColor.YELLOW + "Category name");
    lore.clear();
    if (name != null)
    {
      lore.add("");
      lore.add(ChatColor.GRAY + "Your category's name is: " + ChatColor.GOLD + name);
      lore.add("");
      lore.add(ChatColor.GRAY + "Click to change the name.");
    }
    else
    {
      lore.add("");
      lore.add(ChatColor.GRAY + "Click on this item to add category's name");
    }
    create_meta.setLore(lore);
    create_name.setItemMeta(create_meta);
    
    ItemStack category_items = new ItemStack(Material.DIAMOND_PICKAXE, 1);
    ItemMeta items_meta = category_items.getItemMeta();
    items_meta.setDisplayName(ChatColor.YELLOW + "Set category's items");
    lore.clear();
    lore.add("");
    lore.add(ChatColor.GRAY + "Click to add items for your category");
    if (items != null)
    {
      lore.add("");
      lore.add(ChatColor.GRAY + "You have: " + ChatColor.GOLD + items.size() + " item" + (items.size() == 1 ? "" : "s"));
    }
    items_meta.setLore(lore);
    category_items.setItemMeta(items_meta);
    
    adder.setItem(20, create_name);
    adder.setItem(22, head_item);
    adder.setItem(24, category_items);
    
    ItemStack add_category = new ItemStack(Material.STAINED_GLASS, 1, (short)13);
    ItemStack cancel = new ItemStack(Material.STAINED_GLASS, 1, (short)14);
    
    ItemMeta add_meta = add_category.getItemMeta();
    ItemMeta cancel_meta = cancel.getItemMeta();
    
    add_meta.setDisplayName(ChatColor.GREEN + "Add category");
    lore.clear();
    lore.add("");
    lore.add(ChatColor.GRAY + "Category's name: " + ChatColor.GOLD + (name == null ? "No name" : name));
    lore.add("");
    lore.add(ChatColor.GRAY + "Head item: " + ChatColor.GOLD + (head_item.getType() == Material.BARRIER ? " No head item" : head_item.getType().toString()));
    lore.add("");
    lore.add(ChatColor.GRAY + "Items: " + ChatColor.GOLD + (items == null ? "No items" : new StringBuilder(String.valueOf(items.size())).append(items.size() == 1 ? " item" : " items").toString()));
    lore.add("");
    lore.add(ChatColor.GRAY + "Visibility: " + ChatColor.GOLD + "False (Defaut)");
    lore.add("");
    if ((Variables.items != null) && (Variables.items.size() != 0) && (category_name != null) && (head_item.getType() != Material.BARRIER))
    {
      lore.add(ChatColor.GREEN + "You can save your category");
    }
    else
    {
      lore.add(ChatColor.RED + "You can't save your category");
      lore.add(ChatColor.RED + "You must to add a name, an item head or items");
    }
    add_meta.setLore(lore);
    add_category.setItemMeta(add_meta);
    
    cancel_meta.setDisplayName(ChatColor.RED + "Cancel");
    cancel.setItemMeta(cancel_meta);
    
    adder.setItem(39, add_category);
    adder.setItem(41, cancel);
    
    player.openInventory(adder);
  }
  
  public static void removeCategory(Player player)
  {
    setNames();
    
    Inventory inventory = Bukkit.createInventory(null, 54, "Remove category");
    
    int index = Variables.remove_index;
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
          lore.add(ChatColor.GRAY + "Category's name: " + ChatColor.GOLD + YamlConfig.getCategoryConfiguration().getString(new StringBuilder("Category.").append((String)Items.getCategoryItems().get(index)).append(".name").toString()));
          lore.add("");
          lore.add(ChatColor.GRAY + "Head Item: " + ChatColor.GOLD + GetCode.getItemStack(GetCode.getMinecraftCode(YamlConfig.getCategoryConfiguration().getString(new StringBuilder("Category.").append((String)Items.getCategoryItems().get(index)).append(".head_item").toString()))).getType().name());
          lore.add("");
          lore.add(ChatColor.GRAY + "Items: " + ChatColor.GOLD + YamlConfig.getCategoryConfiguration().getList(new StringBuilder("Category.").append((String)Items.getCategoryItems().get(index)).append(".items").toString()).size());
          lore.add("");
          lore.add(ChatColor.GRAY + "Visibility: " + ChatColor.GOLD + YamlConfig.getCategoryConfiguration().getString(new StringBuilder("Category.").append((String)Items.getCategoryItems().get(index)).append(".visibility").toString()));
          lore.add("");
          lore.add(ChatColor.RED + "Click to remove this category");
          
          item_meta.setLore(lore);
          item_now.setItemMeta(item_meta);
          
          inventory.setItem(i * 9 - j + 1, item_now);
          index++;
        }
      }
    }
    if ((Variables.remove_page < Items.getCategoryItems().size() / 19 + 1) && (Items.getCategoryItems().size() % 19 != 0)) {
      inventory.setItem(34, next_item);
    }
    if (Variables.remove_page > 1) {
      inventory.setItem(28, previous_item);
    }
    inventory.setItem(49, remove_category);
    
    player.openInventory(inventory);
  }
  
  public static void manageCategory(Player player, String category_name, ItemStack head_item, ArrayList<String> items, String visibility)
  {
    Inventory inventory = Bukkit.createInventory(null, 54, "Manage category");
    ArrayList<String> lore = new ArrayList();
    if (category_name != null) {
      Variables.name_edit = category_name;
    }
    if (head_item != null) {
      Variables.head_edit = head_item;
    }
    if (visibility != null) {
      Variables.visibility_edit = visibility;
    }
    ItemStack edit_name = new ItemStack(Material.BOOK_AND_QUILL, 1);
    ItemStack edit_head = Variables.head_edit;
    ItemStack add_items = new ItemStack(Material.WOOL, 1, (short)13);
    ItemStack remove_items = new ItemStack(Material.WOOL, 1, (short)14);
    ItemStack edit_visibility = Variables.visibility_edit.contains("True") ? new ItemStack(Material.getMaterial(351), 1, (short)10) : new ItemStack(Material.getMaterial(351), 1, (short)8);
    ItemStack save = new ItemStack(Material.STAINED_GLASS, 1, (short)13);
    ItemStack cancel = new ItemStack(Material.STAINED_GLASS, 1, (short)14);
    
    ItemMeta edit_meta = edit_name.getItemMeta();
    ItemMeta head_meta = Variables.head_edit.getItemMeta();
    ItemMeta add_meta = add_items.getItemMeta();
    ItemMeta remove_meta = remove_items.getItemMeta();
    ItemMeta visibility_meta = edit_visibility.getItemMeta();
    ItemMeta save_meta = save.getItemMeta();
    ItemMeta cancel_meta = cancel.getItemMeta();
    
    edit_meta.setDisplayName(ChatColor.YELLOW + "Category's names");
    head_meta.setDisplayName(ChatColor.YELLOW + "Category's head");
    add_meta.setDisplayName(ChatColor.YELLOW + "Add items");
    remove_meta.setDisplayName(ChatColor.YELLOW + "Remove items");
    visibility_meta.setDisplayName(ChatColor.YELLOW + "Visibility");
    save_meta.setDisplayName(ChatColor.GREEN + "Save");
    cancel_meta.setDisplayName(ChatColor.RED + "Cancel");
    
    lore.add("");
    lore.add(ChatColor.GRAY + "Category's name: " + ChatColor.GOLD + Variables.name_edit);
    lore.add("");
    lore.add(ChatColor.GREEN + "Click to edit");
    edit_meta.setLore(lore);
    
    lore.clear();
    lore.add("");
    lore.add(ChatColor.GRAY + "Head item: " + ChatColor.GOLD + Variables.head_edit.getType().name() + " (" + Variables.head_edit.getTypeId() + ":" + Variables.head_edit.getData().getData() + ")");
    lore.add("");
    lore.add(ChatColor.GREEN + "Click to edit");
    head_meta.setLore(lore);
    
    lore.clear();
    lore.add("");
    lore.add(ChatColor.GRAY + "You have: " + ChatColor.GOLD + Variables.final_items.size() + " item" + (Variables.final_items.size() > 1 ? "s" : ""));
    lore.add("");
    lore.add(ChatColor.GREEN + "Click to add more items");
    add_meta.setLore(lore);
    
    lore.clear();
    lore.add("");
    lore.add(ChatColor.GRAY + "You have: " + ChatColor.GOLD + Variables.final_items.size() + " item" + (Variables.final_items.size() > 1 ? "s" : ""));
    lore.add("");
    lore.add(ChatColor.RED + "Click to delete items");
    remove_meta.setLore(lore);
    
    lore.clear();
    lore.add("");
    lore.add(ChatColor.GRAY + "Visibility: " + (Variables.visibility_edit.contains("True") ? ChatColor.GREEN + "Enabled" + ChatColor.GRAY + " (It is visible)" : new StringBuilder().append(ChatColor.RED).append("Disabled").append(ChatColor.GRAY).append(" (It is not visible)").toString()));
    lore.add("");
    lore.add(ChatColor.GREEN + (Variables.visibility_edit.contains("True") ? "Click to disable visibility" : "Click to enable visibility"));
    visibility_meta.setLore(lore);
    
    lore.clear();
    lore.add("");
    lore.add(ChatColor.GRAY + "Category's name: " + ChatColor.GOLD + Variables.name_edit);
    lore.add("");
    lore.add(ChatColor.GRAY + "Head item: " + ChatColor.GOLD + Variables.head_edit.getType().name() + " (" + Variables.head_edit.getTypeId() + ":" + Variables.head_edit.getData().getData() + ")");
    lore.add("");
    lore.add(ChatColor.GRAY + "Items: " + ChatColor.GOLD + Variables.final_items.size() + " item" + (Variables.final_items.size() > 1 ? "s" : ""));
    lore.add("");
    lore.add(ChatColor.GRAY + "Visibility: " + ChatColor.GOLD + Variables.visibility_edit);
    lore.add("");
    lore.add(ChatColor.RED + "You can't save changes. Add more items.");
    
    save_meta.setLore(lore);
    
    edit_name.setItemMeta(edit_meta);
    edit_head.setItemMeta(head_meta);
    add_items.setItemMeta(add_meta);
    remove_items.setItemMeta(remove_meta);
    edit_visibility.setItemMeta(visibility_meta);
    save.setItemMeta(save_meta);
    cancel.setItemMeta(cancel_meta);
    
    inventory.setItem(10, edit_name);
    inventory.setItem(12, edit_head);
    inventory.setItem(14, add_items);
    inventory.setItem(16, remove_items);
    inventory.setItem(31, edit_visibility);
    inventory.setItem(48, save);
    inventory.setItem(50, cancel);
    
    player.openInventory(inventory);
  }
}
