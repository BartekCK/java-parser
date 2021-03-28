package com.parser.power.configs;

import com.parser.power.models.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@RequiredArgsConstructor
public class RedisConnection {

    private final ClientRedisConfigProperties clientConfigProperties;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory(
                new RedisStandaloneConfiguration(
                        clientConfigProperties.getRedisServer(),
                        clientConfigProperties.getRedisPort()));
    }

    @Bean
    public RedisTemplate<String, Converter> redisTemplate() {
        RedisTemplate<String, Converter> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }
}
