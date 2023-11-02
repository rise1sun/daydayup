package com.feng.nio;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * @author jiangfeng
 * @date 2023/10/27
 */
@Slf4j
public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        ChannelFuture channelFuture = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                        nioSocketChannel.pipeline().addLast(new StringEncoder());
                        nioSocketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                if(msg instanceof ByteBuf){
                                    ByteBuf byteBuf = (ByteBuf) msg;
                                    System.out.println(byteBuf.toString(Charset.defaultCharset()));
                                }
                            }
                        });


                    }
                })
                .connect(new InetSocketAddress("localhost", 8080));
        //方法一
        ChannelFuture sync = channelFuture.sync();
        Channel channel = channelFuture.channel();

        new Thread(()->{
            Scanner scanner = new Scanner(System.in);
            while (true){
                String s = scanner.nextLine();
                if("q".equals(s)){
                    channel.close();
                    break;
                }
                channel.writeAndFlush(s);
            }
        },"input ").start();

        ChannelFuture closeFuture = channel.closeFuture();
        log.debug("wait close ...");
        closeFuture.sync();
       // group.shutdownGracefully();
        log.debug("finish close ...");
        //方法二
//        channelFuture.addListener((ChannelFutureListener) channelFuture1 -> {
//            Channel channel1 = channelFuture1.channel();
//
//        });
    /*    System.out.println(channel);
        System.out.println("");*/

    }
}
