package net.smashmc.cloud.bungeesystem.handler.report;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.smashmc.cloud.bungeesystem.BungeeSystem;

public class ReportHandler{

    public void listReports( ProxiedPlayer player ){
        player.sendMessage( new TextComponent( "§7§m--------- §c Report List §7§m---------" ) );
        player.sendMessage( " " );
        if ( BungeeSystem.instance.getReportList().size() < 1 ){
            player.sendMessage( new TextComponent( "§eEs sind keine Reports verfügbar!" ) );
        }
        BungeeSystem.instance.getReportList().forEach( report -> {

            player.sendMessage( new TextComponent( "§7Spieler§8| §e" + report.getPlayerName() ) );
            player.sendMessage( new TextComponent( "§7Reporteter Spieler§8| §e" + report.getTargetName() ) );
            player.sendMessage( new TextComponent( "§7Grund Spieler§8| §e" + report.getReason() ) );
            player.sendMessage( " " );
            TextComponent accept = new TextComponent( "§aannehmen" );
            TextComponent message = new TextComponent( " §7Report " );
            message.addExtra( accept );

            accept.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/report accept " + player.getName() ) );
            accept.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( "§cReport §aannehmen" ).create() ) );

            player.sendMessage( new TextComponent( message ) );
        } );
        player.sendMessage( new TextComponent( "§7§m--------- §c Report List §7§m---------" ) );
    }

}
