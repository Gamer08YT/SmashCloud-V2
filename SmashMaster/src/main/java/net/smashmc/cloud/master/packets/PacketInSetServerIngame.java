package net.smashmc.cloud.master.packets;

import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.Channel;
import net.smashmc.cloud.master.Master;
import net.smashmc.cloud.network.packet.Packet;

import java.io.IOException;

public class PacketInSetServerIngame implements Packet{

    private String name;

    public PacketInSetServerIngame(){
    }

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

        final String[] inComingTemplate = this.name.split( "-" );

        Master.instance.getTemplates().forEach( template -> {
            if ( template.getTemplate().equals( inComingTemplate[ 0 ] ) ){
                Master.instance.getCloudHandler().startServer( template );
            }
        } );

        return null;
    }
}
