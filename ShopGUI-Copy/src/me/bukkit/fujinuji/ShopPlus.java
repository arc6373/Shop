package me.bukkit.fujinuji;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.bukkit.fujinuji.gui.MainMenu;
import me.bukkit.fujinuji.gui.PlayerGUI;
import me.bukkit.fujinuji.gui.SellGUI;
import me.bukkit.fujinuji.listener.ChatInput;
import me.bukkit.fujinuji.listener.Inventrory;
import me.bukkit.fujinuji.listener.OnQuit;
import me.bukkit.fujinuji.listener.PlayerInteractHandler;
import me.bukkit.fujinuji.store.Items;
import me.bukkit.fujinuji.store.Variables;
import me.bukkit.fujinuji.util.Availability;
import me.bukkit.fujinuji.util.ConfigureConfig;
import me.bukkit.fujinuji.util.GetArray;
import me.bukkit.fujinuji.util.Info;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ShopPlus
  extends JavaPlugin
{
  public static File path;
  protected static YamlConfiguration ShopItemsYml = new YamlConfiguration();
  protected static YamlConfiguration CategoryItemsYml = new YamlConfiguration();
  public static File ShopItemsFile = null;
  public static File CategoryFile = null;
  private final ShopPlus plugin = this;
  private static Economy econ;
  private static final Logger log = Logger.getLogger("Minecraft");
  private Info info = new Info();
  
  public static Map<Player, Inventory> sellInv;
  
  public void onEnable()
  {
	sellInv = new HashMap<Player, Inventory>();
	  
    getServer().getPluginManager().registerEvents(new Inventrory(), this);
    getServer().getPluginManager().registerEvents(new ChatInput(), this);
    getServer().getPluginManager().registerEvents(new OnQuit(), this);
    
    path = getDataFolder();
    
    initialization();
    try
    {
      getItems();
    }
    catch (FileNotFoundException e)
    {
      getLogger().log(Level.INFO, "Cant't find some files...");
    }
    Variables.player_page_index = new HashMap();
    Variables.player_last_index = new HashMap();
    Variables.player_page_list = new HashMap();
    Variables.player_last_list = new HashMap();
    Variables.player_category = new HashMap();
    
    Items.loadItems();
    if (!setupEconomy())
    {
      log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", new Object[] { getDescription().getName() }));
      getServer().getPluginManager().disablePlugin(this);
      return;
    }
  }
  
  public void onDisable()
  {
    for (Player player : getServer().getOnlinePlayers()) {
      if (Availability.checkInventory1(player)) {
        player.closeInventory();
      }
    }
    getServer().getLogger().info("All players have the inventory closed");
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if ((cmd.getName().equalsIgnoreCase("shop")) && ((sender instanceof Player)))
    {
      Player player = (Player)sender;
      if (args.length != 0)
      {
        if (args[0].equalsIgnoreCase("admin"))
        {
          if ((player.hasPermission("shopplus.admin")) || (player.hasPermission("shopplus.*")) || (player.isOp()) || (player.hasPermission("*")))
          {
            if (Availability.playerCheck(getServer().getOnlinePlayers()))
            {
              Variables.last = 0;
              MainMenu.open(player);
              return true;
            }
            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.GREEN + "ShopPlus" + ChatColor.GRAY + "] " + ChatColor.RED + "Another player already configure the shop. Please try later.");
          }
          else
          {
            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.GREEN + "ShopPlus" + ChatColor.GRAY + "] " + ChatColor.RED + "You have not permission to manage the shop");
            return true;
          }
        }
        else if (args[0].equalsIgnoreCase("help")) {
          this.info.sendInfo(player);
        } else {
          this.info.sendInfo(player);
        }
      }
      else if (args.length == 0)
      {
        if ((player.hasPermission("shopplus.shop")) || (player.hasPermission("shopplus.*")) || (player.isOp()) || (player.hasPermission("*")))
        {
          Variables.player_last_index.put(player.getName(), Integer.valueOf(0));
          Variables.player_page_index.put(player.getName(), Integer.valueOf(1));
          PlayerGUI.categorySelect(player);
          
          return true;
        }
        player.sendMessage(ChatColor.GRAY + "[" + ChatColor.GREEN + "ShopPlus" + ChatColor.GRAY + "] " + ChatColor.RED + "You have not permission to open the shop");
        return true;
      }
    }
    if (!(sender instanceof Player)) {
      sender.sendMessage("Only players cand acces shop!");
    }
    return true;
  }
  
  private void initialization()
  {
    ShopItemsFile = new File(path, "ShopItems.yml");
    mkdir("ShopItems.yml", ShopItemsFile);
    loadYmlShop(ShopItemsFile);
    
    CategoryFile = new File(path, "Category.yml");
    mkdir("Category.yml", CategoryFile);
    loadYmlCategory(CategoryFile);
  }
  
  private void mkdir(String name, File file)
  {
    if (!file.exists())
    {
      Bukkit.getLogger().info("Creating " + name + " file");
      saveResource(name, false);
    }
  }
  
  private void loadYmlShop(File file)
  {
    try
    {
      ShopItemsYml.load(file);
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    catch (InvalidConfigurationException e)
    {
      e.printStackTrace();
    }
  }
  
  private static void loadYmlCategory(File file)
  {
    try
    {
      CategoryItemsYml.load(file);
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    catch (InvalidConfigurationException e)
    {
      e.printStackTrace();
    }
  }
  
  private boolean setupEconomy()
  {
    if (getServer().getPluginManager().getPlugin("Vault") == null) {
      return false;
    }
    RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
    if (rsp == null) {
      return false;
    }
    econ = (Economy)rsp.getProvider();
    return econ != null;
  }
  
  private void getItems()
    throws FileNotFoundException
  {
    getLogger().log(Level.INFO, "Configure items... please wait");
    
    ArrayList<String> temp_items = new ArrayList();
    
    temp_items = GetArray.getArray(ShopItemsYml);
    ConfigureConfig.setUp(temp_items, getLogger());
    
    getLogger().log(Level.INFO, "Configuration complete");
  }
  
  public static Economy getEconomy()
  {
    return econ;
  }
}
