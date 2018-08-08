package net.smashmc.cloud.bungeesystem.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.smashmc.cloud.bungeesystem.BungeeSystem;
import net.smashmc.cloud.bungeesystem.handler.Prefixes;

public class CommandPing extends Command {

    private final BungeeSystem plugin;

    public CommandPing(BungeeSystem plugin) {
        super("ping");
        this.plugin = plugin;
        plugin.getProxy().getPluginManager().registerCommand(plugin, this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;
            player.sendMessage(Prefixes.PREFIX.getText() + "§7Dei Ping ist §8➜ §e" + player.getPing() + "§7ms§8.");
        }
    }
}
