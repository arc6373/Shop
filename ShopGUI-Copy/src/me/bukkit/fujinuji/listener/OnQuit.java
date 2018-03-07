package me.bukkit.fujinuji.listener;

import java.util.Map;
import me.bukkit.fujinuji.store.Variables;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnQuit
  implements Listener
{
  @EventHandler(priority=EventPriority.NORMAL)
  public void PlayerQuitEvent(PlayerQuitEvent event)
  {
    Player player = event.getPlayer();
    
    Variables.player_category.remove(player.getName());
    Variables.player_last_index.remove(player.getName());
    Variables.player_last_list.remove(player.getName());
    Variables.player_page_index.remove(player.getName());
    Variables.player_page_list.remove(player.getName());
  }
}
