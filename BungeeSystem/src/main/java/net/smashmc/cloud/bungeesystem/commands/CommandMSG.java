package net.smashmc.cloud.bungeesystem.commands;


import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.smashmc.cloud.bungeesystem.BungeeSystem;

public class CommandMSG extends Command {

    private BungeeSystem plugin;

    public CommandMSG(BungeeSystem plugin) {
        super("msg");
        this.plugin = plugin;
        plugin.getProxy().getPluginManager().registerCommand(plugin, this);
    }

    private final String prefix = " §c§lFreunde §8§l| ";

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;
            if (args.length > 1) {
                ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
                if (target != null) {
                    if (target != player) {
                        if (BungeeSystem.instance.getFriendHandler().isFriended(player, target.getUniqueId())) {
                            String msg = "";
                            for (int i = 1; i < args.length; i++) {
                                msg = msg + args[i] + " ";
                            }
                            player.sendMessage(new TextComponent(prefix + "§7Du §8-> §e" + target.getName() + "§8: §f" + msg));
                            target.sendMessage(new TextComponent(prefix + "§e" + player.getName() + " §8-> §7Du §8: §f" + msg));
                        } else {
                            player.sendMessage(new TextComponent(prefix + "§cDu bist nicht §cmit §cdiesem §cSpieler §cbefreundet!"));
                        }

                    } else {
                        player.sendMessage(new TextComponent(prefix + "§cDu darfst dir §cnicht §cselber §ceine §cNachricht §csenden."));
                    }
                } else {
                    player.sendMessage(new TextComponent(prefix + "§cDieser Spieler ist nicht online!"));
                }

            }

        }
    }
}
