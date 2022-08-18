package org.example;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        System.out.println("连接报告开始\r\n报告信息: 连接到服务端, channelId: " + ch.id() + ", 报告完毕!");
    }
}
