package net.smashmc.eu.packet;

import io.netty.buffer.ByteBufOutputStream;
import net.smashmc.cloud.network.packet.Packet;

import java.io.IOException;

public class PacketOutRegisterAPI implements Packet {

    private final String name;

    public PacketOutRegisterAPI(String name) {
        this.name = name;
    }

    @Override
    public void write(ByteBufOutputStream byteBuf) throws IOException {
        byteBuf.writeUTF(name);
        byteBuf.writeInt(Type.API.ordinal());
        System.out.println("packet was send");
    }

    public enum Type {
        BUNGEECORD, WRAPPER, API;
    }
}
