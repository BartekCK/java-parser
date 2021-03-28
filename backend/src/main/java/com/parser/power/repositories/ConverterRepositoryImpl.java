package com.parser.power.repositories;

import com.parser.power.models.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class ConverterRepositoryImpl implements ConverterRepository {

    private final RedisTemplate<String, Converter> redisTemplate;
    private ValueOperations<String, Converter> valueOperations;

    @PostConstruct
    private void init() {
        valueOperations = redisTemplate.opsForValue();
    }

    @Override
    public Converter save(String key, Converter converter, int secondsExpired) {
        if (redisTemplate.hasKey(key)) {
            System.out.println("Throw error in this place");
        }
        valueOperations.set(key, converter);
        redisTemplate.expire(key, secondsExpired, TimeUnit.SECONDS);
        return converter;
    }

    @Override
    public Converter getByKey(String key) {
        return valueOperations.get(key);
    }
}
