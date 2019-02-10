package me.bukkit.fujinuji.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractHandler implements Listener
{
	@EventHandler
	public void PlayerInteract(PlayerInteractEvent e)
	{
		Player player = e.getPlayer();
		
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			Block b = e.getClickedBlock();
			ItemStack is = player.getItemInHand();
			
			if (b.getType() == Material.OBSIDIAN && is.getType() == Material.BUCKET)
			{
				b.setType(Material.AIR);
				
				ItemStack lava = new ItemStack(Material.LAVA_BUCKET, 1);
				is.setAmount( is.getAmount() - 1 );
				
				player.setItemInHand(is);
				player.getInventory().addItem(lava);
			}
		}
	}
}
