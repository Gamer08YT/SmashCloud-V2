package net.smashmc.cloud.bungeesystem.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.smashmc.cloud.bungeesystem.BungeeSystem;
import net.smashmc.cloud.bungeesystem.handler.Prefixes;

public class CommandWhereami extends Command{
    private final BungeeSystem plugin;

    public CommandWhereami( BungeeSystem plugin ){
        super( "whereami" );
        this.plugin = plugin;
        plugin.getProxy().getPluginManager().registerCommand( plugin, this );
    }

    @Override
    public void execute( CommandSender commandSender, String[] strings ){
        if ( commandSender instanceof ProxiedPlayer ){

            ProxiedPlayer player = ( ProxiedPlayer ) commandSender;

            player.sendMessage( new TextComponent( Prefixes.CLOUD.getText() + "§7Du befindest dich auf §6" + player.getServer().getInfo().getName() + "§8." ) );

        }
    }
}
