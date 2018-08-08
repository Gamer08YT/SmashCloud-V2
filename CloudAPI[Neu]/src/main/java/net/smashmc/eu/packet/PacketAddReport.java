package net.smashmc.eu.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.Channel;
import net.smashmc.cloud.network.packet.Packet;
import net.smashmc.eu.utils.report.ReportItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import java.io.IOException;

public class PacketAddReport implements Packet {

    private Inventory inventory;

    private String playerName;
    private String targetName;
    private String reason;

    public PacketAddReport() {
    }

    public PacketAddReport(String playerName, String targetName, String reason) {
        this.playerName = playerName;
        this.targetName = targetName;
        this.reason = reason;
    }

    @Override
    public void read(ByteBufInputStream byteBuf) {
        try {
            this.playerName = byteBuf.readUTF();
            this.targetName = byteBuf.readUTF();
            this.reason = byteBuf.readUTF();
            System.out.print("report was load");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(ByteBufOutputStream byteBuf) {
        try {
            byteBuf.writeUTF(this.playerName);
            byteBuf.writeUTF(this.targetName);
            byteBuf.writeUTF(this.reason);
            System.out.print("report was write");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Packet handle(Channel channel) {
        Bukkit.getOnlinePlayers().forEach(all -> {
            if (all.getName().equals(playerName)) {
                this.inventory = Bukkit.createInventory(null, 27, "§cReport §8➜ §e" + this.targetName);
                getItemstacks();
                all.openInventory(inventory);
            }
        });
        System.out.print("inv was load");
        return null;
    }

    public void getItemstacks() {
        for (int i = 0; i < 9; i++) {
            this.inventory.setItem(i, new ReportItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 7).setDisplayName(" ").build());
        }
        this.inventory.setItem(9, new ReportItemBuilder(Material.IRON_SWORD).setDisplayName("§eHacking").build());
        this.inventory.setItem(13, new ReportItemBuilder(Material.GOLD_HELMET).setDisplayName("§eTeaming").build());
        this.inventory.setItem(17, new ReportItemBuilder(Material.PAPER).setDisplayName("§eChatverhalten").build());
        for (int i = 18; i < 27; i++) {
            this.inventory.setItem(i, new ReportItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 7).setDisplayName(" ").build());
        }
    }

}
