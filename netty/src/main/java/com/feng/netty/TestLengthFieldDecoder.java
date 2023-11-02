package com.feng.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.nio.charset.Charset;

/**
 * @author jiangfeng
 * @date 2023/11/2
 */
public class TestLengthFieldDecoder {
    public static void main(String[] args) {
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(
                //maxFrameLength:最大接受长度   lengthFieldOffset:长度字段的偏移量, 0代表从头开始计算
                //lengthFieldLength：代表长度字段的大小，4代表四个字节  lengthAdjustment:长度字段往后调整的偏移量，1代表长度字段往后一位，常用于存协议的版本号
                //initiaBytesToStrip:头部跳过的字节数，0 代表取全部的字节
                new LengthFieldBasedFrameDecoder(
                        1024,0,4,1,0),
                //日志
                new LoggingHandler(LogLevel.DEBUG)
        );

        //发送消息
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        send(buffer,"hello, world");
        send(buffer,"Hi!");
        send(buffer,"fengfengfeng1");
        embeddedChannel.writeInbound(buffer);


    }

    private static void send(ByteBuf buffer, String content) {
        byte[] bytes = content.getBytes(Charset.defaultCharset());
        int length = bytes.length;
        buffer.writeInt(length);
        buffer.writeByte(1);
        buffer.writeBytes(bytes);

    }
}
