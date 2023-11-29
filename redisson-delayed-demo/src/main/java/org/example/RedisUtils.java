package org.example;
import cn.hutool.extra.spring.SpringUtil;
import com.sun.javafx.fxml.BeanAdapter;
import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.config.Config;

import javax.annotation.Resource;

/**
 * @author jiangfeng
 * @date 2023/11/29
 */
public class RedisUtils{

    private  static RedissonClient redissonClient;
    /**
     * 获取客户端实例
     */
    public static RedissonClient getClient() {
        if(null == redissonClient) {
            Config config = new Config();
            config.useSingleServer()
                    .setAddress("redis://124.70.221.38:6379")
                    .setPassword("123456@qwer")
                    .setPingConnectionInterval(1000);
            redissonClient = Redisson.create(config);
        }
        return redissonClient;
    }
}
