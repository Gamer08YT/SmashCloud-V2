package net.smashmc.cloud.network.packet.impl;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.Channel;
import net.smashmc.cloud.network.packet.Packet;

import java.io.IOException;

public class ErrorPacket implements Packet {

    private String error;

    public ErrorPacket() {
    }

    public ErrorPacket(final String error) {
        this.error = error;
    }

    public final Packet handle(final Channel channel) {
        System.out.println(error);
        return new SuccesPacket();
    }

    @Override
    public void read(ByteBufInputStream byteBuf) {
        try {
            byteBuf.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(ByteBufOutputStream byteBuf) {
        try {
            byteBuf.writeUTF(error);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
