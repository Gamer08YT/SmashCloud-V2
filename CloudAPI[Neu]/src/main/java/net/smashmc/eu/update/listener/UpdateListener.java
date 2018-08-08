package net.smashmc.eu.update.listener;

import net.smashmc.eu.CloudAPI;
import net.smashmc.eu.packet.PacketOutServerInformation;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class UpdateListener implements Listener{

    private JavaPlugin plugin;

    public UpdateListener( JavaPlugin plugin ){
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoinUpdate( PlayerJoinEvent event ){
        CloudAPI.instance.getChannels().writeAndFlush( new PacketOutServerInformation( CloudAPI.instance.getServerName(), Bukkit.getOnlinePlayers().size(), Bukkit.getMaxPlayers(), CloudAPI.instance.getMotd() ));
    }

    @EventHandler
    public void onKickUpdate( PlayerKickEvent event ){
        new BukkitRunnable(){
            @Override
            public void run(){
                CloudAPI.instance.getChannels().writeAndFlush( new PacketOutServerInformation( CloudAPI.instance.getServerName(), Bukkit.getOnlinePlayers().size(), Bukkit.getMaxPlayers(), CloudAPI.instance.getMotd() ) );
            }
        }.runTaskLater( plugin, 20 );
    }

    @EventHandler
    public void onLeaveUpdate( PlayerQuitEvent event ){
        new BukkitRunnable(){
            @Override
            public void run(){
                CloudAPI.instance.getChannels().writeAndFlush( new PacketOutServerInformation( CloudAPI.instance.getServerName(), Bukkit.getOnlinePlayers().size(), Bukkit.getMaxPlayers(), CloudAPI.instance.getMotd() ) );
            }
        }.runTaskLater( plugin, 20 );
    }
}
