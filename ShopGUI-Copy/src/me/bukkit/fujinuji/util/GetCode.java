package me.bukkit.fujinuji.util;

import org.bukkit.inventory.ItemStack;

public class GetCode
{
  public static ItemStack getItemStack(String code)
  {
    if (!haveTwoPoints(code)) {
      return new ItemStack(Integer.parseInt(code), 1);
    }
    String type = "";String data = "";
    int index = 0;
    while (code.charAt(index) != ':')
    {
      type = type + code.charAt(index);
      index++;
    }
    index++;
    while (index < code.length())
    {
      data = data + code.charAt(index);
      index++;
    }
    return new ItemStack(Integer.parseInt(type), 1, (byte)Integer.parseInt(data));
  }
  
  static boolean haveTwoPoints(String str)
  {
    return str.contains(":");
  }
  
  public static String getMinecraftCode(String ymlCode)
  {
    StringBuilder result = new StringBuilder();
    int index = 0;
    result.append("");
    while (index < ymlCode.length())
    {
      if (((ymlCode.charAt(index) >= '0') && (ymlCode.charAt(index) <= '9')) || (ymlCode.charAt(index) == '/')) {
        if (ymlCode.charAt(index) == '/') {
          result.append(':');
        } else {
          result.append(ymlCode.charAt(index));
        }
      }
      index++;
    }
    return result.toString();
  }
  
  public static ItemStack getStackPlayer(String inCode, int amount)
  {
    String code = getMinecraftCode(inCode);
    
    String type = "";String data = "";
    int index = 0;
    while (code.charAt(index) != ':')
    {
      type = type + code.charAt(index);
      index++;
    }
    index++;
    while (index < code.length())
    {
      data = data + code.charAt(index);
      index++;
    }
    return new ItemStack(Integer.parseInt(type), amount, (byte)Integer.parseInt(data));
  }
}
