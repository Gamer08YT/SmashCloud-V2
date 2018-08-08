package net.smashmc.wrapper.packet;

import io.netty.buffer.ByteBufOutputStream;
import net.smashmc.cloud.network.packet.Packet;

import java.io.IOException;

public class PacketOutRegisterWrapper implements Packet {

    private String name;

    /**
     * Constructor of PacketAddServer
     *
     * @param name Wrapper name which you want to register
     */
    public PacketOutRegisterWrapper(String name) {
        this.name = name;
    }

    /**
     * Constructor of PacketAddServer
     */
    public PacketOutRegisterWrapper() {
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
        byteBuf.writeInt(Type.WRAPPER.ordinal());
    }

    /**
     * Type which you want to registered
     */
    public enum Type {
        BUNGEECORD, WRAPPER, API;
    }
}
