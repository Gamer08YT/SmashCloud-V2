package net.smashmc.eu.packet;

import io.netty.buffer.ByteBufOutputStream;
import net.smashmc.cloud.network.packet.Packet;

import java.io.IOException;

public class PacketOutAddReport implements Packet {

    private String playerName;
    private String targetName;
    private String reason;

    public PacketOutAddReport() {
    }

    public PacketOutAddReport(String playerName, String targetName, String reason) {
        this.playerName = playerName;
        this.targetName = targetName;
        this.reason = reason;
    }

    @Override
    public void write(ByteBufOutputStream byteBuf) {
        try {
            byteBuf.writeUTF(this.playerName);
            byteBuf.writeUTF(this.targetName);
            byteBuf.writeUTF(this.reason);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
