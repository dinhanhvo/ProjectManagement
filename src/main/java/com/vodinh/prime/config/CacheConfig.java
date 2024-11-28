package com.vodinh.prime.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        List<CacheManager> cacheManagers = new ArrayList<>();

        try {
            RedisCacheManager redisCacheManager = RedisCacheManager.builder(redisConnectionFactory).build();
            cacheManagers.add(redisCacheManager);
        } catch (Exception e) {
            System.err.println("Redis server not avaiable.");
        }

        cacheManagers.add(new ConcurrentMapCacheManager());

        CompositeCacheManager compositeCacheManager = new CompositeCacheManager();
        compositeCacheManager.setCacheManagers(cacheManagers);
        compositeCacheManager.setFallbackToNoOpCache(true);
        return compositeCacheManager;
    }
}

