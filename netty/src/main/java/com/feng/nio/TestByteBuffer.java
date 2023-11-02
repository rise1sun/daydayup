package com.feng.nio;

import java.nio.ByteBuffer;

import static com.feng.nio.ByteBufferUtil.debugAll;

/**
 * @author jiangfeng
 * @date 2023/10/25
 */
public class TestByteBuffer {
    public static void main(String[] args) {
        ByteBuffer source = ByteBuffer.allocate(100);
        source.put("helloword\niamfine\nshibushi".getBytes());
        split(source);
        source.put("hahah\n".getBytes());
        split(source);

    }

    private static void split(ByteBuffer source) {
        //切换为读模式
        source.flip();
        for (int i = 0; i < source.limit(); i++) {

            if(source.get(i)=='\n'){
                int length = i-source.position()+1;
                //开辟一块合适大小的缓冲区
                ByteBuffer target = ByteBuffer.allocate(length);
                for (int j = 0; j < length; j++) {
                    target.put(source.get());
                }
                debugAll(target);

            }

        }
        source.compact();



    }
}
