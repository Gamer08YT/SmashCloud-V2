package net.smashmc.cloud.bungeesystem.listener;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.smashmc.cloud.bungeesystem.BungeeSystem;
import net.smashmc.cloud.bungeesystem.handler.PlayerInfo;
import net.smashmc.cloud.bungeesystem.handler.Prefixes;
import org.bson.Document;

import java.util.ArrayList;

public class ListenerLogin implements Listener{

    private final String prefix = " §c§lFreunde §8§l| ";
    private BungeeSystem plugin;

    public ListenerLogin( BungeeSystem plugin ){
        this.plugin = plugin;
        plugin.getProxy().getPluginManager().registerListener( plugin, this );
    }

    @EventHandler
    public void onLogin( LoginEvent event ){

        //Insert documents into Database
        long count = plugin.instance.getDatabaseConnection().getCollection( "user" ).count( new Document( "uuid", event.getConnection().getUniqueId() ) );
        if ( count == 0 ){
            plugin.instance.getDatabaseConnection().getCollection( "user" ).insertOne( new Document( "uuid", event.getConnection().getUniqueId() ).append( "name", event.getConnection().getName() ).append( "coins", 0 ).append( "id", 7 ).append( "onlineState", true ) );
        }
        plugin.getFriendHandler().create( event.getConnection().getUniqueId(), event.getConnection().getName(), new ArrayList<>() );
        plugin.getDatabaseConnection().insertSettings( event.getConnection().getUniqueId() );
        plugin.getJedis().hset( "players", event.getConnection().getName(), event.getConnection().getUniqueId().toString() );

        //Check if maintennacemode and load player from whitelist
        boolean maintenance = BungeeSystem.instance.getDatabaseConnection().getBoolean( "cloud", "maintenance", "Proxy", "template" );
        if ( maintenance ){
            String uuid = plugin.getJedis().hget( "whitelist", event.getConnection().getName() );
            if ( uuid == null ){
                event.setCancelled( true );
                event.setCancelReason( "§cWir befinden uns aktuell in Wartungsarbeiten \n \n §7Teamspeak§8: §bts.smashmc.eu" );
            }
        }
    }

    @EventHandler
    public void onPostLogin( PostLoginEvent event ){
        ProxiedPlayer player = event.getPlayer();

        //Send the joined Player the header and Footer
        setHeaderAndFooter();

        //Cached Player and his Indormation
        BungeeSystem.instance.getUser().getRankID( player, id -> {
            BungeeSystem.instance.getUser().getPrefix( id, prefix -> {
                BungeeSystem.instance.getUser().getRank( id, rank -> {
                    BungeeSystem.instance.getPlayer().put( player.getUniqueId(), new PlayerInfo( id, prefix, rank ) );
                } );
            } );
        } );

        //Broadcast a message to the friends if a friend is joined
        ProxyServer.getInstance().getPlayers().forEach( all -> {
            if ( plugin.getFriendHandler().isFriended( player, all.getUniqueId() ) ){

                all.sendMessage( new TextComponent( Prefixes.FRIEND.getText() + "§7Dein Freund " + ChatColor.translateAlternateColorCodes( '&', BungeeSystem.instance.getPlayer().get( player.getUniqueId() ).getPrefix() ) + player.getName() + "§7 ist §7nun §aonline" ) );

            }
        } );

        //set the online state of a player to true
        plugin.getFriendHandler().setOnline( player.getUniqueId(), true );


        //Checked if a teammember is logged in in the control center
        BungeeSystem.instance.getUser().isLoggedIn( player, loggedIn -> {

            if ( loggedIn ){
                BungeeSystem.instance.getLoggedIn().add( player );
                player.sendMessage( new TextComponent( Prefixes.CLOUD.getText() + "§7Du bist im Teambereich §aangemeldet§8!" ) );
            }

        } );
    }

    @EventHandler
    public void onDisconnect( PlayerDisconnectEvent event ){
        ProxiedPlayer player = event.getPlayer();

        //Broadcast a message to the friends if a friend is quited
        plugin.getFriendHandler().setOnline( player.getUniqueId(), false );
        ProxyServer.getInstance().getPlayers().forEach( all -> {
            if ( plugin.getFriendHandler().isFriended( player, all.getUniqueId() ) ){
                all.sendMessage( new TextComponent( Prefixes.FRIEND.getText() + "§7Dein Freund " + ChatColor.translateAlternateColorCodes( '&', BungeeSystem.instance.getPlayer().get( player.getUniqueId() ).getPrefix() ) + player.getName() + "§7 ist §7nun §coffline" ) );

            }
        } );

        //Removed player from all Lists
        BungeeSystem.instance.getLoggedIn().remove( player );
        BungeeSystem.instance.getPlayer().remove( player.getUniqueId() );
    }

    @EventHandler
    public void onConnect( ServerConnectEvent event ){
        ProxiedPlayer player = event.getPlayer();

        //Send the player a message of which server he is joined
        player.sendMessage( new TextComponent( Prefixes.CLOUD.getText() + "§7Du befindest dich nun auf §6" + event.getTarget().getName() + "§8." ) );
    }

    public void setHeaderAndFooter(){
        ProxyServer.getInstance().getPlayers().forEach( all -> {
            final String header = "\n  §6§lSMASHMC§8.§fEU §8- §7Minigame Netzwerk \n §7Shop §eshop.smashmc.eu \n";
            final String footer = "\n §7Twitter §b@SmashMCEU \n §7Teamspeak §ets.smashmc.eu \n";
            all.setTabHeader( new TextComponent( header ), new TextComponent( footer ) );
        } );
    }
}
