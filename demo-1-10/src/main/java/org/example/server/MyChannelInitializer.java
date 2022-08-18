package org.example.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.example.codec.MyInMsgHandler;
import org.example.codec.MyOutMsgHandler;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        // 基于换行符, 此策略需要在客户端发送消息时添加\r\n, 否则会解析不到
        socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
        // 解码 UTF_8编码
        socketChannel.pipeline().addLast(new StringDecoder(StandardCharsets.UTF_8));
        // 编码 UTF_8编码
        socketChannel.pipeline().addLast(new StringEncoder(StandardCharsets.UTF_8));
        // 自定义in消息处理器
        socketChannel.pipeline().addLast(new MyInMsgHandler());
        // 自定义out消息处理器
        socketChannel.pipeline().addLast(new MyOutMsgHandler());
    }

}
