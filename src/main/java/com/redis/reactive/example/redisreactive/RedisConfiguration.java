package com.redis.reactive.example.redisreactive;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfiguration {

    @Bean
    public RedisClient redisClient() {

        return RedisClient.create(RedisURI.Builder
                .redis("localhost", 6379).build());
    }

    @Bean
    public StatefulRedisConnection<String, String> redisConnection(@Autowired RedisClient redisClient) {
        StatefulRedisConnection<String, String> connection
                = redisClient.connect();
        return connection;
    }


}