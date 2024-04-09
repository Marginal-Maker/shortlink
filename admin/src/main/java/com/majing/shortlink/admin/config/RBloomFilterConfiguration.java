package com.majing.shortlink.admin.config;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author majing
 * @date 2024-04-09 16:15
 * @Description 布隆过滤器配置类
 */
@Configuration
public class RBloomFilterConfiguration {
    @Bean
    public RBloomFilter<String> userRegisterCachePenetrationBloomFilter(RedissonClient redissonClient) {
        RBloomFilter<String> cachePenetrationBloomFilter = redissonClient.getBloomFilter("userRegisterCachePenetrationBloomFilter");
        //tryInit 有两个核心参数：expectedInsertions：预估布隆过滤器存储的元素长度；falseProbability：运行的误判率。
        cachePenetrationBloomFilter.tryInit(100000000L, 0.01);
        return cachePenetrationBloomFilter;
    }
}
