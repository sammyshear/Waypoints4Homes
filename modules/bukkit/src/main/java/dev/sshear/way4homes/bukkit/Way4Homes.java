package dev.sshear.way4homes.bukkit;

import dev.sshear.way4homes.bukkit.essentials.EssentialsListener;
import dev.sshear.way4homes.bukkit.huskhomes.HuskHomesListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Way4Homes extends JavaPlugin implements Listener {

	public final Logger LOGGER = getLogger();

	@Override
	public void onEnable() {
		// Plugin startup logic
		LOGGER.info("Starting up Way4Homes Bukkit");
		this.saveDefaultConfig();
		FileConfiguration config = this.getConfig();
		if (getServer().getPluginManager().getPlugin("HuskHomes") != null) {
			config.addDefault("HuskHomes", true);
			if (getConfig().getBoolean("HuskHomes"))
				LOGGER.info("Way4Homes setup detected HuskHomes, enabling in config");
		} else {
			config.addDefault("HuskHomes", false);
		}
		if (getServer().getPluginManager().getPlugin("EssentialsX") != null) {
			config.addDefault("EssentialsX", true);
			if (getConfig().getBoolean("EssentialsX"))
				LOGGER.info("Way4Homes setup detected Essentials, enabling in config");
		} else {
			config.addDefault("EssentialsX", false);
		}
		config.options().copyDefaults(true);
		saveConfig();
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "way4homes:packet");
		if (this.getConfig().getBoolean("EssentialsX") &&
						!this.getConfig().getBoolean("HuskHomes")) {
			LOGGER.info("hooking into essentialsx");
			getServer().getPluginManager().registerEvents(new EssentialsListener(), this);
		} else if (this.getConfig().getBoolean("HuskHomes")) {
			LOGGER.info("hooking into huskhomes");
			getServer().getPluginManager().registerEvents(new HuskHomesListener(), this);
		}
	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
	}

}
