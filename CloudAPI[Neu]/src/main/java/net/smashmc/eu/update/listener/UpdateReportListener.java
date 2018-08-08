package net.smashmc.eu.update.listener;

import net.smashmc.eu.CloudAPI;
import net.smashmc.eu.packet.PacketAddReport;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class UpdateReportListener implements Listener{

    private final JavaPlugin plugin;
    private String prefix = " §c§lReport §8| ";

    public UpdateReportListener( JavaPlugin plugin ){
        this.plugin = plugin;

    }

    @EventHandler
    public void onClick( InventoryClickEvent event ){
        Player player = ( Player ) event.getWhoClicked();
        if ( event.getCurrentItem() != null ){
            if ( event.getCurrentItem().getItemMeta().hasDisplayName() && event.getCurrentItem().hasItemMeta() ){
                if(event.getInventory().getTitle() != null){
                    String[] title = event.getInventory().getTitle().split( "§8➜" );
                    String targetName = ChatColor.stripColor( title[ 1 ] );

                    if ( targetName != null ){
                        switch ( event.getCurrentItem().getItemMeta().getDisplayName() ){

                            case "§eHacking":
                                event.getView().close();
                                CloudAPI.instance.getChannels().writeAndFlush( new PacketAddReport( player.getName(), targetName, "Hacking" ) );
                                player.sendMessage( prefix + "§cDu hast §e" + targetName + " §7reportet" );
                                player.playSound( player.getLocation(), Sound.CLICK, 4, 1 );
                                break;

                            case "§eTeaming":
                                event.getView().close();
                                CloudAPI.instance.getChannels().writeAndFlush( new PacketAddReport( player.getName(), targetName, "Teaming" ) );
                                player.sendMessage( prefix + "§cDu hast §e" + targetName + " §7reportet" );
                                player.playSound( player.getLocation(), Sound.CLICK, 4, 1 );
                                break;
                            case "§eChatverhalten":
                                event.getView().close();
                                CloudAPI.instance.getChannels().writeAndFlush( new PacketAddReport( player.getName(), targetName, "Chatverhalten" ) );
                                player.sendMessage( prefix + "§cDu hast §e" + targetName + " §7reportet" );
                                player.playSound( player.getLocation(), Sound.CLICK, 4, 1 );
                                break;


                        }
                    }
                }
            }
        }
    }
}
