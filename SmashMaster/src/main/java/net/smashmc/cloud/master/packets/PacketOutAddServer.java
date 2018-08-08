package net.smashmc.cloud.master.packets;

import io.netty.buffer.ByteBufOutputStream;
import net.smashmc.cloud.master.Master;
import net.smashmc.cloud.master.utils.types.Server;
import net.smashmc.cloud.network.packet.Packet;

import java.io.IOException;

/**
 * @author Mike
 * @version 1.0
 */
public class PacketOutAddServer implements Packet {

    private Server server;

    /**
     * Constructor of PacketAddServer
     */
    public PacketOutAddServer() {
    }

    /**
     * Constructor of PacketAddServer
     *
     * @param server Server object which you want to register
     */
    public PacketOutAddServer(Server server) {
        this.server = server;
    }

    /**
     * Write the Packet to the registered Clients(Wrapper/proxys)
     *
     * @param byteBuf write the outgoing information
     * @throws IOException
     */
    @Override
    public void write(ByteBufOutputStream byteBuf) throws IOException {
        byteBuf.writeUTF(server.getName());
        byteBuf.writeUTF(server.getTemplate().getTemplate());
        byteBuf.writeInt(server.getMinRam());
        byteBuf.writeInt(server.getPort());
    }
}
