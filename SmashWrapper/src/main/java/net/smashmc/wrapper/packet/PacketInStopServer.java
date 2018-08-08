package net.smashmc.wrapper.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.Channel;
import net.smashmc.cloud.network.packet.Packet;
import net.smashmc.wrapper.Wrapper;

import java.io.IOException;

public class PacketInStopServer implements Packet {

    private String name;

    @Override
    public void read(ByteBufInputStream byteBuf) {
        try {
            this.name = byteBuf.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Packet handle(Channel channel) {
        System.out.println(" Server " + name + " stopped");
        Wrapper.instance.getGameServer().stopByName(name);
        System.out.println(" Server " + name + " stopped");
        return null;
    }
}
