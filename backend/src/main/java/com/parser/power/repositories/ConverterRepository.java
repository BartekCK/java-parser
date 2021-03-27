package com.parser.power.repositories;

import com.parser.power.models.Converter;
import org.springframework.stereotype.Repository;

@Repository
public interface ConverterRepository {
    /**
     * Serializable method
     * @param key - Should be unique
     * @param converter - Object for serialization
     * @param secondsExpired - Timeout on key. After the timeout has expired, the key will automatically be deleted
     * @return
     */
    Converter save(String key, Converter converter, int secondsExpired);
    Converter getByKey(String key);
}