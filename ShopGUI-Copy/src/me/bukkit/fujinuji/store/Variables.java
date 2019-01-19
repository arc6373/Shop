package me.bukkit.fujinuji.store;

import java.util.ArrayList;
import java.util.Map;
import org.bukkit.inventory.ItemStack;

public class Variables
{
  public static int page;
  public static int last;
  public static int category_page;
  public static int remove_page;
  public static int adder_page;
  public static int last_category_index;
  public static int remove_index;
  public static int adder_index;
  public static ArrayList<String> items = new ArrayList();
  public static ArrayList<String> available_items = null;
  public static String name_edit;
  public static ItemStack head_edit;
  public static ArrayList<String> items_edit;
  public static String visibility_edit;
  public static ArrayList<String> available_category_items;
  public static ArrayList<String> final_items;
  public static double price;
  public static String action;
  public static String item_code;
  public static String category_name;
  public static int amount;
  public static int temp_amount;
  public static Map<String, Integer> player_page_index;
  public static Map<String, Integer> player_last_index;
  public static Map<String, Integer> player_page_list;
  public static Map<String, Integer> player_last_list;
  public static Map<String, String> player_category;
}
