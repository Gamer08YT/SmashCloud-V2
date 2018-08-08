package net.smashmc.cloud.bungeesystem.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.smashmc.cloud.bungeesystem.BungeeSystem;
import net.smashmc.cloud.bungeesystem.handler.Prefixes;
import org.omg.CORBA.Object;

public class CommandCloud extends Command{

    public CommandCloud( BungeeSystem plugin ){
        super( "cloud" );
        this.plugin = plugin;
        plugin.getProxy().getPluginManager().registerCommand( plugin, this );
    }

    private BungeeSystem plugin;

    @Override
    public void execute( CommandSender sender, String[] args ){
        if ( sender instanceof ProxiedPlayer ){

            ProxiedPlayer player = ( ProxiedPlayer ) sender;


            if ( args.length == 1 ){

                if ( BungeeSystem.instance.getPlayer().get( player.getUniqueId() ).getRankId() < 2 ){
                    switch ( args[ 0 ] ){

                        //Message the list of all registered Server and their online Player
                        case "list":
                            player.sendMessage( new TextComponent( "§7§m-------------  §e§lSERVER §7§m-------------" ) );
                            player.sendMessage( " " );
                            plugin.getServers().forEach( server -> {
                                player.sendMessage( "  §8➜ §a" + server.getName() + "§8(§e" + ProxyServer.getInstance().getServers().get( server.getName() ).getPlayers().size() + "§8)" );
                            } );
                            player.sendMessage( " " );
                            player.sendMessage( new TextComponent( "§7§m-------------  §e§lSERVER §7§m-------------" ) );
                            break;

                        //Message the online Player Size of the Network
                        case "online":
                            player.sendMessage( new TextComponent( Prefixes.PREFIX.getText() + "§eOnline Spieler auf dem Netzwerk §8➜ §a" + ProxyServer.getInstance().getOnlineCount() ) );
                            break;

                            //Toggle the Maintenance state
                        case "maintenance":
                            BungeeSystem.instance.getDatabaseConnection().getBooleanAsync( "cloud", "maintenance", maintenance -> {
                                if ( maintenance ){
                                    BungeeSystem.instance.getSettings().updateMaintenance( false );
                                    player.sendMessage( new TextComponent( Prefixes.CLOUD.getText() + "§cDu hast die Wartungen deaktiviert!" ) );
                                } else{
                                    BungeeSystem.instance.getSettings().updateMaintenance( true );
                                    player.sendMessage( new TextComponent( Prefixes.CLOUD.getText() + "§cDu hast die Wartungen aktiviert!" ) );
                                }

                            }, "Proxy", "template" );

                            break;

                    }
                } else if ( BungeeSystem.instance.getPlayer().get( player.getUniqueId() ).getRankId() < 6 ){

                    switch ( args[ 0 ] ){

                        //Logged in or logged out a  Teammember into the control center
                        case "login":

                            if ( !BungeeSystem.instance.getLoggedIn().contains( player ) ){
                                BungeeSystem.instance.getLoggedIn().add( player );
                                BungeeSystem.instance.getUser().setLoggedIn( player, true );
                                player.sendMessage( new TextComponent( Prefixes.CLOUD.getText() + "§7Du hast dich §aangemeldet." ) );
                            } else{
                                BungeeSystem.instance.getLoggedIn().remove( player );
                                BungeeSystem.instance.getUser().setLoggedIn( player, false );
                                player.sendMessage( new TextComponent( Prefixes.CLOUD.getText() + "§7Du hast dich §cabgemeldet." ) );
                            }
                            break;
                    }
                } else{
                    player.sendMessage( new TextComponent( Prefixes.PREFIX.getText() + "§cDu hast dazu keine rechte!" ) );
                }
            } else{
                player.sendMessage( new TextComponent( Prefixes.PREFIX.getText() + "§e/cloud list §8➜ §7Listet alle online Server auf" ) );
                player.sendMessage( new TextComponent( Prefixes.PREFIX.getText() + "§e/cloud online §8➜ §7Zeigt die §7aktuelle §7Spieleranzahl §7auf §7dem §7Netzwerk" ) );
                player.sendMessage( new TextComponent( Prefixes.PREFIX.getText() + "§e/cloud login §8➜ §7Meldet dich im Teamsystem an." ) );
            }

        }
    }
}
