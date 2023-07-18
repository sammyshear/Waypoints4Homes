package dev.sshear.way4homes.fabric.jmap;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import dev.sshear.way4homes.fabric.Home;
import dev.sshear.way4homes.fabric.Way4Homes;
import journeymap.client.api.IClientAPI;
import journeymap.client.api.IClientPlugin;
import journeymap.client.api.display.Waypoint;
import journeymap.client.api.event.ClientEvent;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class JmapPlugin implements IClientPlugin {

	private IClientAPI jmApi;

	Map<String, Home> homes = new HashMap<>();
	Map<String, Home> delHomes = new HashMap<>();

	Map<String, Waypoint> waypointMap = new HashMap<>();


	@Override
	public void initialize(IClientAPI jmClientApi) {
		this.jmApi = jmClientApi;
		this.jmApi.subscribe(Way4Homes.MOD_ID, EnumSet.of(ClientEvent.Type.MAPPING_STARTED));
	}

	@Override
	public String getModId() {
		return Way4Homes.MOD_ID;
	}

	@Override
	public void onEvent(ClientEvent event) {
		if (event.type == ClientEvent.Type.MAPPING_STARTED) {
			ClientPlayNetworking.registerGlobalReceiver(new Identifier("way4homes", "packet"),
					(client, handler, buf, resSender) -> {
						ByteArrayDataInput in = ByteStreams.newDataInput(buf.getWrittenBytes());
						Home home;
						if (in.readUTF().equals("NEW HOME")) {
							home = new Home(in.readInt(), in.readInt(), in.readInt(), in.readUTF());
							client.execute(() -> {
								homes.put(home.getName(), home);
								syncWaypoints();
							});
						} else {
							home = new Home(in.readInt(), in.readInt(), in.readInt(), in.readUTF());
							client.execute(() -> {
								homes.remove(home.getName());
								delHomes.put(home.getName(), home);
								syncDelWaypoints();
							});
						}
					});
		}
	}

	private void syncDelWaypoints() {
		for (Home home : delHomes.values()) {
			Waypoint waypoint = waypointMap.get(home.getName());
			try
			{
				jmApi.remove(waypoint);
			}
			catch (Throwable t)
			{
				Way4Homes.LOGGER.error(t.getMessage(), t);
			}
		}
	}

	private void syncWaypoints() {
		for (Home home : homes.values()) {
			Waypoint waypoint;
			try
			{
				waypoint = new Waypoint(Way4Homes.MOD_ID, home.getName(), home.getName(),
						MinecraftClient.getInstance().world.getRegistryKey(),
						new BlockPos(home.getX(), home.getY(), home.getZ()));
				waypointMap.put(home.getName(), waypoint);
				jmApi.show(waypoint);
			}
			catch (Throwable t)
			{
				Way4Homes.LOGGER.error(t.getMessage(), t);
			}
		}
	}
}
