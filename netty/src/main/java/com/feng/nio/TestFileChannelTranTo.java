package com.feng.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @author jiangfeng
 * @date 2023/10/25
 */
public class TestFileChannelTranTo {
    public static void main(String[] args) throws IOException {
        try( FileChannel from = new FileInputStream("log.txt").getChannel();
             FileChannel to = new FileOutputStream("to.txt").getChannel();) {
            //一次最大传输2G，大于2G只保留2G数据
            // from.transferTo(0,from.size(),to);
            //left 剩余未传输的数量
            //大于2G文件即可分段传输
            for (long left = from.size();left>0;){
                left  -= from.transferTo(from.size()-left, left, to);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
