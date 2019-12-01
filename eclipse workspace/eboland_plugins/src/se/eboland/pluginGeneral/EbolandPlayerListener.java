package se.eboland.pluginGeneral;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
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
	
	private String timestampToInterval(long t)
	{
		t /= 1000;
		
		long d = t/(60*60*24);
		t %= (60*60*24);
		long h = t/(60*60);
		t %= (60*60);
		long m = t/60;
		t %= 60;
		long s = t;
		
		String message = "nyss";

		if(d == 1)
			message = "en dag";
		else if(d != 0)
			message = d + " dagar";
		else if(h != 0)
			message = h + "h " + m + "m " + s + "s";
		else if(m != 0)
			message = m + "m " + s + "s";
		else if(s != 0)
			message = s + "s";
		
		return message;
	}
	
	@EventHandler
	public void valkommen(PlayerJoinEvent e)
	{
		Player p = e.getPlayer();

		long lastPlayed = p.getLastPlayed();
		long now = System.currentTimeMillis(); 
		
		p.sendMessage("Välkommen " + ChatColor.AQUA + p.getName() + ChatColor.RESET + "!");
		
		
		OfflinePlayer[] ops = p.getServer().getOfflinePlayers();
		if(ops.length != 0)
		{
			List<OfflinePlayer> opList = new ArrayList<>();
			for(OfflinePlayer op : ops)
			{
				if(op.getLastPlayed() != 0)
					opList.add(op);
			}
			
			opList.sort((b,a) -> a.getLastPlayed() < b.getLastPlayed() ? -1 : a.getLastPlayed() == b.getLastPlayed() ? 0 : 1);
			
			
			p.sendMessage("Senast inloggade spelare:");
			
			for(int i = Math.min(6, opList.size()-1); i >= 0; i--)
			{
				OfflinePlayer op = opList.get(i);
				p.sendMessage("   " + ChatColor.GREEN + op.getName() + ChatColor.RESET + " loggade in för " + timestampToInterval(now-op.getLastPlayed()) + " sedan");
				//p.sendMessage("   " + op.getName() + " " + op.getFirstPlayed());
			}
		}
		
		
		//p.sendMessage("Ditt senaste besök var för " + timestampToInterval(now-lastPlayed) + " sedan");
		
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
