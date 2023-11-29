package org.example;

import org.redisson.api.RDelayedQueue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author jiangfeng
 * @date 2023/11/29
 */
@RestController
public class RedissonDelayController {

    @Resource
    private RDelayedQueue<String> delayedQueue;



    @GetMapping(value = "/test")
    public void offerAsync() {
        //20秒后到期，在监听现成哪里可以打印出  1234567890
        delayedQueue.offerAsync("1234567890", 20, TimeUnit.SECONDS);
    }



}
