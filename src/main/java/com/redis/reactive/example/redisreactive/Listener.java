package com.redis.reactive.example.redisreactive;

import io.lettuce.core.pubsub.RedisPubSubListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Listener implements RedisPubSubListener<String, String> {

    private static final Logger logger = LoggerFactory.getLogger(Listener.class);

    @Override
    public void message(String channel, String message) {
        logger.info("subscribing message {}  on channel {}", message, channel);
    }

    @Override
    public void message(String pattern, String channel, String message) {
        logger.info("subscribing message {}  on channel {}, pattern {}", message, channel, pattern);

    }

    @Override
    public void subscribed(String channel, long count) {
        logger.info("subscribing on channel {}, count {}", channel, count);
    }

    @Override
    public void psubscribed(String pattern, long count) {
        logger.info("psubscribed on pattern {}, count {}", pattern, count);
    }

    @Override
    public void unsubscribed(String channel, long count) {
        logger.info("unsubscribed on channel {}, count {}", channel, count);

    }

    @Override
    public void punsubscribed(String pattern, long count) {
        logger.info("punsubscribed on pattern {}, count {}", pattern, count);
    }
}
