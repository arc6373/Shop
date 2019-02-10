package me.bukkit.fujinuji.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.bukkit.fujinuji.ShopPlus;
import net.md_5.bungee.api.ChatColor;

public class SellGUI 
{
	private static ItemStack sell_item = new ItemStack(Material.DIAMOND_BLOCK, 1);
	private static ItemStack glass_pane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)8);
	private static ItemStack cancel_item = new ItemStack(Material.TNT, 1);
	
	public static void openGUI (Player player)
	{
		// Build items
		ItemMeta sellIM = sell_item.getItemMeta();
		sellIM.setDisplayName( ChatColor.GREEN + "Click to sell items!" );
		sell_item.setItemMeta(sellIM);
		
		ItemMeta glassIM = glass_pane.getItemMeta();
		glassIM.setDisplayName(" ");
		glass_pane.setItemMeta(glassIM);
		
		ItemMeta cancelIM = cancel_item.getItemMeta();
		cancelIM.setDisplayName("Click to cancel selling");
		cancel_item.setItemMeta(cancelIM);
		
		// Build GUI
		Inventory inv = Bukkit.createInventory(null, 54, "Sell Items");
		
		for (int i = 45; i < 54; i++)
		{
			inv.setItem(i, glass_pane);
		}
		
		inv.setItem(45, cancel_item);
		inv.setItem(49, sell_item);
		
		
		player.openInventory(inv);
		ShopPlus.sellInv.put(player, inv);
	}
}
