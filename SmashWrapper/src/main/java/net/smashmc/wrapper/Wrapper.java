package net.smashmc.wrapper;

import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;
import net.smashmc.cloud.network.Client;
import net.smashmc.cloud.network.handler.PacketDecoder;
import net.smashmc.cloud.network.handler.PacketEncoder;
import net.smashmc.cloud.network.registry.PacketRegistry;
import net.smashmc.wrapper.data.ServerData;
import net.smashmc.wrapper.handler.GameServer;
import net.smashmc.wrapper.handler.NetworkHandler;
import net.smashmc.wrapper.packet.PacketInAddServer;
import net.smashmc.wrapper.packet.PacketInStopServer;
import net.smashmc.wrapper.packet.PacketOutRegisterWrapper;

import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Mike
 * @version 1.0
 */
@Getter
public class Wrapper {

    public static Wrapper instance;
    private Client client = new Client(5000, "127.0.0.1");
    private GameServer gameServer = new GameServer();
    private String[] property = System.getProperty("user.dir").split("/");
    private final String wrapperName = property[property.length - 1];
    public static final ConcurrentHashMap<String, ServerData> servers = new ConcurrentHashMap<>();

    @Setter
    private Channel channel;

    /**
     * Constructor which load the methods
     */
    private Wrapper() {

        instance = this;

        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" _______         _____  _     _ ______  _  _  _  ______ _______  _____   _____  _______  ______\n" +
                " |       |      |     | |     | |     \\ |  |  | |_____/ |_____| |_____] |_____] |______ |_____/\n" +
                " |_____  |_____ |_____| |_____| |_____/ |__|__| |    \\_ |     | |       |       |______ |    \\_\n" +
                "                                                                                            ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println("Wrapper (" + wrapperName + ") started!");

        registerPackets();

        setUpClient();

        Runtime.getRuntime().addShutdownHook(new Thread(this::stop));

        new Thread(()->{
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNext("stop")) {
                System.exit(0);
            }
        }).start();
    }

    /**
     * SetUp the netty client and connect to Server
     */
    private void setUpClient() {
        this.channel = this.client.connect(channel -> channel.pipeline().addLast(new PacketEncoder()).addLast(new PacketDecoder()).addLast(new NetworkHandler()));
    }

    /**
     * Register incoming and outgoing packets
     */
    private void registerPackets() {
        PacketRegistry.PacketDirection.OUT.addPacket(1, PacketOutRegisterWrapper.class);
        PacketRegistry.PacketDirection.IN.addPacket(2, PacketInAddServer.class);
        PacketRegistry.PacketDirection.IN.addPacket(3, PacketInStopServer.class);
    }

    /**
     * Load the Methods which are used in the Shutdown part.
     */
    public void stop() {
        this.channel.close().awaitUninterruptibly();
        this.client.disconnect();
        this.gameServer.kill();
        System.out.println("Wrapper (" + wrapperName + ") stopped!");

    }

    /**
     * Java starting Project class
     *
     * @param args
     */
    public static void main(String[] args) {
        new Wrapper();
    }
}