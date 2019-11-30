package se.eboland.pluginGeneral;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.event.block.*;

public class EbolandPlayerListener implements Listener
{

	@EventHandler
	public void dags_att_sova(PlayerBedEnterEvent e) {
		Bukkit.getServer().broadcastMessage(ChatColor.AQUA + e.getPlayer().getName() + ChatColor.YELLOW + " har lagt sig i en säng och vill sova!");
	}
	
	@EventHandler
	public void dags_att_svika(PlayerBedLeaveEvent e) {
		if (e.getPlayer().getWorld().getTime() != 0)
			Bukkit.getServer().broadcastMessage(ChatColor.AQUA + e.getPlayer().getName() + ChatColor.DARK_RED + " har gått ur sängen, vilken " + ChatColor.UNDERLINE + "SVIKARE" + ChatColor.RESET + ChatColor.DARK_RED + "!");
	}
	
	@EventHandler
	public void valkommen(PlayerJoinEvent e)
	{
		e.getPlayer().sendMessage("Välkommen " + ChatColor.AQUA + e.getPlayer().getName() + ChatColor.RESET + "!");
	}
	
	@EventHandler
	public void spara_ny_sang(PlayerInteractEvent e)
	{
		//Update the spawn location even if it is night
		if (e.hasBlock() && e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == org.bukkit.Material.LEGACY_BED_BLOCK)
		{			
			byte bed_data = e.getClickedBlock().getData();
			Location pillow = e.getClickedBlock().getLocation();

			// Set 'pillow' to the pillows position
			if ((bed_data & 8) == 0)
			{
				switch (bed_data&3)
				{
				case 0:	pillow.add( 0, 0, 1);	break;
				case 1:	pillow.add(-1, 0, 0);	break;
				case 2:	pillow.add( 0, 0,-1);	break;
				case 3:	pillow.add( 1, 0, 0);	break;
				}
			}
			
			
			// Only update the position if it is new, and tell the player
			if (e.getPlayer().getWorld().getTime() < 12540)
			{
				Location lastbed = e.getPlayer().getBedSpawnLocation();
				e.getPlayer().setBedSpawnLocation(pillow);
				
				if (e.getPlayer().getBedSpawnLocation() == null)
				{
					e.getPlayer().sendMessage(ChatColor.RED + "Slöseri på en till synes bekväm säng, den är ju blockerad åt alla håll! " 
							+ ChatColor.WHITE + "\u2620" + ChatColor.GREEN + "\u2623" + ChatColor.YELLOW + "\u2622 \n"
							+ ChatColor.BLUE + "Hoppas att det är åtgärdat när du dör..");
				}
				else if (lastbed == null || e.getPlayer().getBedSpawnLocation() == null || lastbed.distanceSquared(e.getPlayer().getBedSpawnLocation()) != 0)
				{
					e.getPlayer().sendMessage(ChatColor.YELLOW + "Du har hittat dig en ny säng, en otrolig trygghet i svåra situationer! \u263A");	
				}
				else
				{
						e.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "Du ser din säng " + ChatColor.RED + "\u2665");
				}
				e.setCancelled(true);
			}
		}
	}

}
