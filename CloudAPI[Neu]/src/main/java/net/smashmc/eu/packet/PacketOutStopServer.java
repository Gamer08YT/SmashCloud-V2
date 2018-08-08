package net.smashmc.eu.packet;
import io.netty.buffer.ByteBufOutputStream;
import net.smashmc.cloud.network.packet.Packet;

import java.io.IOException;

public class PacketOutStopServer implements Packet{

    private String name;

    public PacketOutStopServer( String name ){
        this.name = name;
    }

    public PacketOutStopServer(){
    }

    @Override
    public void write( ByteBufOutputStream byteBuf ){
        try{
            byteBuf.writeUTF( this.name );
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}