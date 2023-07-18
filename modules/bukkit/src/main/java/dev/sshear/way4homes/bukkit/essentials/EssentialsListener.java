package dev.sshear.way4homes.bukkit.essentials;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.sshear.way4homes.bukkit.Home;
import dev.sshear.way4homes.bukkit.Way4Homes;
import net.essentialsx.api.v2.events.HomeModifyEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EssentialsListener implements Listener {

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
			owner.getBase().sendPluginMessage(Way4Homes.getPlugin(Way4Homes.class),
					"way4homes:packet", out.toByteArray());
		} else {
			var oldLoc = event.getOldLocation();
			var home = new Home(oldLoc.getBlockX(), oldLoc.getBlockY(), oldLoc.getBlockZ(), event.getOldName());
			ByteArrayDataOutput out = ByteStreams.newDataOutput();
			out.writeUTF("DEL HOME");
			out.writeInt(home.getX());
			out.writeInt(home.getY());
			out.writeInt(home.getZ());
			out.writeUTF(home.getName());
			out.writeInt(home.getName().length());
			owner.getBase().sendPluginMessage(Way4Homes.getPlugin(Way4Homes.class),
					"way4homes:packet", out.toByteArray());
		}
	}
}
