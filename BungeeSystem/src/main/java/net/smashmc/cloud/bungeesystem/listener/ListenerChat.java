package net.smashmc.cloud.bungeesystem.listener;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.smashmc.cloud.bungeesystem.BungeeSystem;

import java.util.Arrays;
import java.util.List;

public class ListenerChat implements Listener{

    private List<String> blockedWords = Arrays.asList( "/help", "/pl", "/bukkit", "/me", "/deop", "/op", "/bukkit:", "/plugins", "/?:", "/version:", "/about:", "/?" );

    private BungeeSystem plugin;

    public ListenerChat( BungeeSystem plugin ){
        this.plugin = plugin;
        plugin.getProxy().getPluginManager().registerListener( plugin, this );
    }

    @EventHandler
    public void onChat( ChatEvent event ){
        ProxiedPlayer player = ( ProxiedPlayer ) event.getSender();

        //Disable Plugins for Player
        String msgreal = event.getMessage().split( " " )[ 0 ];
        if ( BungeeSystem.instance.getPlayer().get( player.getUniqueId() ).getRankId() > 2 ){
            if ( blockedWords.contains( msgreal ) ){
                event.setCancelled( true );
                player.sendMessage( new TextComponent( "§cDazu hast du leider keine Rechte" ) );
            }
        }

        //Teamchat Command
        if ( event.getMessage().startsWith( "/tc" ) ){
            if ( BungeeSystem.instance.getPlayer().get( player.getUniqueId() ).getRankId() < 6 ){
                ProxyServer.getInstance().getPlayers().forEach( all -> {
                    if ( BungeeSystem.instance.getPlayer().get( all.getUniqueId() ).getRankId() < 6 && BungeeSystem.instance.getLoggedIn().contains( all ) ){
                        String msg = event.getMessage().replace( "/tc", "" );
                        final String prefix = ChatColor.translateAlternateColorCodes( '&', BungeeSystem.instance.getPlayer().get( player.getUniqueId() ).getPrefix() );
                        all.sendMessage( new TextComponent( "§cTeamchat §8➜ " + prefix  + player.getName()+ "§8 : §f" + msg ) );
                        event.setCancelled( true );
                    }
                } );
            }
        }


    }

    @EventHandler
    public void onTab( final TabCompleteEvent event ){
        if ( event.getSender() instanceof ProxiedPlayer ){
            final ProxiedPlayer player = ( ProxiedPlayer ) event.getSender();

            //Disable Tamcomplete
            final String[] args = event.getCursor().split( " " );
            final String checked = ( ( args.length > 0 ) ? args[ args.length - 1 ] : event.getCursor() ).toLowerCase();
            if ( checked.equals( "/" ) || checked.equals( "/bukkit:ver" ) || checked.equals( "/bukkit:about" ) || checked.equals( "/bukkit:version" ) || checked.equals( "/bukkit:?" ) || checked.equals( "/bukkit:pl" ) || checked.equals( "/bukkit:plugins" ) || checked.equals( "/ver" ) || checked.equals( "/about" ) || checked.equals( "/version" ) || checked.equals( "/?" ) || checked.equals( "/pl" ) || checked.equals( "/icanhasbukkit" ) || checked.equals( "/plugins" ) || checked.equals( "/help" ) || checked.equals( "/a" ) || checked.equals( "/b" ) || checked.equals( "/c" ) || checked.equals( "/d" ) || checked.equals( "/e" ) || checked.equals( "/f" ) || checked.equals( "/g" ) || checked.equals( "/h" ) || checked.equals( "/i" ) || checked.equals( "/j" ) || checked.equals( "/k" ) || checked.equals( "/l" ) || checked.equals( "/m" ) || checked.equals( "/n" ) || checked.equals( "/\u00f1" ) || checked.equals( "/o" ) || checked.equals( "/p" ) || checked.equals( "/q" ) || checked.equals( "/r" ) || checked.equals( "/s" ) || checked.equals( "/t" ) || checked.equals( "/u" ) || checked.equals( "/v" ) || checked.equals( "/w" ) || checked.equals( "/x" ) || checked.equals( "/y" ) || checked.equals( "/z" ) ){
                event.setCancelled( true );
                player.sendMessage( new TextComponent( "§cDazu hast du leider keine Rechte" ) );
            }
        }
    }
}
