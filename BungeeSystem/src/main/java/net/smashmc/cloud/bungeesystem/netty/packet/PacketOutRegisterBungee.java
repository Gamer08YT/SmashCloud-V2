package net.smashmc.cloud.bungeesystem.netty.packet;

import io.netty.buffer.ByteBufOutputStream;
import net.smashmc.cloud.network.packet.Packet;

import java.io.IOException;

public class PacketOutRegisterBungee implements Packet {

    private String name;

    /**
     * Constructor of PacketAddServer
     *
     * @param name Wrapper name which you want to register
     */
    public PacketOutRegisterBungee(String name) {
        this.name = name;
    }

    /**
     * Constructor of PacketAddServer
     */
    public PacketOutRegisterBungee() {
    }

    /**
     * Write the Packet to the registered Clients(Wrapper/proxys)
     *
     * @param byteBuf write the outgoing information
     * @throws IOException
     */
    @Override
    public void write(ByteBufOutputStream byteBuf) throws IOException {
        byteBuf.writeUTF(name);
        byteBuf.writeInt(Type.BUNGEECORD.ordinal());
    }

    /**
     * Type which you want to registered
     */
    public enum Type {
        BUNGEECORD, WRAPPER, API;

    }
}