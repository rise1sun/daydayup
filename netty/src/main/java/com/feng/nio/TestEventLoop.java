package com.feng.nio;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.NettyRuntime;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author jiangfeng
 * @date 2023/10/30
 */
@Slf4j
public class TestEventLoop {
    public static void main(String[] args) {
        System.out.println(NettyRuntime.availableProcessors());
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup(2);
       /* eventExecutors.submit(()->{
            try {
                Thread.sleep(1000);
                log.debug("ok");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });*/
        eventExecutors.next().scheduleAtFixedRate(()->{
            log.debug("ok");
        },0,1, TimeUnit.SECONDS);

        log.debug("main");
    }
}
