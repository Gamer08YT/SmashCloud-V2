package net.smashmc.cloud.master.packets;

import io.netty.buffer.ByteBufOutputStream;
import net.smashmc.cloud.network.packet.Packet;

import java.io.IOException;

public class PacketOutUpdateSign implements Packet {

    private String template;
    private String world;
    private double x;
    private double y;
    private double z;

    public PacketOutUpdateSign(String template, String world, double x, double y, double z) {
        this.template = template;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public PacketOutUpdateSign() {
    }

    @Override
    public void write(ByteBufOutputStream byteBuf) {
        try {
            byteBuf.writeUTF(this.template);
            byteBuf.writeUTF(this.world);
            byteBuf.writeDouble(this.x);
            byteBuf.writeDouble(this.y);
            byteBuf.writeDouble(this.z);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
