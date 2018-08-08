package net.smashmc.eu.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Server {

    private String name;
    private String template;
    private int ram;
    private int port;
    private int onlinePlayer;
    private int maxPlayer;
    private String motd;

    public Server(String name, String template, int ram, int port, int onlinePlayer, int maxPlayer, String motd) {
        this.name = name;
        this.template = template;
        this.ram = ram;
        this.port = port;
        this.onlinePlayer = onlinePlayer;
        this.maxPlayer = maxPlayer;
        this.motd = motd;
    }
}
