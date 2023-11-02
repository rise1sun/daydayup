package com.feng.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @author jiangfeng
 * @date 2023/11/2
 */
public class TestRedis {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup work = new NioEventLoopGroup();
        try {
         final byte[] LINE = {13,10};
            Channel    channel = new Bootstrap()
                    .group(work)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    ByteBuf buf1 = ctx.alloc().buffer();
                                    buf1.writeBytes("*2".getBytes());
                                    buf1.writeBytes(LINE);
                                    buf1.writeBytes("$4".getBytes());
                                    buf1.writeBytes(LINE);
                                    buf1.writeBytes("auth".getBytes());
                                    buf1.writeBytes(LINE);
                                    buf1.writeBytes("$11".getBytes());
                                    buf1.writeBytes(LINE);
                                    buf1.writeBytes("123456@qwer".getBytes());
                                    buf1.writeBytes(LINE);
                                    ctx.writeAndFlush(buf1);

                                    //连接成功发送协议
                                    ByteBuf buf = ctx.alloc().buffer();
                                    buf.writeBytes("*3".getBytes());
                                    buf.writeBytes(LINE);
                                    buf.writeBytes("$3".getBytes());
                                    buf.writeBytes(LINE);
                                    buf.writeBytes("set".getBytes());
                                    buf.writeBytes(LINE);
                                    buf.writeBytes("$4".getBytes());
                                    buf.writeBytes(LINE);
                                    buf.writeBytes("name".getBytes());
                                    buf.writeBytes(LINE);
                                    buf.writeBytes("$13".getBytes());
                                    buf.writeBytes(LINE);
                                    buf.writeBytes("fengfengfeng2".getBytes());
                                    buf.writeBytes(LINE);
                                    ctx.writeAndFlush(buf);
                                }

                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    //发送完成后 打印返回后的内柔
                                    ByteBuf byteBuf = (ByteBuf) msg;
                                    System.out.println(byteBuf.toString(Charset.defaultCharset()));
                                }
                            });

                        }
                    })
                    .connect(new InetSocketAddress("192.168.2.111", 6379))
                    .sync().channel();
            channel.closeFuture().sync();
     }catch (Exception e){
         throw new RuntimeException(e);
     }finally {
            work.shutdownGracefully();
     }
    }
}
