package com.parser.power.utils;

import com.parser.power.models.ConvertType;
import org.springframework.core.convert.converter.Converter;

public class StringParamToEnumConverter implements Converter<String, ConvertType> {
    @Override
    public ConvertType convert(String source) {
        try {
            return ConvertType.fromString(source);
        } catch (Exception e) {
            return null;
        }
    }
}
