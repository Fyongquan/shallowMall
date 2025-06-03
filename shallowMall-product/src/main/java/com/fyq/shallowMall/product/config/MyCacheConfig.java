package com.fyq.shallowMall.product.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Random;

@EnableConfigurationProperties(CacheProperties.class)
@Configuration
@EnableCaching
public class MyCacheConfig {
    // 第一种的引入配置文件方法
//    @Autowired
//    private CacheProperties cacheProperties;

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(/*第二种引入配置文件方法**/CacheProperties cacheProperties){

        //  使用fastjson序列化
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericFastJsonRedisSerializer()));

        // 将配置文件中的默认配置生效
        CacheProperties.Redis redisProperties = cacheProperties.getRedis();
        //这是RedisCacheConfiguration类里面的默认配置写法，直接照抄
        if(redisProperties.getKeyPrefix() != null){
            config = config.prefixKeysWith(redisProperties.getKeyPrefix());
        }
        if(!redisProperties.isCacheNullValues()){
            config = config.disableCachingNullValues();
        }
        if(!redisProperties.isUseKeyPrefix()) {
            config = config.disableKeyPrefix();
        }

        // 获取配置文件中的过期时间，如果没有设置则使用默认值
        Duration baseExpiration = redisProperties.getTimeToLive() != null ?
                redisProperties.getTimeToLive() : Duration.ofHours(1);

        // 添加随机波动（0-30分钟的随机值）
        Random random = new Random();
        int randomSeconds = random.nextInt(1800); // 0-1800秒 = 0-30分钟
        Duration randomExpiration = baseExpiration.plusSeconds(randomSeconds);

        config = config.entryTtl(randomExpiration);

        return config;
    }
}
