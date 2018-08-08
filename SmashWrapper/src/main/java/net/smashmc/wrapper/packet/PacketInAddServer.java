package net.smashmc.wrapper.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.Channel;
import net.smashmc.cloud.network.packet.Packet;
import net.smashmc.wrapper.Wrapper;
import net.smashmc.wrapper.data.ServerData;

import java.io.IOException;

/**
 * @author Mike
 * @version 1.0
 */
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
        Wrapper.instance.getGameServer().startGameServer(new ServerData(name, template, port, ram));
        System.out.println(String.format("Group %s (%s) registered ", template, name));
        return null;
    }
}