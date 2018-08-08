package net.smashmc.cloud.bungeesystem.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.smashmc.cloud.bungeesystem.BungeeSystem;
import net.smashmc.cloud.bungeesystem.handler.Prefixes;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class CommandHub extends Command{

    private BungeeSystem plugin;
    private static Random random = new Random();

    public CommandHub( BungeeSystem plugin ){
        super( "hub" );
        this.plugin = plugin;
        plugin.getProxy().getPluginManager().registerCommand( plugin, this );
    }

    @Override
    public void execute( CommandSender commandSender, String[] strings ){
        if ( commandSender instanceof ProxiedPlayer ){
            ProxiedPlayer player = ( ProxiedPlayer ) commandSender;
            if ( !player.getServer().getInfo().getName().startsWith( "Lobby" ) ){
                List<ServerInfo> serverinfo = ProxyServer.getInstance().getServers().values().stream().filter( srver -> ( srver.getName().startsWith( "Lobby" ) && !srver.getName().equals( player.getPendingConnection().getName() ) ) ).collect( Collectors.toList() );
                int next = random.nextInt( serverinfo.size() );
                ServerInfo info = serverinfo.get( next );
                player.connect( info );
            } else{
                player.sendMessage( new TextComponent( Prefixes.PREFIX.getText() + "§cDu befindest dich §cbereits §cauf §ceiner §cLobby" ) );
            }
        }
    }
}
