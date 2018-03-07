package me.bukkit.fujinuji.listener;

import me.bukkit.fujinuji.gui.CategoryManager;
import me.bukkit.fujinuji.store.Items;
import me.bukkit.fujinuji.store.Variables;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatInput
  implements Listener
{
  public static Player player;
  public static boolean done = false;
  public static boolean edit_mode = false;
  private boolean eligibility = true;
  
  @EventHandler
  public void onPlayerChat(AsyncPlayerChatEvent e)
  {
    if ((e.getPlayer() == Inventrory.player) && (done))
    {
      e.setCancelled(true);
      
      this.eligibility = true;
      done = false;
      if (isCancel(e.getMessage().toLowerCase())) {
        this.eligibility = false;
      }
      if (Items.getCategoryItems() != null) {
        for (String s : Items.getCategoryItems()) {
          if ((s.toLowerCase().contains(e.getMessage().toLowerCase())) && (s.length() == e.getMessage().length()))
          {
            player.sendMessage(ChatColor.RED + "Your category already exists. Type a neww name for your category, CANCEL to cencel or RESET to reset category's name");
            done = true;
            this.eligibility = false;
          }
        }
      }
      if ((this.eligibility) && (!done))
      {
        CategoryManager.category_name = e.getMessage();
        if (!edit_mode) {
          CategoryManager.addCategory(player, e.getMessage(), CategoryManager.head_item, Variables.items);
        }
        if (edit_mode) {
          CategoryManager.manageCategory(player, e.getMessage(), null, null, null);
        }
      }
      if ((!this.eligibility) && (isCancel(e.getMessage().toLowerCase())))
      {
        if (!edit_mode) {
          CategoryManager.addCategory(player, CategoryManager.category_name, CategoryManager.head_item, Variables.items);
        }
        if (edit_mode) {
          CategoryManager.manageCategory(player, null, null, null, null);
        }
      }
    }
  }
  
  boolean isCancel(String s)
  {
    if (s.length() == 6)
    {
      if ((s.charAt(0) == 'c') && (s.charAt(1) == 'a') && (s.charAt(2) == 'n') && (s.charAt(3) == 'c') && (s.charAt(4) == 'e') && (s.charAt(5) == 'l')) {
        return true;
      }
      return false;
    }
    return false;
  }
}
