package dev.sshear.way4homes.bukkit;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.essentialsx.api.v2.events.HomeModifyEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Way4Homes extends JavaPlugin implements Listener {

	final Logger LOGGER = getLogger();

	@Override
	public void onEnable() {
		// Plugin startup logic
		LOGGER.info("Starting up Way4Homes Bukkit");
		getServer().getPluginManager().registerEvents(this, this);
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "way4homes:packet");
		for (String channel : Bukkit.getMessenger().getOutgoingChannels(this)) {
			LOGGER.info(channel);
		}
	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
	}

	@EventHandler
	public void modifyHomeEvent(HomeModifyEvent event) {
		var owner = event.getHomeOwner();
		var newLoc = event.getNewLocation();
		var name = event.getNewName();
		if (newLoc != null) {
			var home = new Home(newLoc.getBlockX(), newLoc.getBlockY(), newLoc.getBlockZ(), name);
			ByteArrayDataOutput out = ByteStreams.newDataOutput();
			out.writeUTF("NEW HOME");
			out.writeInt(home.getX());
			out.writeInt(home.getY());
			out.writeInt(home.getZ());
			out.writeUTF(home.getName());
			out.writeInt(home.getName().length());
			owner.getBase().sendPluginMessage(this, "way4homes:packet", out.toByteArray());
			LOGGER.info("sent packet with " + owner + " " + newLoc + " " + name);
		} else {
			var oldLoc = event.getOldLocation();
			var home = new Home(oldLoc.getBlockX(), oldLoc.getBlockY(), oldLoc.getBlockZ(), name);
			ByteArrayDataOutput out = ByteStreams.newDataOutput();
			out.writeUTF("DEL HOME");
			out.writeInt(home.getX());
			out.writeInt(home.getY());
			out.writeInt(home.getZ());
			out.writeUTF(home.getName());
			out.writeInt(home.getName().length());
			owner.getBase().sendPluginMessage(this, "way4homes:packet", out.toByteArray());
			LOGGER.info("sent delhome packet for " + name);
		}
	}
}
