package com.redis.reactive.example.redisreactive;

import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RestController
public class RedisController {
    @Autowired
    private Publisher publisher;

    private final StatefulRedisConnection<String, String> redisConnection;

    private static final Logger logger = LoggerFactory.getLogger(RedisController.class);

    RedisController(StatefulRedisConnection<String, String> redisConnection) {

        this.redisConnection = redisConnection;
    }

    @GetMapping("/redis-put")
    public String putKey() throws InterruptedException, ExecutionException {
        RedisAsyncCommands<String, String> asyncCommands = redisConnection.async();
        asyncCommands.reset();
        for (int i = 0; i < 100; i++) {
            String newStr = asyncCommands.get("key").get() + "test" + i;
            logger.info("Putting key {} ", newStr);
            asyncCommands.set("key", newStr);
            Thread.sleep(2000);
        }
        return "Success";

    }

    @GetMapping("/redis-get")
    public String getKey() {
        RedisAsyncCommands<String, String> asyncCommands = redisConnection.async();
        RedisFuture<String> future = asyncCommands.get("key");
        try {
            while (true) {
                String value = future.get(1000, TimeUnit.SECONDS);
                logger.info("value is {}", value);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return "Success";

    }

    @GetMapping("/redis-publish")
    public String publishMessage() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            publisher.publish("channel", "test" + i);
            Thread.sleep(2000);
        }
        return "Success";

    }
}