package me.bukkit.fujinuji.store;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Buttons
  extends JavaPlugin
{
  public static enum Action
  {
    ADD,  REMOVE,  SET;
  }
  
  public static final ItemStack ADD_1 = setStack(Action.ADD, 1);
  public static final ItemStack ADD_4 = setStack(Action.ADD, 4);
  public static final ItemStack ADD_8 = setStack(Action.ADD, 8);
  public static final ItemStack ADD_16 = setStack(Action.ADD, 16);
  public static final ItemStack ADD_32 = setStack(Action.ADD, 32);
  public static final ItemStack ADD_10 = setStack(Action.ADD, 10);
  public static final ItemStack ADD_50 = setStack(Action.ADD, 50);
  public static final ItemStack ADD_100 = setStack(Action.ADD, 100);
  public static final ItemStack ADD_1000 = setStack(Action.ADD, 1000);
  public static final ItemStack REMOVE_1 = setStack(Action.REMOVE, 1);
  public static final ItemStack REMOVE_4 = setStack(Action.REMOVE, 4);
  public static final ItemStack REMOVE_8 = setStack(Action.REMOVE, 8);
  public static final ItemStack REMOVE_16 = setStack(Action.REMOVE, 16);
  public static final ItemStack REMOVE_32 = setStack(Action.REMOVE, 32);
  public static final ItemStack REMOVE_10 = setStack(Action.REMOVE, 10);
  public static final ItemStack REMOVE_50 = setStack(Action.REMOVE, 50);
  public static final ItemStack REMOVE_100 = setStack(Action.REMOVE, 100);
  public static final ItemStack REMOVE_1000 = setStack(Action.REMOVE, 1000);
  
  private static ItemStack setStack(Action action, int price)
  {
    ItemStack item = null;
    if (action == Action.ADD)
    {
      item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)13);
      
      ItemMeta meta = item.getItemMeta();
      meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Add " + price);
      item.setItemMeta(meta);
      
      return item;
    }
    if (action == Action.REMOVE)
    {
      item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)14);
      
      ItemMeta meta = item.getItemMeta();
      meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Remove " + price);
      item.setItemMeta(meta);
      
      return item;
    }
    return item;
  }
}
