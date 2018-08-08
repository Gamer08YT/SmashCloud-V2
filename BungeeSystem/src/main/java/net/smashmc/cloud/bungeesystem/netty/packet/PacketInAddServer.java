package net.smashmc.cloud.bungeesystem.netty.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.Channel;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.smashmc.cloud.bungeesystem.BungeeSystem;
import net.smashmc.cloud.bungeesystem.handler.objects.Server;
import net.smashmc.cloud.network.packet.Packet;

import java.io.IOException;
import java.net.InetSocketAddress;

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
        ServerInfo info = ProxyServer.getInstance().constructServerInfo(name, new InetSocketAddress(port), name, false);
        ProxyServer.getInstance().getServers().put(name, info);
        BungeeSystem.instance.getServers().add(new Server(template, name, port));
        return null;
    }
}
