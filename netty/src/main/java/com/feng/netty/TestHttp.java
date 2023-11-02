package com.feng.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import static com.google.common.net.HttpHeaders.CONTENT_LENGTH;

/**
 * @author jiangfeng
 * @date 2023/11/2
 */
@Slf4j
public class TestHttp {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup work = new NioEventLoopGroup();
        try {
            new ServerBootstrap()
                    .group(boss,work)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                            //编解码器
                            ch.pipeline().addLast(new HttpServerCodec());
                            //处理head和content
                            ch.pipeline().addLast(new SimpleChannelInboundHandler<HttpRequest>() {

                                @Override
                                protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpRequest httpRequest) throws Exception {
                                    log.debug(httpRequest.uri());
                                    DefaultFullHttpResponse response = new DefaultFullHttpResponse(
                                            httpRequest.protocolVersion(), HttpResponseStatus.OK);
                                    byte[] bytes = "<h1>Hello, world!</h1>".getBytes();
                                    response.headers().setInt(CONTENT_LENGTH,bytes.length);
                                    response.content().writeBytes(bytes);
                                    channelHandlerContext.writeAndFlush(response);
                                }
                            });

                        }
                    })
                    .bind(8081)
                    .sync().channel().closeFuture().sync();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }

    }
}
