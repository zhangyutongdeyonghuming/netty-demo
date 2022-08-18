package org.example.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class MyServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        String message = msg.content().toString(StandardCharsets.UTF_8);
        System.out.printf("[%s]: UDP服务器收到消息: %s", LocalDateTime.now(), message);
        // 发送给客户端
        String send = "通知：收到消息\r\n";
        byte[] bytes = send.getBytes(StandardCharsets.UTF_8);
        DatagramPacket datagramPacket = new DatagramPacket(Unpooled.copiedBuffer(bytes), msg.sender());
        ctx.writeAndFlush(datagramPacket);
    }
}
