package com.fyq.shallowMall.product.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyRedissonConfig {

    /**
     *  所有对象操作都使用redisson对象进行
     * @return
     */
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() {
        // 创建RedissonClient对象
        Config config = new Config();
        // 设置单机的Redis地址，在ip地址前需要加上redis://，如果是使用ssl端口，则需要rediss://
        config.useSingleServer().setAddress("redis://192.168.56.10:6379");

        //根据config创建出RedissonClient对象
        return Redisson.create(config);
    }
}
