package net.smashmc.cloud.master.packets;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.Channel;
import net.smashmc.cloud.master.Master;
import net.smashmc.cloud.network.packet.Packet;

import java.io.IOException;

public class PacketAddReport implements Packet {

    private String playerName;
    private String targetName;
    private String reason;

    public PacketAddReport() {
    }

    public PacketAddReport(String playerName, String targetName, String reason) {
        this.playerName = playerName;
        this.targetName = targetName;
        this.reason = reason;
    }

    @Override
    public void read(ByteBufInputStream byteBuf) {
        try {
            this.playerName = byteBuf.readUTF();
            this.targetName = byteBuf.readUTF();
            this.reason = byteBuf.readUTF();
            System.out.print("report wa read");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(ByteBufOutputStream byteBuf) {
        try {
            byteBuf.writeUTF(this.playerName);
            byteBuf.writeUTF(this.targetName);
            byteBuf.writeUTF(this.reason);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Packet handle(Channel channel) {
        if (reason.isEmpty()) {
            Master.instance.getApis().forEach(apis -> {

                apis.writeAndFlush(new PacketAddReport(this.playerName, this.targetName, reason), apis.voidPromise());
            });
        } else {
            Master.instance.getProxyHandler().getBungeecords().forEach(bungeecord -> {
                bungeecord.getChannel().writeAndFlush(new PacketAddReport(this.playerName, this.targetName, reason), bungeecord.getChannel().voidPromise());
            });
        }


        System.out.print("report wa send");
        return null;
    }
}
