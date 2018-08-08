package net.smashmc.cloud.bungeesystem.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.smashmc.cloud.bungeesystem.BungeeSystem;
import net.smashmc.cloud.bungeesystem.handler.Prefixes;

public class CommandReports extends Command{

    private final BungeeSystem plugin;

    public CommandReports( BungeeSystem plugin ){
        super( "reports" );
        this.plugin = plugin;
        this.plugin.getProxy().getPluginManager().registerCommand( this.plugin, this );
    }

    @Override
    public void execute( CommandSender commandSender, String[] args ){
        if ( commandSender instanceof ProxiedPlayer ){
            ProxiedPlayer player = ( ProxiedPlayer ) commandSender;

            if ( args.length == 1 ){
                if ( args[ 0 ].equalsIgnoreCase( "list" ) ){
                    if ( BungeeSystem.instance.getPlayer().get( player.getUniqueId() ).getRankId() < 6 )

                        BungeeSystem.instance.getReportHandler().listReports( player );
                } else{
                    player.sendMessage( new TextComponent( Prefixes.PREFIX.getText() + "§cDu hast dazu keine rechte!" ) );
                }


            }

        }

    }
}
