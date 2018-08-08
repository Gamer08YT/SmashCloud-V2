package net.smashmc.cloud.bungeesystem.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.smashmc.cloud.bungeesystem.BungeeSystem;
import net.smashmc.cloud.bungeesystem.handler.Prefixes;

public class CommandKick extends Command{

    private BungeeSystem plugin;

    public CommandKick( BungeeSystem plugin ){
        super( "kick" );
        this.plugin = plugin;
        plugin.getProxy().getPluginManager().registerCommand( plugin, this );
    }

    @Override
    public void execute( CommandSender commandSender, String[] args ){
        if ( commandSender instanceof ProxiedPlayer ){
            ProxiedPlayer player = ( ProxiedPlayer ) commandSender;

            if ( BungeeSystem.instance.getPlayer().get( player.getUniqueId() ).getRankId() < 5 ){
                if ( args.length == 2 ){


                    ProxiedPlayer target = ProxyServer.getInstance().getPlayer( args[ 0 ] );

                    if ( target != null ){

                        String reason = args[ 1 ];

                        target.disconnect( new TextComponent( "§cDu wurdest von §6§lSmashMC§f.EU §cgekickt \n §cGrund§8: §e" + reason ) );

                        ProxyServer.getInstance().getPlayers().forEach( all -> {

                            BungeeSystem.instance.getUser().getRankID( all, rankId -> {

                                if ( rankId < 5 && BungeeSystem.instance.getLoggedIn().contains( all ) ){
                                    all.sendMessage( new TextComponent( "§7§m--------- §c Kick §7§m---------" ) );
                                    player.sendMessage( new TextComponent( " " ) );
                                    all.sendMessage( new TextComponent( "§cSpieler§8: §e" + target.getName() ) );
                                    all.sendMessage( new TextComponent( "§cGrund§8: §e" + reason ) );
                                    player.sendMessage( new TextComponent( " " ) );
                                    all.sendMessage( new TextComponent( "§7§m--------- §c Kick §7§m---------" ) );
                                }

                            } );

                        } );

                    } else{
                        player.sendMessage( new TextComponent( Prefixes.CLOUD.getText() + "§cDieser Spieler ist nicht online!" ) );
                    }

                } else{
                    player.sendMessage( new TextComponent( Prefixes.CLOUD.getText() + "§cNutze /kick <PlayerInfo> <Grund>" ) );
                }
            } else{
                player.sendMessage( new TextComponent( Prefixes.PREFIX.getText() + "§cDu hast dazu keine rechte!" ) );
            }


        }
    }
}
