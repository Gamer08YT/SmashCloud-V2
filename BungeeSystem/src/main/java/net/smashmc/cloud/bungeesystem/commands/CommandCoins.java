package net.smashmc.cloud.bungeesystem.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.smashmc.cloud.bungeesystem.BungeeSystem;
import net.smashmc.cloud.bungeesystem.handler.Prefixes;

import java.util.UUID;

public class CommandCoins extends Command{

    private final BungeeSystem plugin;

    public CommandCoins( BungeeSystem plugin ){
        super( "setcoins" );
        this.plugin = plugin;
        plugin.getProxy().getPluginManager().registerCommand( plugin, this );
    }

    @Override
    public void execute( CommandSender commandSender, String[] args ){
        if ( commandSender instanceof ProxiedPlayer ){

            ProxiedPlayer player = ( ProxiedPlayer ) commandSender;
            if ( BungeeSystem.instance.getPlayer().get( player.getUniqueId() ).getRankId() == 1 ){
                if ( args.length == 2 ){

                    final String uuid = plugin.getJedis().hget( "players", args[ 0 ] );

                    final int coins = Integer.parseInt( args[ 1 ] );

                    plugin.getUser().getCoins( UUID.fromString( uuid ), i -> {
                        plugin.getUser().setCoins( UUID.fromString( uuid ), i + coins );
                    } );

                    player.sendMessage( new TextComponent( Prefixes.CLOUD.getText() + "§7Du hast dem Spieler §b" + args[ 0 ] + "§e " + args[ 1 ] + "§7 Coins §7gegeben" ) );
                }
            } else{
                player.sendMessage( new TextComponent( Prefixes.PREFIX.getText() + "§cDu hast dazu keine rechte!" ) );
            }
        }
    }
}
