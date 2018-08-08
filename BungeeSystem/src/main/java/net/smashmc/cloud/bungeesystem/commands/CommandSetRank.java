package net.smashmc.cloud.bungeesystem.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.smashmc.cloud.bungeesystem.BungeeSystem;
import net.smashmc.cloud.bungeesystem.handler.Prefixes;

import java.util.UUID;

public class CommandSetRank extends Command{

    private final BungeeSystem plugin;

    public CommandSetRank( BungeeSystem plugin ){
        super( "setrank" );
        this.plugin = plugin;
        plugin.getProxy().getPluginManager().registerCommand( plugin, this );
    }

    @Override
    public void execute( CommandSender commandSender, String[] args ){

        if ( commandSender instanceof ProxiedPlayer ){

            ProxiedPlayer player = ( ProxiedPlayer ) commandSender;

            if ( BungeeSystem.instance.getPlayer().get( player.getUniqueId() ).getRankId() == 1 ){
                if ( args.length == 2 ){

                    ProxiedPlayer target = ProxyServer.getInstance().getPlayer( args[ 0 ] );

                    String uuid = plugin.getJedis().hget( "players", args[0] );
                    switch ( args[ 1 ] ){
                        case "Admin":
                            if ( target != null ){
                                if ( !BungeeSystem.instance.getPlayer().get( target.getUniqueId() ).getRank().equals( args[ 1 ] ) ){
                                    BungeeSystem.instance.getUser().setRank( target, 1 );
                                    target.disconnect( new TextComponent( "§6§lSmash§8.§fEU \n §cDu hast einen neueh Rang erhalten! \n §cRang§8: §e" + args[ 1 ] ) );
                                    player.sendMessage( new TextComponent( Prefixes.CLOUD.getText() + "§7Du hast dem Spieler §b" + target.getName() + "§7 den Rang §e" + args[ 1 ] + "§7 gegeben!" ) );
                                } else{
                                    player.sendMessage( new TextComponent( Prefixes.CLOUD.getText() + "§cDer Spieler hat bereits diesen Rang!" ) );
                                }
                            }else{
                                if(uuid != null){
                                    BungeeSystem.instance.getUser().setRank( UUID.fromString( uuid ), 1 );
                                    player.sendMessage( new TextComponent( Prefixes.CLOUD.getText() + "§7Du hast dem Spieler §b" + args[0] + "§7 den Rang §e" + args[ 1 ] + "§7 gegeben!" ) );
                                }else{
                                    player.sendMessage( new TextComponent( Prefixes.CLOUD.getText() + "§cDieser Spieler existiert nicht in der Datenbank!" ) );
                                }
                            }
                            break;
                        case "Developer":
                            if ( target != null ){
                                if ( !BungeeSystem.instance.getPlayer().get( target.getUniqueId() ).getRank().equals( args[ 1 ] ) ){
                                    BungeeSystem.instance.getUser().setRank( target, 2 );
                                    target.disconnect( new TextComponent( "§6§lSmash§8.§fEU \n §cDu hast einen neueh Rang erhalten! \n §cRang§8: §e" + args[ 1 ] ) );
                                    player.sendMessage( new TextComponent( Prefixes.CLOUD.getText() + "§7Du hast dem Spieler §b" + target.getName() + "§7 den Rang §e" + args[ 1 ] + "§7 gegeben!" ) );
                                } else{
                                    player.sendMessage( new TextComponent( Prefixes.CLOUD.getText() + "§cDer Spieler hat bereits diesen Rang!" ) );
                                }
                            }else{
                                if(uuid != null){
                                    BungeeSystem.instance.getUser().setRank( UUID.fromString( uuid ), 2 );
                                    player.sendMessage( new TextComponent( Prefixes.CLOUD.getText() + "§7Du hast dem Spieler §b" + args[0] + "§7 den Rang §e" + args[ 1 ] + "§7 gegeben!" ) );
                                }else{
                                    player.sendMessage( new TextComponent( Prefixes.CLOUD.getText() + "§cDieser Spieler existiert nicht in der Datenbank!" ) );
                                }
                            }
                            break;
                        case "Mod":
                            if ( target != null ){
                                if ( !BungeeSystem.instance.getPlayer().get( target.getUniqueId() ).getRank().equals( args[ 1 ] ) ){
                                    BungeeSystem.instance.getUser().setRank( target, 3 );
                                    target.disconnect( new TextComponent( "§6§lSmash§8.§fEU \n §cDu hast einen neueh Rang erhalten! \n §cRang§8: §e" + args[ 1 ] ) );
                                    player.sendMessage( new TextComponent( Prefixes.CLOUD.getText() + "§7Du hast dem Spieler §b" + target.getName() + "§7 den Rang §e" + args[ 1 ] + "§7 gegeben!" ) );
                                } else{
                                    player.sendMessage( new TextComponent( Prefixes.CLOUD.getText() + "§cDer Spieler hat bereits diesen Rang!" ) );
                                }
                            }else{
                                if(uuid != null){
                                    BungeeSystem.instance.getUser().setRank( UUID.fromString( uuid ), 3 );
                                    player.sendMessage( new TextComponent( Prefixes.CLOUD.getText() + "§7Du hast dem Spieler §b" + args[0] + "§7 den Rang §e" + args[ 1 ] + "§7 gegeben!" ) );
                                }else{
                                    player.sendMessage( new TextComponent( Prefixes.CLOUD.getText() + "§cDieser Spieler existiert nicht in der Datenbank!" ) );
                                }
                            }
                            break;
                        case "Builder":
                            if ( target != null ){
                                if ( !BungeeSystem.instance.getPlayer().get( target.getUniqueId() ).getRank().equals( args[ 1 ] ) ){
                                    BungeeSystem.instance.getUser().setRank( target, 4 );
                                    target.disconnect( new TextComponent( "§6§lSmash§8.§fEU \n §cDu hast einen neueh Rang erhalten! \n §cRang§8: §e" + args[ 1 ] ) );
                                    player.sendMessage( new TextComponent( Prefixes.CLOUD.getText() + "§7Du hast dem Spieler §b" + target.getName() + "§7 den Rang §e" + args[ 1 ] + "§7 gegeben!" ) );
                                }
                            }else{
                                if(uuid != null){
                                    BungeeSystem.instance.getUser().setRank( UUID.fromString( uuid ), 4 );
                                    player.sendMessage( new TextComponent( Prefixes.CLOUD.getText() + "§7Du hast dem Spieler §b" + args[0] + "§7 den Rang §e" + args[ 1 ] + "§7 gegeben!" ) );
                                }else{
                                    player.sendMessage( new TextComponent( Prefixes.CLOUD.getText() + "§cDieser Spieler existiert nicht in der Datenbank!" ) );
                                }
                            }
                            break;
                        case "Sup":
                            if ( target != null ){
                                if ( !BungeeSystem.instance.getPlayer().get( target.getUniqueId() ).getRank().equals( args[ 1 ] ) ){
                                    BungeeSystem.instance.getUser().setRank( target, 5 );
                                    target.disconnect( new TextComponent( "§6§lSmash§8.§fEU \n §cDu hast einen neueh Rang erhalten! \n §cRang§8: §e" + args[ 1 ] ) );
                                    player.sendMessage( new TextComponent( Prefixes.CLOUD.getText() + "§7Du hast dem Spieler §b" + target.getName() + "§7 den Rang §e" + args[ 1 ] + "§7 gegeben!" ) );
                                } else{
                                    player.sendMessage( new TextComponent( Prefixes.CLOUD.getText() + "§cDer Spieler hat bereits diesen Rang!" ) );
                                }
                            }else{
                                if(uuid != null){
                                    BungeeSystem.instance.getUser().setRank( UUID.fromString( uuid ), 5 );
                                    player.sendMessage( new TextComponent( Prefixes.CLOUD.getText() + "§7Du hast dem Spieler §b" + args[0] + "§7 den Rang §e" + args[ 1 ] + "§7 gegeben!" ) );
                                }else{
                                    player.sendMessage( new TextComponent( Prefixes.CLOUD.getText() + "§cDieser Spieler existiert nicht in der Datenbank!" ) );
                                }
                            }
                            break;
                        case "VIP":
                            if ( target != null ){
                                if ( !BungeeSystem.instance.getPlayer().get( target.getUniqueId() ).getRank().equals( args[ 1 ] ) ){
                                    BungeeSystem.instance.getUser().setRank( target, 6 );
                                    target.disconnect( new TextComponent( "§6§lSmash§8.§fEU \n §cDu hast einen neueh Rang erhalten! \n §cRang§8: §e" + args[ 1 ] ) );
                                    player.sendMessage( new TextComponent( Prefixes.CLOUD.getText() + "§7Du hast dem Spieler §b" + target.getName() + "§7 den Rang §e" + args[ 1 ] + "§7 gegeben!" ) );
                                }
                            }else{
                                if(uuid != null){
                                    BungeeSystem.instance.getUser().setRank( UUID.fromString( uuid ), 6 );
                                    player.sendMessage( new TextComponent( Prefixes.CLOUD.getText() + "§7Du hast dem Spieler §b" + args[0] + "§7 den Rang §e" + args[ 1 ] + "§7 gegeben!" ) );
                                }else{
                                    player.sendMessage( new TextComponent( Prefixes.CLOUD.getText() + "§cDieser Spieler existiert nicht in der Datenbank!" ) );
                                }
                            }
                            break;
                        case "Spieler":
                            if ( target != null ){
                                if ( !BungeeSystem.instance.getPlayer().get( target.getUniqueId() ).getRank().equals( args[ 1 ] ) ){
                                    BungeeSystem.instance.getUser().setRank( target, 7 );
                                    target.disconnect( new TextComponent( "§6§lSmash§8.§fEU \n §cDu hast einen neueh Rang erhalten! \n §cRang§8: §e" + args[ 1 ] ) );
                                    player.sendMessage( new TextComponent( Prefixes.CLOUD.getText() + "§7Du hast dem Spieler §b" + target.getName() + "§7 den Rang §e" + args[ 1 ] + "§7 gegeben!" ) );
                                } else{
                                    player.sendMessage( new TextComponent( Prefixes.CLOUD.getText() + "§cDer Spieler hat bereits diesen Rang!" ) );
                                }
                            }else{
                                if(uuid != null){
                                    BungeeSystem.instance.getUser().setRank( UUID.fromString( uuid ), 7 );
                                    player.sendMessage( new TextComponent( Prefixes.CLOUD.getText() + "§7Du hast dem Spieler §b" + args[0] + "§7 den Rang §e" + args[ 1 ] + "§7 gegeben!" ) );
                                }else{
                                    player.sendMessage( new TextComponent( Prefixes.CLOUD.getText() + "§cDieser Spieler existiert nicht in der Datenbank!" ) );
                                }
                            }
                            break;
                    }
                }else{
                    player.sendMessage( new TextComponent( Prefixes.CLOUD.getText() + "§c/setrank <Spieler> <Rank>" ) );
                }
            } else{
                player.sendMessage( new TextComponent( Prefixes.PREFIX.getText() + "§cDu hast dazu keine rechte!" ) );
            }

        }

    }
}
