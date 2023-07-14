package dev.sshear.way4homes.fabric;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;
import xaero.common.XaeroMinimapSession;
import xaero.common.minimap.waypoints.Waypoint;
import xaero.common.minimap.waypoints.WaypointSet;
import xaero.minimap.XaeroMinimap;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Way4HomesClient implements ClientModInitializer {

	Map<String, Home> homes = new HashMap<>();
	Map<String, Home> delHomes = new HashMap<>();

	@Override
	public void onInitializeClient() {
		Way4Homes.LOGGER.info("Init for Way4Homes on this client");
		Way4Homes.LOGGER.info(String.valueOf(ClientPlayNetworking.registerGlobalReceiver(new Identifier("way4homes", "packet"),
				(client, handler, buf, resSender) -> {
					Way4Homes.LOGGER.debug("received packet with home data " + buf);
					ByteArrayDataInput in = ByteStreams.newDataInput(buf.getWrittenBytes());
					Home home;
					if (in.readUTF().equals("NEW HOME")) {
						home = new Home(
								in.readInt(), in.readInt(), in.readInt(),
								in.readUTF());
						client.execute(() -> {
							homes.put(home.getName(), home);
							syncWaypoints();
						});
					} else if (in.readUTF().equals("DEL HOME")) {
						home = new Home(in.readInt(), in.readInt(), in.readInt(), in.readUTF());
						client.execute(() -> {
							homes.remove(home.getName());
							delHomes.put(home.getName(), home);
							syncDelWaypoints();
						});
					}
				}
		)));
	}

	private void syncDelWaypoints() {
		for (Home home : delHomes.values()) {
			XaeroMinimapSession session = XaeroMinimapSession.getCurrentSession();
			var mgr = session.getWaypointsManager();
			WaypointSet set = mgr.getCurrentWorld().getCurrentSet();
			set.getList().removeIf((way) -> way.getName().equals(home.getName()));
			try {
				XaeroMinimap.instance.getSettings().saveAllWaypoints(mgr);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	void syncWaypoints() {

		for (Home home : homes.values()) {
			XaeroMinimapSession session = XaeroMinimapSession.getCurrentSession();
			var mgr = session.getWaypointsManager();
			WaypointSet set = mgr.getCurrentWorld().getCurrentSet();
			set.getList().removeIf((way) -> way.getName().equals(home.getName()));
			var waypoint =  new Waypoint(home.getX(), home.getY(), home.getZ(), home.getName(),
					home.getName().toUpperCase().substring(0, 0), 4, 0, false);
			set.getList().add(waypoint);
			try {
				XaeroMinimap.instance.getSettings().saveAllWaypoints(mgr);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
