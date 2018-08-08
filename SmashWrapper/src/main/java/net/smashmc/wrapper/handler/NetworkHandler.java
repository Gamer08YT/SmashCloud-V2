package net.smashmc.wrapper.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import net.smashmc.cloud.network.packet.Packet;
import net.smashmc.wrapper.Wrapper;
import net.smashmc.wrapper.packet.PacketOutRegisterWrapper;

/**
 * @author Mike
 * @version 1.0
 */
public class NetworkHandler extends SimpleChannelInboundHandler<Packet> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().writeAndFlush(new PacketOutRegisterWrapper(Wrapper.instance.getWrapperName()), ctx.channel().voidPromise());
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