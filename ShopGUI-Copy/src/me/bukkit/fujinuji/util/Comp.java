package me.bukkit.fujinuji.util;

import java.util.Comparator;

public class Comp
  implements Comparator<String>
{
  public int compare(String arg0, String arg1)
  {
    String code_1 = arg0.substring(5, arg0.length());
    String code_2 = arg1.substring(5, arg1.length());
    
    String number_1 = "";
    String number_2 = "";
    
    int index = 0;
    while (code_1.charAt(index) != '/')
    {
      number_1 = number_1 + code_1.charAt(index);
      index++;
    }
    index = 0;
    while (code_2.charAt(index) != '/')
    {
      number_2 = number_2 + code_2.charAt(index);
      index++;
    }
    if (Integer.parseInt(number_1) < Integer.parseInt(number_2)) {
      return -1;
    }
    if (Integer.parseInt(number_1) == Integer.parseInt(number_2))
    {
      String data_1 = "";
      String data_2 = "";
      
      data_1 = code_1.substring(1 + number_1.length(), code_1.length());
      data_2 = code_2.substring(1 + number_2.length(), code_2.length());
      if (Integer.parseInt(data_1) < Integer.parseInt(data_2)) {
        return -1;
      }
      return 1;
    }
    return 1;
  }
}
