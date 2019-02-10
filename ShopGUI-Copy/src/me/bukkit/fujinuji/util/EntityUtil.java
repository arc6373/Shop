package me.bukkit.fujinuji.util;

import java.util.HashMap;
import java.util.Map;

public class EntityUtil 
{
	public static Map<String, Integer> entityIDS = createMap();
	
	private static Map<String, Integer> createMap()
	{
		Map<String, Integer> myMap = new HashMap<String, Integer>();
		
		
		// Passive Mobs
		myMap.put("Pig", 90);
		myMap.put("Sheep", 91);
		myMap.put("Cow", 92);
		myMap.put("Chicken", 93);
		myMap.put("Squid", 94);
		myMap.put("Wolf", 95);
		myMap.put("Mooshroom", 96);
		myMap.put("SnowGolem", 97);
		myMap.put("Ocelot", 98);
		myMap.put("IronGolem", 99);
		myMap.put("Horse", 100);
		myMap.put("Rabbit", 101);
		myMap.put("Villager", 120);
		
		// Aggressive mobs
		myMap.put("Wither", 64);
		myMap.put("Bat", 65);
		myMap.put("Witch", 66);
		myMap.put("Endermite", 67);
		myMap.put("Guardian", 68);
		myMap.put("Creeper", 50);
		myMap.put("Skeleton", 51);
		myMap.put("Spider", 52);
		myMap.put("Giant", 53);
		myMap.put("Zombie", 54);
		myMap.put("Slime", 55);
		myMap.put("Ghast", 56);
		myMap.put("ZombiePigman", 57);
		myMap.put("Enderman", 58);
		myMap.put("CaveSpider", 59);
		myMap.put("SilverFish", 60);
		myMap.put("Blaze", 61);
		myMap.put("MagmaCube", 62);
		myMap.put("EnderDragon", 63);
		
		return myMap;
	}
}