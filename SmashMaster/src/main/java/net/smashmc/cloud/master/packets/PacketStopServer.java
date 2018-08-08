package net.smashmc.cloud.master.packets;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.Channel;
import net.smashmc.cloud.master.Master;
import net.smashmc.cloud.network.packet.Packet;

import java.io.IOException;

public class PacketStopServer implements Packet{

    private String name;

    public PacketStopServer(){
    }

    public PacketStopServer( String name ){
        this.name = name;
    }

    @Override
    public void write( ByteBufOutputStream byteBuf ){
        try{
            byteBuf.writeUTF( this.name );
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void read( ByteBufInputStream byteBuf ){
        try{
            this.name = byteBuf.readUTF();
            System.out.println( Master.instance.getTemplates().size() );
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public Packet handle( Channel channel ){
        String[] templateName = name.split( "-" );

        Master.instance.getWrapperHandler().getWrappers().forEach( wrapper -> {
            wrapper.getChannel().writeAndFlush( new PacketStopServer( name ), wrapper.getChannel().voidPromise() );
            Master.instance.getServers().forEach( server -> {
                if ( server.getName().equals( name ) ){
                    wrapper.setUsableRam( wrapper.getUsableRam() + server.getMinRam() );
                    wrapper.getPorts().remove( new Integer( server.getPort() ) );
                    wrapper.getServers().remove( server );
                }
            } );
        } );
        Master.instance.getTemplates().forEach( template -> {
            if ( templateName[ 0 ].equalsIgnoreCase( template.getTemplate() ) ){
                template.setOnline( template.getOnline() - 1 );
                if ( template.getOnline() < template.getMinOnline() ){
                    Master.instance.getCloudHandler().startServer( template );
                }
            }
        } );
        Master.instance.getProxyHandler().getBungeecords().forEach( bungeecord -> {
            bungeecord.getChannel().writeAndFlush( new PacketStopServer( name ), bungeecord.getChannel().voidPromise() );
        } );
        return null;
    }
}
