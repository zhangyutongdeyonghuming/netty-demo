package org.example.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class MyBytesEncoder extends MessageToByteEncoder {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        String s = msg.toString();
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        byte[] newMsg = new byte[bytes.length + 2];
        newMsg[0] = 0x02;
        newMsg[newMsg.length - 1] = 0x03;
        System.arraycopy(bytes, 0, newMsg, 1, bytes.length);
        out.writeInt(newMsg.length);
        out.writeBytes(newMsg);
    }
}
