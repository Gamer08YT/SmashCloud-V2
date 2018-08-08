package net.smashmc.cloud.bungeesystem.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.Title;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.smashmc.cloud.bungeesystem.BungeeSystem;

public class CommandBroadcast extends Command{
    private final BungeeSystem plugin;

    public CommandBroadcast( BungeeSystem plugin ){
        super( "bc" );
        this.plugin = plugin;
        plugin.getProxy().getPluginManager().registerCommand( plugin, this );
    }

    @Override
    public void execute( CommandSender commandSender, String[] args ){
        if ( commandSender instanceof ProxiedPlayer ){

            ProxiedPlayer player = ( ProxiedPlayer ) commandSender;

            if ( BungeeSystem.instance.getPlayer().get( player.getUniqueId() ).getRankId() < 2 ){

                if ( args.length >= 1 ){

                    String message = "";
                    for ( int i = 0; i < args.length; i++ ){
                        message = message + args[ i ] + " ";
                    }
                    message = ChatColor.translateAlternateColorCodes( '&', message );
                    ProxyServer.getInstance().broadcast( new TextComponent( " §cBroadcast §8➜ " + message ));
                }

            }

        }
    }
}
