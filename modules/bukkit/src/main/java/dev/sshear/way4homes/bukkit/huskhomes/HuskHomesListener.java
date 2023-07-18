package dev.sshear.way4homes.bukkit.huskhomes;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.sshear.way4homes.bukkit.Way4Homes;
import net.william278.huskhomes.event.HomeCreateEvent;
import net.william278.huskhomes.event.HomeDeleteEvent;
import net.william278.huskhomes.event.HomeEditEvent;
import net.william278.huskhomes.user.BukkitUser;
import net.william278.huskhomes.user.OnlineUser;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class HuskHomesListener implements Listener {

	@EventHandler
	public void onModifyHome(HomeEditEvent event) {
		var home = event.getHome();
		var owner = home.getOwner();
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("NEW HOME");
		out.writeInt(((int) home.getX()));
		out.writeInt((int) home.getY());
		out.writeInt((int) home.getZ());
		out.writeUTF(home.getName());
		out.writeInt(home.getName().length());
		Bukkit.getPlayer(owner.getUuid()).sendPluginMessage(Way4Homes.getPlugin(Way4Homes.class),
				"way4homes:packet", out.toByteArray());
	}

	@EventHandler
	public void onCreateHome(HomeCreateEvent event) {
		var owner = event.getOwner();
		var loc = event.getPosition();
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("NEW HOME");
		out.writeInt(((int) loc.getX()));
		out.writeInt((int) loc.getY());
		out.writeInt((int) loc.getZ());
		out.writeUTF(event.getName());
		out.writeInt(event.getName().length());
		Bukkit.getPlayer(owner.getUuid()).sendPluginMessage(Way4Homes.getPlugin(Way4Homes.class),
				"way4homes:packet", out.toByteArray());
	}

	@EventHandler
	public void onDeleteHome(HomeDeleteEvent event) {
		var home = event.getHome();
		var owner = home.getOwner();
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("DEL HOME");
		out.writeInt(((int) home.getX()));
		out.writeInt((int) home.getY());
		out.writeInt((int) home.getZ());
		out.writeUTF(home.getName());
		out.writeInt(home.getName().length());
		Bukkit.getPlayer(owner.getUuid()).sendPluginMessage(Way4Homes.getPlugin(Way4Homes.class),
				"way4homes:packet", out.toByteArray());
	}
}
