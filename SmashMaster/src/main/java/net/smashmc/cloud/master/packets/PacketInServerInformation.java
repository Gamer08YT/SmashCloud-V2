package net.smashmc.cloud.master.packets;

import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.Channel;
import net.smashmc.cloud.master.Master;
import net.smashmc.cloud.network.packet.Packet;

import java.io.IOException;

public class PacketInServerInformation implements Packet {

    private String serverName;
    private int onlinePlayer;
    private int maxPlayer;
    private String motd;


    @Override
    public void read(ByteBufInputStream byteBuf) {
        try {
            this.serverName = byteBuf.readUTF();
            this.onlinePlayer = byteBuf.readInt();
            this.maxPlayer = byteBuf.readInt();
            this.motd = byteBuf.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Packet handle(Channel channel) {
        Master.instance.getServers().forEach(server -> {
            if (server.getName().equals(serverName)) {
                server.setOnlinePlayer(onlinePlayer);
                server.setMaxPlayer(maxPlayer);
                server.setMotd(motd);
            }
        });
        Master.instance.getApis().forEach(channels -> {
            channels.writeAndFlush(new PacketOutServerInformation(serverName, onlinePlayer, maxPlayer, motd ), channels.voidPromise());
        });
        return null;
    }
}
