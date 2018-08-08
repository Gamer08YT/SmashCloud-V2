package net.smashmc.cloud.master;


import io.netty.channel.Channel;
import lombok.Getter;
import net.smashmc.cloud.master.command.StopCloudCommand;
import net.smashmc.cloud.master.command.handler.CommandHandler;
import net.smashmc.cloud.master.handler.CloudHandler;
import net.smashmc.cloud.master.handler.NetworkHandler;
import net.smashmc.cloud.master.handler.ProxyHandler;
import net.smashmc.cloud.master.handler.WrapperHandler;
import net.smashmc.cloud.master.packets.*;
import net.smashmc.cloud.master.utils.Shutdown;
import net.smashmc.cloud.master.utils.modules.Template;
import net.smashmc.cloud.network.Server;
import net.smashmc.cloud.network.handler.PacketDecoder;
import net.smashmc.cloud.network.handler.PacketEncoder;
import net.smashmc.cloud.network.registry.PacketRegistry;
import net.smashmc.module.logger.LogHandler;
import net.smashmc.modules.mongodb.storage.Credential;
import net.smashmc.modules.mongodb.storage.connection.DatabaseConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Mike
 * @version 1.0
 */
@Getter
public class Master{
    public List<Template> templates = new CopyOnWriteArrayList<>();
    public static Master instance;
    private final CommandHandler commandHandler;
    private final CloudHandler cloudWrapper = new CloudHandler();
    private List<Channel> apis = new ArrayList<>();
    private List<Channel> lobbys = new ArrayList<>();
    private final LogHandler logger;
    private DatabaseConnection databaseConnection;
    private Server server = new Server( 5000 );
    private WrapperHandler wrapperHandler = new WrapperHandler();
    private ProxyHandler proxyHandler = new ProxyHandler();
    private List<net.smashmc.cloud.master.utils.types.Server> servers = new ArrayList<>();
    private CloudHandler cloudHandler = new CloudHandler();

    /**
     * Constructor which load the methods
     */
    private Master(){
        instance = this;

        this.logger = new LogHandler();

        logger.log( " " );
        logger.log( "   _____ _                 _ __  __           _            " );
        logger.log( "  / ____| |               | |  \\/  |         | |           " );
        logger.log( " | |    | | ___  _   _  __| | \\  / | __ _ ___| |_ ___ _ __ " );
        logger.log( " | |    | |/ _ \\| | | |/ _` | |\\/| |/ _` / __| __/ _ \\ '__|" );
        logger.log( " | |____| | (_) | |_| | (_| | |  | | (_| \\__ \\ ||  __/ |   " );
        logger.log( "  \\_____|_|\\___/ \\__,_|\\__,_|_|  |_|\\__,_|___/\\__\\___|_|   " );
        logger.log( " " );
        logger.log( "                             (Made by ByteException)" );
        logger.log( " " );

        databaseConnection = new DatabaseConnection();
        databaseConnection.openConnection( new Credential( "localhost", "network" ) );

        getWrapperHandler().addWrapper();

        registerPacket();

        setUpMaster();


        this.commandHandler = new CommandHandler();
        this.commandHandler.startTask();

        registerCommands();

        Runtime.getRuntime().addShutdownHook( new Thread( new Shutdown() ) );
    }

    /**
     * Bind netty server with ssl
     */
    private void setUpMaster(){
        this.server.bind( () -> {
        }, channel -> channel.pipeline().addLast( new PacketEncoder() ).addLast( new PacketDecoder() ).addLast( new NetworkHandler() ) );
    }

    /**
     * Register incoming and outgoing packets
     */
    private void registerPacket(){
        PacketRegistry.PacketDirection.IN.addPacket( 1, PacketInRegister.class );
        PacketRegistry.PacketDirection.OUT.addPacket( 2, PacketOutAddServer.class );
        PacketRegistry.PacketDirection.IN.addPacket( 3, PacketStopServer.class );
        PacketRegistry.PacketDirection.OUT.addPacket( 3, PacketStopServer.class );
        PacketRegistry.PacketDirection.IN.addPacket( 5, PacketInServerInformation.class );
        PacketRegistry.PacketDirection.OUT.addPacket( 6, PacketOutServerInformation.class );
        PacketRegistry.PacketDirection.OUT.addPacket( 7, PacketAddReport.class );
        PacketRegistry.PacketDirection.IN.addPacket( 7, PacketAddReport.class );
        PacketRegistry.PacketDirection.IN.addPacket( 9, PacketInSetServerIngame.class );
    }

    /**
     * Method which load the commands
     */
    private void registerCommands(){
        this.commandHandler.addCommand( new StopCloudCommand() );
    }

    /**
     * Java starting Project class
     *
     * @param args main arguments
     */
    public static void main( String[] args ){
        new Master();
    }
}

