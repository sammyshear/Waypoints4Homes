package dev.sshear.way4homes.bukkit;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;

public class ClientboundSetHomePacket implements Packet<ClientboundSetHomePacket>, PacketListener {

	private Home home;

	public ClientboundSetHomePacket() {

	}

	public ClientboundSetHomePacket(int x, int y, int z, String name) {
		home = new Home(x, y, z, name);
	}


	@Override
	public void write(FriendlyByteBuf buf) {
		buf.writeInt(home.getX());
		buf.writeInt(home.getY());
		buf.writeInt(home.getZ());
		buf.writeUtf(home.getName());
	}

	@Override
	public void handle(ClientboundSetHomePacket listener) {
		listener.handleHome(this);
	}

	void handleHome(ClientboundSetHomePacket packet) {

	}

	@Override
	public void onDisconnect(Component reason) {

	}

	@Override
	public boolean isAcceptingMessages() {
		return false;
	}
}
