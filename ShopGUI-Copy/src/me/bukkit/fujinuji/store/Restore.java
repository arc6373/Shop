package me.bukkit.fujinuji.store;

import java.util.ArrayList;
import me.bukkit.fujinuji.gui.CategoryManager;

public class Restore
{
  public static void restoreCategoryManager()
  {
    CategoryManager.category_name = "";
    CategoryManager.head_item = null;
    if (CategoryManager.items != null) {
      CategoryManager.items.clear();
    }
  }
}
