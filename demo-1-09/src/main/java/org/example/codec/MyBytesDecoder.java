package org.example.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class MyBytesDecoder extends ByteToMessageDecoder {

    /**
     * 基础长度
     */
    private final int baseLen = 4;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 小于基本长度 略过
        if (in.readableBytes() < baseLen) return;

        int beginIndex = 0;

        while (true) {
            // 包头开始index
            beginIndex = in.readerIndex();
            // 标记读取index
            in.markReaderIndex();
            // 我们协议以0x02起
            if (in.readByte() == 0x02) {
                break;
            }
            // 未读到略过一个字节
            in.resetReaderIndex();
            in.readByte();
            // 读过之后不满足四位略过
            if (in.readableBytes() < baseLen) {
                return;
            }
        }


        //剩余长度不足可读取数量[没有内容长度位]
        int readableCount = in.readableBytes();
        if (readableCount <= 1) {
            in.readerIndex(beginIndex);
            return;
        }

        //长度域占4字节，读取int
        ByteBuf byteBuf = in.readBytes(1);
        String msgLengthStr = byteBuf.toString(Charset.forName("GBK"));
        int msgLength = Integer.parseInt(msgLengthStr);

        //剩余长度不足可读取数量[没有结尾标识]
        readableCount = in.readableBytes();
        if (readableCount < msgLength + 1) {
            in.readerIndex(beginIndex);
            return;
        }

        ByteBuf msgContent = in.readBytes(msgLength);

        //如果没有结尾标识，还原指针位置[其他标识结尾]
        byte end = in.readByte();
        if (end != 0x03) {
            in.readerIndex(beginIndex);
            return;
        }

        out.add(msgContent.toString(StandardCharsets.UTF_8));
    }
}
