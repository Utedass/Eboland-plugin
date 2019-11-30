package se.cookiez.eboland.Eboland_plugins;

import org.bukkit.event.*;
import org.bukkit.event.enchantment.*;


public class Ebolands_enchantlistener implements Listener{
	
	@EventHandler
	public void fixa_enchantment_level(PrepareItemEnchantEvent e)
	{
		char max_level_table[] = {8, 9, 11, 12, 14, 15, 17, 18, 20, 21, 23, 24, 26, 27, 29, 30};
		int shelves = Math.min(e.getEnchantmentBonus(), 15);
		
		/*
		e.getExpLevelCostsOffered()[0] = (int)(Math.random()*(max_level_table[shelves]-2)+1);
		if (e.getEnchanter().getLevel() < max_level_table[shelves])
			e.getExpLevelCostsOffered()[1] = Math.max(e.getEnchanter().getLevel(), 1);
		e.getExpLevelCostsOffered()[2] = max_level_table[shelves];
		*/
		
		e.getOffers()[0].setCost((int)(Math.random()*(max_level_table[shelves]-2)+1));
		
		if (e.getEnchanter().getLevel() < max_level_table[shelves])
			e.getOffers()[1].setCost(Math.max(e.getEnchanter().getLevel(), 1));
		e.getOffers()[2].setCost(max_level_table[shelves]);
			
	}

}
