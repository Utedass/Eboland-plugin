package se.cookiez.eboland.Eboland_plugins;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.inventory.ItemStack;

public class Ebolands_blocklistener implements Listener {
	
	@EventHandler
	public void droppa_svamp(BlockBreakEvent e) {
		if(	e.getBlock().getType() == Material.MYCELIUM &&
			e.getPlayer().getInventory().getItemInMainHand().getType() == Material.DIAMOND_HOE) {
			e.getBlock().setType(Material.AIR);
			e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.SPONGE));
		}	
	}
}


class Ebolands_katastrofskydd implements Listener {
	
	@EventHandler
	public void no_ghasts(EntityPortalEnterEvent e) {
		if(	e.getEntityType() == EntityType.GHAST) {
			e.getEntity().remove();
		}	
	}
}