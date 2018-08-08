package net.smashmc.eu.server;

import net.smashmc.eu.utils.Server;

public class ServerInfo{

    public String getServerName( Server server ){
        return server.getName();
    }

    public String getServerTemplate( Server server ){
        return server.getTemplate();
    }
}