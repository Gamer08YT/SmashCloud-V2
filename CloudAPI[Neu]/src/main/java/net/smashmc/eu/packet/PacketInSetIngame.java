package net.smashmc.eu.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.Channel;
import net.smashmc.cloud.network.packet.Packet;

import java.io.IOException;

public class PacketInSetIngame implements Packet{

    private String name;

    public PacketInSetIngame (){}

    @Override
    public void read( ByteBufInputStream byteBuf ){
        try{
            this.name = byteBuf.readUTF();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public Packet handle( Channel channel ){

        return null;
    }
}
