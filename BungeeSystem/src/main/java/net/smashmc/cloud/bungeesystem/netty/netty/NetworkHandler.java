package net.smashmc.cloud.bungeesystem.netty.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.smashmc.cloud.bungeesystem.BungeeSystem;
import net.smashmc.cloud.bungeesystem.netty.packet.PacketOutRegisterBungee;
import net.smashmc.cloud.network.packet.Packet;

/**
 * @author Mike
 * @version 1.0
 */
public class NetworkHandler extends SimpleChannelInboundHandler<Packet> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().writeAndFlush(new PacketOutRegisterBungee(BungeeSystem.instance.getProxyName()), ctx.channel().voidPromise());
    }

    /**
     * Handle the incoming packets
     * @param ctx ChannelHandler context for handling channel
     * @param packet Packet which you want to handle
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        packet.handle(ctx.channel());
    }
}