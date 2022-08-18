package org.example;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.time.LocalDateTime;

public class MyClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接报告开始\r\n报告信息: 连接到服务端, channelId: " + ctx.channel().id() + ", 报告完毕!");
        // 通知服务端连接成功
        String msg = String.format("通知服务端连接成功, %s, %s\r\n", LocalDateTime.now(), ctx.channel().localAddress());
        ctx.writeAndFlush(msg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接断开!");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String message = String.format("客户端收到: [%s] %s", LocalDateTime.now(), msg);
        ctx.writeAndFlush(message);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("连接异常!" + cause.getMessage());
    }
}
