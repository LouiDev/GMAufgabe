package de.louidev.main;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	public static Main plugin;
	
	public void onEnable() {
		
		plugin = this;
		
		getCommand("seriennummer").setExecutor(new CommandListener());
		
		Timer.startTimer();
		
	}
	
	public void onDisable() {
		Timer.endTimer();
	}
	
	public static Main getPlugin() {
		return plugin;
	}
	
}
