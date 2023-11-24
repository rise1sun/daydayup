package cn.itcast.protocol.client.handler;

import cn.itcast.message.RpcRequestMessage;
import cn.itcast.protocol.MessageCodecSharable;
import cn.itcast.protocol.ProcotolFrameDecoder;
import cn.itcast.protocol.SequenceIdGenerator;
import cn.itcast.server.service.HelloService;
import cn.itcast.server.service.UserService;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Proxy;

/**
 * @author jiangfeng
 * @date 2023/11/7
 */
@Slf4j
public class RpcClientByMySelf {

    private static final Object LOCK = new Object();
    private static Channel channel = null;

    public static void main(String[] args) {
        //每次使用反射调用,不仅麻烦,改变了用户使用习惯,封装成代理模式调用更加优雅
        HelloService helloService = getProxyService1(HelloService.class);
        System.out.println(helloService.sayHello("jiang"));
        System.out.println(helloService.sayHello("wanmgwu"));

    }

    private static <T> T getProxyService1(Class<T> service) {
        ClassLoader classLoader = service.getClassLoader();
        Class<?>[] interfaces = new Class[]{service};
        Object o = Proxy.newProxyInstance(classLoader, interfaces, (proxy, method, args) -> {
            int sequenceId = SequenceIdGenerator.nextId();
            RpcRequestMessage msg = new RpcRequestMessage(
                    sequenceId,
                    service.getName(),
                    method.getName(),
                    method.getReturnType(),
                    method.getParameterTypes(),
                    args);
            Channel channel = getChannel1();
            channel.writeAndFlush(msg);
            //盛放结果的容器
            DefaultPromise<Object> promise = new DefaultPromise<>(channel.eventLoop());
            RpcResponseMessageHandler.PROMISES.put(sequenceId, promise);
            //异步操作需要阻塞住 直到获取结果
            promise.await();
            if (promise.isSuccess()) {
                return promise.getNow();
            }else{
                throw new RuntimeException(promise.cause());
            }
        });
        return (T) o;
    }

    //确保只初始化一次,使用单例模型
    private static Channel getChannel1() {
        if (channel != null) {
            return channel;
        }
        synchronized (LOCK) {
            if (channel != null) {
                return channel;
            }
            initChannel();
        }
        return channel;
    }

    // 初始化 channel 方法
    private static void initChannel() {
        NioEventLoopGroup group = new NioEventLoopGroup();
        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
        MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
        RpcResponseMessageHandler RPC_HANDLER = new RpcResponseMessageHandler();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.group(group);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new ProcotolFrameDecoder());
                ch.pipeline().addLast(LOGGING_HANDLER);
                ch.pipeline().addLast(MESSAGE_CODEC);
                ch.pipeline().addLast(RPC_HANDLER);
            }
        });
        try {
            channel = bootstrap.connect("localhost", 8080).sync().channel();
            channel.closeFuture().addListener(future -> {
                group.shutdownGracefully();
            });
        } catch (Exception e) {
            log.error("client error", e);
        }
    }
}
