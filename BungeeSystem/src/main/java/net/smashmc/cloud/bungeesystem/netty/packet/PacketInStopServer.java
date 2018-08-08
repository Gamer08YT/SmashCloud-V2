package net.smashmc.cloud.bungeesystem.netty.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.Channel;
import net.md_5.bungee.api.ProxyServer;
import net.smashmc.cloud.network.packet.Packet;

import java.io.IOException;

public class PacketInStopServer implements Packet{

    private String name;

    /**
     * Constructor of PacketInStopServer
     *
     * @param name the name of server which you want to stop
     */
    public PacketInStopServer( String name ){
        this.name = name;
    }

    /**
     * Create a empty constructor
     */
    public PacketInStopServer(){
    }

    /**
     * Write the packet to server
     *
     * @param byteBuf write the outgoing information
     */
    @Override
    public void read( ByteBufInputStream byteBuf ){
        try{
            this.name = byteBuf.readUTF();
            System.out.println( "read" );
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public Packet handle( Channel channel ){
        ProxyServer.getInstance().getServers().remove( name );
        return null;
    }
}
