package com.parser.power.repositories;

import com.parser.power.models.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class ConverterRepositoryImpl implements ConverterRepository {

    private final RedisTemplate<String, Converter> redisTemplate;
    private HashOperations<String, String, Object> hashOperations;
    private static final String REDIS_ENTITY = "convert_data";


    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public Converter save(String key, Converter converter, int secondsExpired) {
        if (redisTemplate.hasKey(key)) {
            System.out.println("Throw error in this place");
        }
        hashOperations.put(key, REDIS_ENTITY, converter);
        redisTemplate.expire(key, secondsExpired, TimeUnit.SECONDS);
        return converter;
    }

    @Override
    public Converter getByKey(String key) {
        return (Converter) hashOperations.get(key, REDIS_ENTITY);
    }
}
