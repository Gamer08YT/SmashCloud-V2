package net.smashmc.cloud.network.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.EmptyByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import net.smashmc.cloud.network.packet.Packet;
import net.smashmc.cloud.network.registry.PacketRegistry;
import io.netty.buffer.ByteBufInputStream;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {

    protected void decode(final ChannelHandlerContext ctx, final ByteBuf byteBuf, final List<Object> output) throws Exception {

        if(byteBuf instanceof EmptyByteBuf) return;

        final int id = byteBuf.readInt();
        final Packet packet = PacketRegistry.getPacketById(id, PacketRegistry.PacketDirection.IN);
        if (packet == null)
            throw new NullPointerException("Could not find packet by id " + id + "!");
        packet.read(new ByteBufInputStream(byteBuf));
        output.add(packet);
    }
}
