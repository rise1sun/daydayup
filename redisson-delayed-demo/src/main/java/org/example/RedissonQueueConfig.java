package org.example;

import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jiangfeng
 * @date 2023/11/29
 */
@Configuration
public class RedissonQueueConfig {

    private final String queueName = "taskQueue";

    @Bean
    public RBlockingQueue<String> blockingQueue() {
        return RedisUtils.getClient().getBlockingQueue(queueName);
    }
    @Bean
    public RDelayedQueue<String> delayedQueue(RBlockingQueue<String> blockQueue) {
        return RedisUtils.getClient().getDelayedQueue(blockQueue);
    }
}
