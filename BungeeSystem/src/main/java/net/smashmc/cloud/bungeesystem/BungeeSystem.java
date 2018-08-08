package net.smashmc.cloud.bungeesystem;

import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.smashmc.cloud.bungeesystem.commands.*;
import net.smashmc.cloud.bungeesystem.database.Friend;
import net.smashmc.cloud.bungeesystem.database.Settings;
import net.smashmc.cloud.bungeesystem.database.User;
import net.smashmc.cloud.bungeesystem.handler.PlayerInfo;
import net.smashmc.cloud.bungeesystem.handler.lobby.LobbyDistributor;
import net.smashmc.cloud.bungeesystem.handler.report.ReportHandler;
import net.smashmc.cloud.bungeesystem.netty.netty.NetworkHandler;
import net.smashmc.cloud.bungeesystem.handler.objects.Server;
import net.smashmc.cloud.bungeesystem.listener.ListenerChat;
import net.smashmc.cloud.bungeesystem.listener.ListenerLogin;
import net.smashmc.cloud.bungeesystem.listener.ListenerPing;
import net.smashmc.cloud.bungeesystem.netty.packet.PacketAddReport;
import net.smashmc.cloud.bungeesystem.netty.packet.PacketInAddServer;
import net.smashmc.cloud.bungeesystem.netty.packet.PacketInStopServer;
import net.smashmc.cloud.bungeesystem.netty.packet.PacketOutRegisterBungee;
import net.smashmc.cloud.bungeesystem.handler.objects.Report;
import net.smashmc.cloud.network.Client;
import net.smashmc.cloud.network.handler.PacketDecoder;
import net.smashmc.cloud.network.handler.PacketEncoder;
import net.smashmc.cloud.network.registry.PacketRegistry;
import net.smashmc.modules.mongodb.storage.Credential;
import net.smashmc.modules.mongodb.storage.connection.DatabaseConnection;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @author Mike
 * @version 1.0
 */

@Getter
public class BungeeSystem extends Plugin{

    private final Client client = new Client( 5000, "localhost" );
    private final String[] property = System.getProperty( "user.dir" ).split( "/" );
    private final String proxyName = property[ property.length - 1 ];
    private DatabaseConnection databaseConnection;
    private List<Server> servers = new ArrayList<>();
    public static BungeeSystem instance;
    private final Friend.FriendHandler friendHandler = new Friend.FriendHandler();
    private final Jedis jedis = new Jedis( "localhost", 6379 );
    private final User user = new User();
    private final List<Report> reportList = new ArrayList<>();
    private final ReportHandler reportHandler = new ReportHandler();
    private List<ProxiedPlayer> loggedIn = new ArrayList<>();
    private final HashMap<UUID, PlayerInfo> player = new HashMap<>();
    private final Settings settings = new Settings();

    @Setter
    private Channel channels;

    /**
     * Create singleton
     */
    @Override
    public void onEnable(){
        instance = this;
        init();
    }

    /**
     * Intialize Methdos
     */
    private void init(){
        connectMongo();
        connectRedis();
        registerPackets();
        setUpClient();
        registerListener();
        registerCommands();
        getProxy().setReconnectHandler( new LobbyDistributor() );
    }

    private void connectRedis(){
        jedis.connect();
    }

    /**
     * Register Listener
     */
    private void registerListener(){
        new ListenerPing( this );
        new ListenerLogin( this );
        new ListenerChat( this );
    }

    /**
     * Connect to Mongo Database
     */
    private void connectMongo(){
        databaseConnection = new DatabaseConnection();
        databaseConnection.openConnection( new Credential( "localhost", "network" ) );
    }

    /**
     * SetUp the netty client and connect to Server
     */
    private void setUpClient(){
        this.channels = this.client.connect( channel -> channel.pipeline().addLast( new PacketEncoder() ).addLast( new PacketDecoder() ).addLast( new NetworkHandler() ) );
    }

    /**
     * Register incoming and outgoing packets
     */
    private void registerPackets(){
        PacketRegistry.PacketDirection.OUT.addPacket( 1, PacketOutRegisterBungee.class );
        PacketRegistry.PacketDirection.IN.addPacket( 2, PacketInAddServer.class );
        PacketRegistry.PacketDirection.IN.addPacket( 3, PacketInStopServer.class );
        PacketRegistry.PacketDirection.OUT.addPacket( 7, PacketAddReport.class );
        PacketRegistry.PacketDirection.IN.addPacket( 7, PacketAddReport.class );
    }

    /**
     * Register Commands
     */
    private void registerCommands(){
        new CommandPing( this );
        new CommandCloud( this );
        new CommandJoinMe( this );
        new CommandFriend( this );
        new CommandMSG( this );
        new CommandReport( this );
        new CommandHub( this );
        new CommandReports( this );
        new CommandKick( this );
        new CommandBroadcast( this );
        new CommandWhereami( this );
        new CommandSetRank( this );
        new CommandCoins( this );
    }

    /**
     * Disconnect from MongoDB
     */
    @Override
    public void onDisable(){
        this.client.disconnect();
    }


}