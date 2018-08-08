package net.smashmc.cloud.bungeesystem.handler.lobby;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ReconnectHandler;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class LobbyDistributor implements ReconnectHandler {

    private static Random random = new Random();

    public ServerInfo getRandom(PendingConnection pendingConnection) {
        //Search a random lobby and set the pending
        List<ServerInfo> serverinfo = ProxyServer.getInstance().getServers().values().stream().filter(srver -> (srver.getName().startsWith("Lobby") && !srver.getName().equals(pendingConnection.getName()))).collect(Collectors.toList());
        int next = random.nextInt(serverinfo.size());
        ServerInfo info = serverinfo.get(next);
        return info;
    }

    @Override
    public ServerInfo getServer(ProxiedPlayer proxiedPlayer) {
        ServerInfo info = getRandom(proxiedPlayer.getPendingConnection());
        if (info == null) {
            proxiedPlayer.disconnect(new TextComponent("§cEs ist aktuell keine Lobby erreichbar"));
        }
        return info;
    }

    @Override
    public void setServer(ProxiedPlayer proxiedPlayer) {

    }

    @Override
    public void save() {

    }

    @Override
    public void close() {

    }
}
