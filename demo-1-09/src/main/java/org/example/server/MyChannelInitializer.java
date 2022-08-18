package org.example.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.example.codec.MyBytesDecoder;
import org.example.codec.MyBytesEncoder;

public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        // 自定义解码bytes
        socketChannel.pipeline().addLast(new MyBytesDecoder());
        // 自定义编码字符集处理
        socketChannel.pipeline().addLast(new MyBytesEncoder());
        // 自定义处理器
        socketChannel.pipeline().addLast(new MyServerHandler());
    }

}
