package net.smashmc.eu.packet;

import io.netty.buffer.ByteBufOutputStream;
import net.smashmc.cloud.network.packet.Packet;

import java.io.IOException;

public class PacketOutServerInformation implements Packet{

    private String serverName;
    private int onlinePlayer;
    private int maxPlayer;
    private String motd;

    public PacketOutServerInformation( String serverName, int onlinePlayer, int maxPlayer, String motd ){
        this.serverName = serverName;
        this.onlinePlayer = onlinePlayer;
        this.maxPlayer = maxPlayer;
        this.motd = motd;
    }

    public PacketOutServerInformation(){
    }

    @Override
    public void write( ByteBufOutputStream byteBuf ){
        try{
            byteBuf.writeUTF( this.serverName );
            byteBuf.writeInt( this.onlinePlayer );
            byteBuf.writeInt( this.maxPlayer );
            byteBuf.writeUTF( this.motd );
            System.out.println( "packet was write: " + serverName + motd + maxPlayer + onlinePlayer );
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
