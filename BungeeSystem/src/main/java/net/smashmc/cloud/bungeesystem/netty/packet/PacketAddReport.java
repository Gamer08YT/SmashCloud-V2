package net.smashmc.cloud.bungeesystem.netty.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.Channel;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.smashmc.cloud.bungeesystem.BungeeSystem;
import net.smashmc.cloud.bungeesystem.handler.Prefixes;
import net.smashmc.cloud.bungeesystem.handler.objects.Report;
import net.smashmc.cloud.network.packet.Packet;

import java.io.IOException;

public class PacketAddReport implements Packet{

    private String playerName;
    private String targetName;
    private String reason;

    public PacketAddReport(){
    }

    public PacketAddReport( String playerName, String targetName, String reason ){
        this.playerName = playerName;
        this.targetName = targetName;
        this.reason = reason;
    }

    @Override
    public void read( ByteBufInputStream byteBuf ){
        try{
            this.playerName = byteBuf.readUTF();
            this.targetName = byteBuf.readUTF();
            this.reason = byteBuf.readUTF();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void write( ByteBufOutputStream byteBuf ){
        try{
            byteBuf.writeUTF( this.playerName );
            byteBuf.writeUTF( this.targetName );
            byteBuf.writeUTF( this.reason );
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public Packet handle( Channel channel ){
        BungeeSystem.instance.getReportList().add( new Report( playerName, targetName, reason ) );
        ProxyServer.getInstance().getPlayers().forEach( all -> {

            BungeeSystem.instance.getUser().getRankID( all, id -> {
                if ( id < 6  && BungeeSystem.instance.getLoggedIn().contains( all )){
                    all.sendMessage( new TextComponent( Prefixes.REPORT.getText() + "§7Ein neuer Report ist eingetroffen. §8(§e" + BungeeSystem.instance.getReportList().size() + "§8)" ) );
                }
            } );

        } );
        return null;
    }
}
