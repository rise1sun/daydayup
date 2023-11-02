package com.feng.nio;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author jiangfeng
 * @date 2023/10/31
 */
@Slf4j
public class JdkFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(2);
        Future<Integer> future = threadPool.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.debug("执行计算");
                Thread.sleep(1000);
                return 50;
            }
        });

        log.debug("get result...");
        log.debug("result id {}",future.get());

    }
}
