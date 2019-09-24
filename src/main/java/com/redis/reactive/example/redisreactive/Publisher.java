package com.redis.reactive.example.redisreactive;

import io.lettuce.core.RedisClient;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.async.RedisPubSubAsyncCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Publisher {
    @Autowired
    private RedisClient redisClient;
    private StatefulRedisPubSubConnection<String, String> connection;
    private RedisPubSubAsyncCommands<String, String> async;
    private static final Logger logger = LoggerFactory.getLogger(Publisher.class);

    @Autowired
    public Publisher(@Autowired RedisClient redisClient) {
        this.redisClient = redisClient;

        connection = redisClient.connectPubSub();

        async = connection.async();
    }

    public void publish(String channel, String message) {
        logger.info("Publishing message {} for channel {}", message, channel);
        async.publish(channel, message);
    }

}
