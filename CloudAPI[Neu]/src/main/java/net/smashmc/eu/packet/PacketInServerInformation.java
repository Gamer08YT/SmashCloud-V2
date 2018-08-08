package net.smashmc.eu.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.Channel;
import net.smashmc.cloud.network.packet.Packet;
import net.smashmc.eu.CloudAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;

public class PacketInServerInformation implements Packet{

    private String serverName;
    private int onlinePlayer;
    private int maxPlayer;
    private String motd;

    public PacketInServerInformation(){
    }

    @Override
    public void read( ByteBufInputStream byteBuf ){
        try{
            this.serverName = byteBuf.readUTF();
            this.onlinePlayer = byteBuf.readInt();
            this.maxPlayer = byteBuf.readInt();
            this.motd = byteBuf.readUTF();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public Packet handle( Channel channel ){
        CloudAPI.instance.getServers().forEach( server -> {
            if ( server.getName().equals( serverName ) ){
                server.setOnlinePlayer( onlinePlayer );
                server.setMaxPlayer( maxPlayer );
                server.setMotd( this.motd );
                CloudAPI.instance.getServerUpdateList().forEach( serverUpdate -> serverUpdate.onUpdate( server ) );
            }
        } );


        return null;
    }
}
