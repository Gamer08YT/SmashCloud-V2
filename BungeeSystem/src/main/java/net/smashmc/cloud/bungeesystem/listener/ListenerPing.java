package net.smashmc.cloud.bungeesystem.listener;

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.smashmc.cloud.bungeesystem.BungeeSystem;
import org.bson.Document;

import java.util.function.Consumer;

public class ListenerPing implements Listener {

    private BungeeSystem plugin;

    public ListenerPing(BungeeSystem plugin) {
        this.plugin = plugin;
        plugin.getProxy().getPluginManager().registerListener(plugin, this);
    }

    @EventHandler
    public void onPing(ProxyPingEvent event) {
        ServerPing ping = event.getResponse();

        //Checked if the server is in maintenance and load motd from redis
        boolean maintenance = BungeeSystem.instance.getDatabaseConnection().getBoolean("cloud", "maintenance", "Proxy", "template");
        if (maintenance) {
            ping.setVersion(new ServerPing.Protocol("§4Maintenance", 1));
            ping.setDescription(ChatColor.translateAlternateColorCodes('&', getLineOne()) + "\n" + ChatColor.translateAlternateColorCodes('&', getLineTwo() + " §8» §cMaintenance"));
        } else {
            ping.setDescription(ChatColor.translateAlternateColorCodes('&', getLineOne()) + "\n" + ChatColor.translateAlternateColorCodes('&', getLineTwo()));
        }
        event.setResponse(ping);
    }
    public String getLineOne(){
        return BungeeSystem.instance.getJedis().hget("motd", "line-1");
    }
    public String getLineTwo(){
        return BungeeSystem.instance.getJedis().hget("motd", "line-2");
    }
}