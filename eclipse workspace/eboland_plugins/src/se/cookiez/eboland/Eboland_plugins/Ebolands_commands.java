package se.cookiez.eboland.Eboland_plugins;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Ebolands_commands implements CommandExecutor {
	
	@SuppressWarnings("unused")
	private Eboland_plugins eboland_instance;
	
	public Ebolands_commands(Eboland_plugins plugin)
	{
		eboland_instance = plugin;
	}
	
	public boolean onCommand(CommandSender cs, Command cmd, String alias, String []args)
	{
		if(cmd.getName().equalsIgnoreCase("reg_chunk"))
			return reg_chunk(cs, cmd, alias, args);
		else if (cmd.getName().equalsIgnoreCase("here"))
			return here(cs, cmd, alias, args);
		
		return false;
	}
	
	private boolean reg_chunk(CommandSender cs, Command cmd, String alias, String []args)
	{
		if(!cs.isOp() || !cs.getName().equalsIgnoreCase("ahrbahr")) {
			cs.sendMessage("U haz n0 1337 p0wrz!!!1");
			return true;
		}
		if(args.length == 2) {	
			int cx, cz;		// Parse the argz
			if(alias.equalsIgnoreCase("reg_chunk_wc")) {	// treat as float worldcoords if wc is used
				int tx = (int) Math.floor(Double.parseDouble(args[0])),
					tz = (int) Math.floor(Double.parseDouble(args[1]));
				cx = tx>>4;
				cz = tz>>4;
			} else {	// otherwise we treat as integer chunkcoords
				cx = Integer.parseInt(args[0]);
				cz = Integer.parseInt(args[1]);
			}
			if(Bukkit.getWorld("world").regenerateChunk(cx, cz))
				cs.sendMessage("Regeneration Successful! :D");
			else
				cs.sendMessage("Regeneration Phailed! D:");
			
		} else if(args.length == 4) {
			int c1x, c2x, c1z, c2z;
			if(alias.equalsIgnoreCase("reg_chunk_wc")) {
				int tx = (int) Math.floor(Double.parseDouble(args[0])),
					tz = (int) Math.floor(Double.parseDouble(args[1])),
					t2x= (int) Math.floor(Double.parseDouble(args[2])),
					t2z= (int) Math.floor(Double.parseDouble(args[3]));
				c1x = tx>>4; c1z = tz>>4;
				c2x = t2x>>4;c2z = t2z>>4;
			} else {
				c1x = Integer.parseInt(args[0]);
				c1z = Integer.parseInt(args[1]);
				c2x = Integer.parseInt(args[2]);
				c2z = Integer.parseInt(args[3]);
			}
			if(c2x < c1x) { int tmp = c1x; c1x = c2x; c2x = tmp; }
			if(c2z < c1z) { int tmp = c1z; c1z = c2z; c2z = tmp; }
			
			World w = Bukkit.getWorld("world");
			
			for(int z=c1z; z<=c2z; ++z) {
				for(int x=c1x; x<=c2x; ++x) {
					if(!w.isChunkLoaded(x, z))
						w.loadChunk(x, z);
					if(w.regenerateChunk(x, z))
						cs.sendMessage("Chunk("+x+", "+z+") Regenerated! :D");
					else
						cs.sendMessage("Chunk("+x+", "+z+") Phailed! D:");
					//w.loadChunk(x, z);
					w.refreshChunk(x, z);
				}
			}
		} else
			return false;
		
		return true;
	}
	
	public boolean here(CommandSender cs, Command cmd, String alias, String []args)
	{
		Player p = cs.getServer().getPlayer(cs.getName());
		Location pl = p.getLocation();
		p.chat("Jag är i \"" + ChatColor.LIGHT_PURPLE + p.getWorld().getName() + ChatColor.RESET + "\" på koordinaterna\n" +
													"x: " + ChatColor.YELLOW + pl.getX() + ChatColor.RESET + "\n" +
													"y: " + ChatColor.YELLOW + pl.getY() + ChatColor.RESET + "\n" +
													"z: " + ChatColor.YELLOW + pl.getZ() );
		return true;
	}
	

}