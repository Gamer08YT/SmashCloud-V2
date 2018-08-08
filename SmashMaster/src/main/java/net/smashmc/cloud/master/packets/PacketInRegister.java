package net.smashmc.cloud.master.packets;

import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.Channel;
import net.smashmc.cloud.master.Master;
import net.smashmc.cloud.network.packet.Packet;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

/**
 * @author Mike
 * @version 1.0
 */
public class PacketInRegister implements Packet {

    public Type type;
    private String name;

    /**
     * Constructor of PacketRegister
     */
    public PacketInRegister() {
    }

    /**
     * Read the Incoming Packet
     *
     * @param byteBuf read the incoming information
     * @throws IOException
     */
    @Override
    public void read(ByteBufInputStream byteBuf) throws IOException {
        this.name = byteBuf.readUTF();
        this.type = Type.class.getEnumConstants()[byteBuf.readInt()];
    }

    /**
     * Handle the incoming information
     *
     * @param channel connected channel
     * @return
     */
    @Override
    public Packet handle(Channel channel) {
        switch (type) {
            case WRAPPER:
                Master.instance.getWrapperHandler().getWrappers().forEach(wrapper -> {
                    if (wrapper.getName().equals(name)) {
                        wrapper.setChannel(channel);
                        Master.instance.getLogger().log(String.format("Wrapper (%s) bound on this Master!", name));
                        Master.instance.getProxyHandler().startProxies("Proxy-1", 1024, new File(System.getProperty("user.dir") + "/local/Proxy-1/"));
                    }
                });
                break;
            case BUNGEECORD:
                Master.instance.getProxyHandler().getBungeecords().forEach(bungeecord -> {
                    if (bungeecord.getName().equals(this.name)) {
                        bungeecord.setChannel(channel);
                        Master.instance.getLogger().log(String.format("Proxy (%s) bind on Master", name));
                    }
                });
                Master.instance.getCloudWrapper().loadGroups();
                break;

            case API:
                Master.instance.getApis().add(channel);
                Master.instance.getServers().forEach(server -> {
                    channel.writeAndFlush(new PacketOutAddServer(server), channel.voidPromise());
                });
                if(this.name.startsWith("Lobby")){
                    Master.instance.getLobbys().add(channel);
                }
                break;
        }

        return null;
    }

    /**
     * Type which is registered
     */
    public enum Type {
        BUNGEECORD, WRAPPER, API;
    }
}
