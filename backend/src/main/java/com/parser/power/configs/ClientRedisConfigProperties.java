package com.parser.power.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties("redis")
public class ClientRedisConfigProperties {
    private String redisServer;
    private int redisPort;
}
