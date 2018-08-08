package net.smashmc.eu;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;
import net.smashmc.api.API;
import net.smashmc.cloud.network.Client;
import net.smashmc.cloud.network.handler.PacketDecoder;
import net.smashmc.cloud.network.handler.PacketEncoder;
import net.smashmc.cloud.network.registry.PacketRegistry;
import net.smashmc.eu.handler.NetworkHandler;
import net.smashmc.eu.packet.*;
import net.smashmc.eu.update.ServerUpdate;
import net.smashmc.eu.utils.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CloudAPI{
    private Client client = new Client( 5000, "localhost" );
    public List<Server> servers = new ArrayList<>();
    public List<ServerUpdate> serverUpdateList = new ArrayList<>();
    public static CloudAPI instance;
    private String[] property;
    private final String serverName;
    private API api = new API();

    @Setter
    private String motd = " ";

    @Setter
    private Channel channels;

    private JavaPlugin javaPlugin;

    public CloudAPI( JavaPlugin javaPlugin ){
        this.javaPlugin = javaPlugin;
        instance = this;
        property = System.getProperty( "user.dir" ).split( "/" );
        serverName = property[ property.length - 1 ];
        System.out.println( serverName );
        init();
    }


    private void init(){
        setMotd( "§aLobby" );
        registerPackets();
        setUpClient();

        javaPlugin.getServer().getMessenger().registerOutgoingPluginChannel( javaPlugin, "BungeeCord" );

        Runtime.getRuntime().addShutdownHook( new Thread( () -> this.channels.close().awaitUninterruptibly() ) );
    }

    /**
     * Connect client to server with ssl
     */
    private void setUpClient(){
        this.channels = this.client.connect( channel -> channel.pipeline().addLast( new PacketEncoder() ).addLast( new PacketDecoder() ).addLast( new NetworkHandler() ) );
        this.channels.writeAndFlush( new PacketOutRegisterAPI( serverName ), this.channels.voidPromise() );
    }

    /**
     * Register incoming and outgoing packets
     */
    private void registerPackets(){
        PacketRegistry.PacketDirection.OUT.addPacket( 1, PacketOutRegisterAPI.class );
        PacketRegistry.PacketDirection.IN.addPacket( 2, PacketInAddServer.class );
        PacketRegistry.PacketDirection.OUT.addPacket( 3, PacketOutStopServer.class );
        PacketRegistry.PacketDirection.OUT.addPacket( 5, PacketOutServerInformation.class );
        PacketRegistry.PacketDirection.IN.addPacket( 6, PacketInServerInformation.class );
        PacketRegistry.PacketDirection.IN.addPacket( 7, PacketAddReport.class );
        PacketRegistry.PacketDirection.OUT.addPacket( 7, PacketAddReport.class );
        PacketRegistry.PacketDirection.OUT.addPacket( 9, PacketOutSetServerIngame.class );
    }

    public void setServerIngame(int online, int max, String motd){
        getChannels().writeAndFlush( new PacketOutSetServerIngame( serverName ), channels.voidPromise() );
    }

    public void updateSign(int online, int max, String motd){

        getChannels().writeAndFlush( new PacketOutServerInformation( CloudAPI.instance.getServerName(), online, max, motd) );
    }

    public void setStopPacket(){
        getChannels().writeAndFlush( new PacketOutStopServer( serverName ), getChannels().voidPromise() );
    }

    public void sendPlayerToServer( Player player, String serverName ){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF( "Connect" );
        out.writeUTF( serverName );
        player.sendPluginMessage( javaPlugin, "BungeeCord", out.toByteArray() );
        return;
    }
}
