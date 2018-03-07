package me.bukkit.fujinuji.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Info
{
  public void sendInfo(Player player)
  {
    if (((player.hasPermission("shopplus.admin")) || (player.hasPermission("shopplus.*")) || (player.isOp()) || (player.hasPermission("*"))) && (
      (player.hasPermission("shopplus.shop")) || (player.hasPermission("shopplus.*")) || (player.isOp()) || (player.hasPermission("*"))))
    {
      player.sendMessage(ChatColor.GRAY + "----------" + ChatColor.GREEN + "ShopPlus" + ChatColor.GRAY + "----------");
      player.sendMessage(ChatColor.GOLD + "/shop : " + ChatColor.WHITE + "Open shop");
      player.sendMessage(ChatColor.GOLD + "/shop admin : " + ChatColor.WHITE + "Manage shop configuration");
      player.sendMessage(ChatColor.GRAY + "----------------------------");
    }
    else if (((!player.hasPermission("shopplus.admin")) && (!player.hasPermission("shopplus.*")) && (!player.isOp()) && (!player.hasPermission("*"))) || 
      ((!player.hasPermission("shopplus.shop")) && (!player.hasPermission("shopplus.*")) && (!player.isOp())) || (player.hasPermission("*")))
    {
      player.sendMessage(ChatColor.GRAY + "----------" + ChatColor.GREEN + "ShopPlus" + ChatColor.GRAY + "----------");
      player.sendMessage(ChatColor.GOLD + "/shop admin : " + ChatColor.WHITE + "Manage shop configuration");
      player.sendMessage(ChatColor.GRAY + "----------------------------");
    }
    else if (((!player.hasPermission("shopplus.admin")) && (!player.hasPermission("shopplus.*")) && (!player.isOp()) && (!player.hasPermission("*")) && (
      (player.hasPermission("shopplus.shop")) || (player.hasPermission("shopplus.*")) || (player.isOp()))) || (player.hasPermission("*")))
    {
      player.sendMessage(ChatColor.GRAY + "----------" + ChatColor.GREEN + "ShopPlus" + ChatColor.GRAY + "----------");
      player.sendMessage(ChatColor.GOLD + "/shop : " + ChatColor.WHITE + "Open shop");
      player.sendMessage(ChatColor.GRAY + "----------------------------");
    }
    else
    {
      player.sendMessage(ChatColor.GRAY + "----------" + ChatColor.GREEN + "ShopPlus" + ChatColor.GRAY + "----------");
      player.sendMessage(ChatColor.RED + "You have not any permission for this plugin");
      player.sendMessage(ChatColor.GRAY + "----------------------------");
    }
  }
}
