package org.example;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author jiangfeng
 * @date 2023/11/29
 */
@Slf4j
@Component
public class OrderTask {

    @Resource
    private RBlockingQueue<Object> blockingQueue;

    @PostConstruct
    public void take() {
        new Thread(() -> {
            while (true) {
                try {
                    log.info(blockingQueue.take().toString());  //将到期的数据取出来，如果一直没有到期数据，就一直等待。
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
