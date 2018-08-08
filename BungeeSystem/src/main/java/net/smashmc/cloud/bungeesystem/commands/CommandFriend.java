package net.smashmc.cloud.bungeesystem.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.smashmc.cloud.bungeesystem.BungeeSystem;
import net.smashmc.cloud.bungeesystem.database.Friend;
import net.smashmc.cloud.bungeesystem.handler.Prefixes;

import java.util.UUID;

import static net.smashmc.cloud.bungeesystem.database.Friend.FriendHandler.getNameFromUUID;

public class CommandFriend extends Command{

    private final BungeeSystem plugin;

    public CommandFriend( BungeeSystem plugin ){
        super( "friend" );
        this.plugin = plugin;
        plugin.getProxy().getPluginManager().registerCommand( plugin, this );
    }

    @Override
    public void execute( CommandSender commandSender, String[] args ){
        if ( commandSender instanceof ProxiedPlayer ){

            ProxiedPlayer player = ( ProxiedPlayer ) commandSender;

            if ( args.length == 2 ){
                ProxiedPlayer target = ProxyServer.getInstance().getPlayer( args[ 1 ] );
                switch ( args[ 0 ] ){

                    //Sent your target a message with your invite
                    case "add":
                        if ( !plugin.getFriendHandler().isFriended( player, target.getUniqueId() ) ){
                            if ( plugin.getFriendHandler().getFriendSize( player ) < plugin.getFriendHandler().getMaxFriendRange( player.getUniqueId() ) ){
                                addFriend( player, target );
                            } else{
                                player.sendMessage( new TextComponent( Prefixes.FRIEND.getText() + "§cDu kannst §ckeine §cweiteren §cFreunde §chinzufügen!" ) );
                            }
                        } else{
                            player.sendMessage( new TextComponent( Prefixes.FRIEND.getText() + "§cDu bist bereits mit diesem Spieler befreundet!" ) );
                        }
                        break;

                    //Add a new Friend an save into Database
                    case "accept":
                        if ( !plugin.getFriendHandler().isFriended( player, target.getUniqueId() ) ){
                            acceptFriend( player, target );
                        } else{
                            player.sendMessage( new TextComponent( Prefixes.FRIEND.getText() + "§cDu bist bereits mit diesem Spieler befreundet!" ) );
                        }
                        break;

                    //Deny your invite and send the sender a message
                    case "deny":
                        if ( !plugin.getFriendHandler().isFriended( player, target.getUniqueId() ) ){
                            denyFriend( player, target );
                        } else{
                            player.sendMessage( new TextComponent( Prefixes.FRIEND.getText() + "§cDu bist bereits mit diesem Spieler befreundet!" ) );
                        }
                        break;
                }
            }
            if ( args.length == 1 ){
                switch ( args[ 0 ] ){

                    //List all your friends
                    case "list":
                        player.sendMessage( new TextComponent( "§7§m--------- §c Freunde §7§m---------" ) );
                        player.sendMessage( " " );
                        for ( UUID uuid : plugin.getFriendHandler().getFriendList( player ) ){
                            String playerName = getNameFromUUID( uuid );

                            BungeeSystem.instance.getUser().getRankID(uuid, id ->{
                                BungeeSystem.instance.getUser().getPrefix( id, prefix ->{
                                    player.sendMessage( "§8» §a" + ChatColor.translateAlternateColorCodes( '&', prefix) + playerName + " §8(§e" + ChatColor.translateAlternateColorCodes( '&', plugin.getFriendHandler().getStatus( uuid ) ) + "§8) §8| " + ( plugin.getFriendHandler().isOnline( uuid ) ? "§aOnline" : "§cOffline" ) );
                                });
                            }  );
                        }
                        player.sendMessage( " " );
                        player.sendMessage( new TextComponent( "§7§m--------- §c Freunde §7§m---------" ) );
                        break;

                }
            }
        }
    }

    public void addFriend( ProxiedPlayer player, ProxiedPlayer target ){
        if ( target != null ){
            if ( !target.getName().equals( player.getName() ) ){
                player.sendMessage( new TextComponent( Prefixes.FRIEND.getText() + "§7Du hast §e" + target.getName() + " §7eine §7Freundschaftsanfrage §7gesendet" ) );
                TextComponent accept = new TextComponent( "§aannehmen" );
                TextComponent deny = new TextComponent( "§cablehnen" );
                TextComponent message = new TextComponent( Prefixes.FRIEND.getText() + "§7Freundschaftsanfrage " );
                message.addExtra( accept );
                message.addExtra( " §8| " );
                message.addExtra( deny );

                accept.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/friend accept " + player.getName() ) );
                accept.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( "§7Anfrage §aannehmen" ).create() ) );
                deny.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/friend deny " + player.getName() ) );
                deny.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( "§7Anfrage §cablehnen" ).create() ) );

                target.sendMessage( new TextComponent( Prefixes.FRIEND.getText() + "§7Du hast von §e" + player.getName() + "§7 eine §7Freundschaftsanfrage §7bekommen." ) );
                target.sendMessage( new TextComponent( message ) );
                plugin.getJedis().hset( "friend-invites", target.getUniqueId().toString(), player.getUniqueId().toString() );

            } else{
                player.sendMessage( new TextComponent( Prefixes.FRIEND.getText() + "§cDu kannst dich nicht selber hinzufügen!" ) );
            }
        } else{
            player.sendMessage( new TextComponent( Prefixes.FRIEND.getText() + "§cDieser Spieler ist aktuell offline!" ) );
        }

    }

    public void acceptFriend( ProxiedPlayer player, ProxiedPlayer target ){
        plugin.getFriendHandler().addFriend( player.getUniqueId(), new Friend( target.getUniqueId() ) );
        player.sendMessage( new TextComponent( Prefixes.FRIEND.getText() + "§7Du bist nun mit §e" + target.getName() + "§7 befreundet!" ) );

        plugin.getFriendHandler().addFriend( target.getUniqueId(), new Friend( player.getUniqueId() ) );
        target.sendMessage( new TextComponent( Prefixes.FRIEND.getText() + "§7Der Spieler §e" + player.getName() + "§7 hat deine §7Anfrage §aangenommen!" ) );
        plugin.getJedis().hdel( "friend-invites", player.getUniqueId().toString() );
    }

    public void denyFriend( ProxiedPlayer player, ProxiedPlayer target ){
        target.sendMessage( new TextComponent( Prefixes.FRIEND.getText() + "§e" + player.getName() + " §chat deine Anfrage abgelehnt!" ) );
        player.sendMessage( new TextComponent( Prefixes.FRIEND.getText() + "§cDu hast die Anfrage abglehnt" ) );
    }
}
