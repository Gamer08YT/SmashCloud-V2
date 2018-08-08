package net.smashmc.eu.packet;

import io.netty.buffer.ByteBufOutputStream;
import net.smashmc.cloud.network.packet.Packet;

import java.io.IOException;

public class PacketOutSetServerIngame implements Packet{

    private String name;

    public PacketOutSetServerIngame(String name) {
        this.name = name;
    }

    public PacketOutSetServerIngame(){}

    @Override
    public void write(ByteBufOutputStream byteBuf) {
        try{
            byteBuf.writeUTF( this.name );
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
