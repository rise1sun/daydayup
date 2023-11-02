package com.feng.nio;

import io.netty.buffer.ByteBuf;
import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import static io.netty.buffer.ByteBufUtil.appendPrettyHexDump;
import static jdk.nashorn.internal.runtime.regexp.joni.encoding.CharacterType.NEWLINE;

/**
 * @author jiangfeng
 * @date 2023/10/31
 */
@Slf4j
public class NettyPromise {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        EventLoop eventloop = new NioEventLoopGroup().next();
        //promise容器
        DefaultPromise<Integer> promise = new DefaultPromise<>(eventloop);
       new Thread(()->{
           log.debug("开始计算：");
           try {
               int i = 1/0;
               Thread.sleep(1000);
               promise.setSuccess(80);
           } catch (InterruptedException e) {
              promise.setFailure(e);
           }
       }).start();
       log.debug("等待计算结果");
       log.debug("获取计算结果，{}",promise.get());
    }

    public static void log(ByteBuf buffer) {
        int length = buffer.readableBytes();
        int rows = length / 16 + (length % 15 == 0 ? 0 : 1) + 4;
        StringBuilder buf = new StringBuilder(rows * 80 * 2)
                .append("read index:").append(buffer.readerIndex())
                .append(" write index:").append(buffer.writerIndex())
                .append(" capacity:").append(buffer.capacity())
                .append(NEWLINE);
        appendPrettyHexDump(buf, buffer);
        System.out.println(buf.toString());
    }
}
