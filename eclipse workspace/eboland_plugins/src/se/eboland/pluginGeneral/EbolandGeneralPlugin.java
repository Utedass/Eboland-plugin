package se.eboland.pluginGeneral;

import org.bukkit.plugin.java.JavaPlugin;

public final class EbolandGeneralPlugin extends JavaPlugin {
	
	@Override
	public void onEnable()
	{
		getLogger().info("Ebolands egna plugins aktiveras. Kompilerad med spigot-1.14.4");
		
		this.getServer().getPluginManager().registerEvents(new EbolandPlayerListener(), this);
		this.getServer().getPluginManager().registerEvents(new EbolandBlockListener(), this);
		this.getServer().getPluginManager().registerEvents(new EbolandKatastrofskydd(), this);
		this.getServer().getPluginManager().registerEvents(new EbolandEnchantListener(), this);
		
		EbolandCommands ec = new EbolandCommands(this);
		getCommand("reg_chunk").setExecutor(ec);
		getCommand("here").setExecutor(ec);
	}
	
	@Override
	public void onDisable()
	{
		getLogger().info("Ebolands egna plugins avaktiveras");
	}

}
