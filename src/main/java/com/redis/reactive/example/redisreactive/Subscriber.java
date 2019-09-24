package com.redis.reactive.example.redisreactive;

import io.lettuce.core.RedisClient;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.async.RedisPubSubAsyncCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Subscriber {
    @Autowired
    private RedisClient redisClient;
    @Autowired
    private Listener listener;
    private StatefulRedisPubSubConnection<String, String> connection;
    private RedisPubSubAsyncCommands<String, String> async;

    @Autowired
    public Subscriber(@Autowired RedisClient redisClient, @Autowired Listener listener) {
        this.redisClient = redisClient;

        StatefulRedisPubSubConnection<String, String> redisPubSubConnectiontion
                = redisClient.connectPubSub();
        redisPubSubConnectiontion.addListener(listener);
        RedisPubSubAsyncCommands<String, String> async
                = redisPubSubConnectiontion.async();
        async.subscribe("channel");
    }

}
