package net.smashmc.eu.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.smashmc.cloud.network.packet.Packet;

public class NetworkHandler extends SimpleChannelInboundHandler<Packet> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        packet.handle(ctx.channel());
    }
}
