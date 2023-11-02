package com.feng.nio;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

import static java.nio.file.Files.*;
import static java.nio.file.Paths.*;

/**
 * @author jiangfeng
 * @date 2023/10/25
 */
public class TestFileWalkTree {
    public static void main(String[] args) throws IOException {
        final AtomicInteger directoryCount = new AtomicInteger();
        final AtomicInteger fileCount = new AtomicInteger();
        walkFileTree(get("D:\\BaiduNetdisk"),
                new SimpleFileVisitor<Path>(){
                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                        directoryCount.incrementAndGet();
                        System.out.println("====>"+dir);
                        return super.preVisitDirectory(dir, attrs);
                    }

                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        fileCount.incrementAndGet();
                        System.out.println("====>"+file);
                        return super.visitFile(file, attrs);
                    }
                });
        System.out.println("directoryCount:"+directoryCount);
        System.out.println("fileCount:"+fileCount);
    }
}
