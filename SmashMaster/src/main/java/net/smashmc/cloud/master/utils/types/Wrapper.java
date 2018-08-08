package net.smashmc.cloud.master.utils.types;

import io.netty.channel.Channel;

import java.util.List;

/**
 * @author Mike
 * @version 1.0
 */
public class Wrapper {

    private String name;
    private List<Integer> ports;
    private List<Server> servers;
    private int maxRam;
    private int usableRam;
    private Channel channel;

    /**
     * Create a new Wrapper Object
     * @param name the Wrapper name must be equals then the Wrappper folder
     * @param ports binded Ports on that Wrapper
     * @param servers binded Server on that Wrapper
     * @param maxRam MaxRam for the Wrapper
     * @param usableRam the ram which the Wrapper used
     */
    public Wrapper(String name, List<Integer> ports, List<Server> servers, int maxRam, int usableRam) {
        this.name = name;
        this.ports = ports;
        this.servers = servers;
        this.maxRam = maxRam;
        this.usableRam = usableRam;
    }

    public String getName() {
        return name;
    }

    public Channel getChannel() {
        return channel;
    }

    public int getMaxRam() {
        return maxRam;
    }

    public int getUsableRam() {
        return usableRam;
    }

    public List<Integer> getPorts() {
        return ports;
    }

    public List<Server> getServers() {
        return servers;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public void setMaxRam(int maxRam) {
        this.maxRam = maxRam;
    }

    public void setPorts(List<Integer> ports) {
        this.ports = ports;
    }

    public void setServers(List<Server> servers) {
        this.servers = servers;
    }

    public void setUsableRam(int usableRam) {
        this.usableRam = usableRam;
    }

    public Integer getFreePort() {
        for (int i = 50000; i < 65535; i++) {
            if (!ports.contains(i)) {
                ports.add(i);
                return i;
            }
        }
        return -1;
    }
}
