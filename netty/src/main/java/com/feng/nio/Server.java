package com.feng.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

import static com.feng.nio.ByteBufferUtil.debugRead;

/**
 * @author jiangfeng
 * @date 2023/10/26
 */
@Slf4j
public class Server {
    public static void main(String[] args) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(8080));
        //非阻塞
        ssc.configureBlocking(false);
        ArrayList<SocketChannel> socketChannels = new ArrayList<>();
        while (true){
            //没有链接 sc为null
            SocketChannel sc = ssc.accept();
            if(sc != null){
                log.info("connected... {}",sc);
                sc.configureBlocking(false);
                socketChannels.add(sc);
            }
            for (SocketChannel socketChannel : socketChannels) {
                int read = socketChannel.read(buffer);
                //没有读到数据
                if(read >0){
                    buffer.flip();
                    log.info("after read... {}",socketChannel);
                    debugRead(buffer);
                    buffer.clear();
                }
            }
        }

    }
}
