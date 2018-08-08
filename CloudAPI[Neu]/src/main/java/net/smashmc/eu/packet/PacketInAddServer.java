package net.smashmc.eu.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.Channel;
import net.smashmc.cloud.network.packet.Packet;
import net.smashmc.eu.CloudAPI;
import net.smashmc.eu.utils.Server;

import java.io.IOException;

public class PacketInAddServer implements Packet {

    private String name;
    private int port;
    private int ram;
    private String template;

    /**
     * Constructor of PacketAddServer
     */
    public PacketInAddServer() {
    }

    /**
     * Read the Incoming Packet
     *
     * @param byteBuf read the incoming information
     * @throws IOException
     */
    @Override
    public void read(ByteBufInputStream byteBuf) throws IOException {
        System.out.println("packet was read");
        this.name = byteBuf.readUTF();
        this.template = byteBuf.readUTF();
        this.ram = byteBuf.readInt();
        this.port = byteBuf.readInt();

    }

    /**
     * Handle the incoming information
     *
     * @param channel connected channel
     * @return
     */
    @Override
    public Packet handle(Channel channel) {
        Server server = new Server(this.name, this.template, this.ram, this.port, 0, 0, "");
        System.out.print( "1" );
        CloudAPI.instance.getServers().add(server);
        return null;
    }
}
