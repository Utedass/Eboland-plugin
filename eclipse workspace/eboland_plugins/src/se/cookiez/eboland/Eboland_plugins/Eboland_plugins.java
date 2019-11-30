package se.cookiez.eboland.Eboland_plugins;

import org.bukkit.plugin.java.JavaPlugin;

public final class Eboland_plugins extends JavaPlugin {
	
	@Override
	public void onEnable()
	{
		getLogger().info("Ebolands egna plugins aktiveras. Kompilerad med spigot-1.14.4");
		
		this.getServer().getPluginManager().registerEvents(new Ebolands_playerlistener(), this);
		this.getServer().getPluginManager().registerEvents(new Ebolands_blocklistener(), this);
		this.getServer().getPluginManager().registerEvents(new Ebolands_katastrofskydd(), this);
		//this.getServer().getPluginManager().registerEvents(new Ebolands_enchantlistener(), this);
		
		Ebolands_commands ec = new Ebolands_commands(this);
		getCommand("reg_chunk").setExecutor(ec);
		getCommand("here").setExecutor(ec);
	}
	
	@Override
	public void onDisable()
	{
		getLogger().info("Ebolands egna plugins avaktiveras");
	}

}
