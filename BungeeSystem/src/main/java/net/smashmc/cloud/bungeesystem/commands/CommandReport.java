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
import net.smashmc.cloud.bungeesystem.netty.packet.PacketAddReport;

public class CommandReport extends Command{

    private BungeeSystem plugin;

    public CommandReport( BungeeSystem plugin ){
        super( "report" );
        this.plugin = plugin;
        plugin.getProxy().getPluginManager().registerCommand( plugin, this );
    }

    @Override
    public void execute( CommandSender commandSender, String[] args ){
        if ( commandSender instanceof ProxiedPlayer ){
            ProxiedPlayer player = ( ProxiedPlayer ) commandSender;


            if ( args.length == 1 ){

                ProxiedPlayer target = ProxyServer.getInstance().getPlayer( args[ 0 ] );
                if ( target != null ){
                    BungeeSystem.instance.getChannels().writeAndFlush( new PacketAddReport( player.getName(), target.getName(), "" ) );
                } else{
                    player.sendMessage( new TextComponent( Prefixes.REPORT.getText() + "§cDieser Spieler ist nicht online!" ) );
                }
            }
        }
    }
}
