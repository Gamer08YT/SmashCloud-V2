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

public class CommandJoinMe extends Command {

    private BungeeSystem plugin;

    public CommandJoinMe(BungeeSystem plugin) {
        super("joinme");
        this.plugin = plugin;
        this.plugin.getProxy().getPluginManager().registerCommand(this.plugin, this);
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {

        if (commandSender instanceof ProxiedPlayer) {

            ProxiedPlayer player = (ProxiedPlayer) commandSender;

            if (args.length != 1) {

                ProxyServer.getInstance().getPlayers().forEach(all -> {
                    TextComponent connect = new TextComponent("§a" + player.getServer().getInfo().getName());
                    TextComponent message = new TextComponent("§b " + player.getName() + "§7spielt auf");
                    message.addExtra(message);
                    message.addExtra(" §8| ");
                    message.addExtra(connect);

                    connect.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/join " + player.getName()));
                    connect.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aVerbinden").create()));
                    all.sendMessage(new TextComponent(message));
                });
            }
        }
    }
}
