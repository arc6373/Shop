package me.bukkit.fujinuji.util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.material.MaterialData;

public class MaxItems
{
  public static int getMaxAmount(Player player, String code)
  {
    ItemStack item = GetCode.getItemStack(GetCode.getMinecraftCode(code));
    int max_items = 0;
    int max_stack = item.getMaxStackSize();
    int null_slots = 0;
    
    int index = 0;
    for (int item_index = 0; item_index < 36; item_index++)
    {
      ItemStack now = player.getInventory().getItem(item_index);
      if (now == null) {
        null_slots++;
      } else if ((now.getTypeId() == item.getTypeId()) && (now.getData().getData() == item.getData().getData())) {
        max_items += max_stack - now.getAmount();
      }
      index++;
    }
    max_items += null_slots * max_stack;
    
    return max_items;
  }
  
  public static int getPlayerAmount(String code, Player player)
  {
    int amount = 0;
    
    ItemStack verify_item = GetCode.getItemStack(GetCode.getMinecraftCode(code));
    ItemStack[] arrayOfItemStack;
    int j = (arrayOfItemStack = player.getInventory().getContents()).length;
    for (int i = 0; i < j; i++)
    {
      ItemStack item = arrayOfItemStack[i];
      if ((item != null) && 
        (verify_item.getType() == item.getType())) {
        amount += item.getAmount();
      }
    }
    return amount;
  }
}
