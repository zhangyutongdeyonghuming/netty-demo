package org.example;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class MyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] byteBufArr = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(byteBufArr);
        String message = new String(byteBufArr, StandardCharsets.UTF_8);
        System.out.println(LocalDateTime.now() + "接收到消息: ");
        System.out.println(message);
    }
}
