package me.bukkit.fujinuji.util;

import java.util.Collection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;

public class Availability
{
  public static boolean playerCheck(Collection<? extends Player> playersOnline)
  {
    for (Player player : playersOnline) {
      if (checkInventory(player)) {
        return false;
      }
    }
    return true;
  }
  
  public static boolean checkInventory(Player player)
  {
    String inv = player.getOpenInventory().getTitle();
    
    return (inv.contains("Main Menu")) || 
      (inv.contains("Manage categories")) || 
      (inv.contains("Remove category")) || 
      (inv.contains("Create a new category")) || 
      (inv.contains("Choose item for category head")) || 
      (inv.contains("Select items for category")) || 
      (inv.contains("Manage category")) || 
      (inv.contains("Edit item for category's head")) || 
      (inv.contains("Add items")) || 
      (inv.contains("Remove items")) || 
      (inv.contains("Categories list")) || 
      (inv.contains("Category items")) || 
      (inv.contains("Set buy price")) || 
      (inv.contains("Set sell price")) || 
      (inv.contains("Set amount"));
  }
  
  public static boolean checkInventory1(Player player)
  {
    String inv = player.getOpenInventory().getTitle();
    
    return (inv.contains("Items")) || 
      (inv.contains("Categories"));
  }
}
