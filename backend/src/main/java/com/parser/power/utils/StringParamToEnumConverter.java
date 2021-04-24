package com.parser.power.utils;

import com.parser.power.models.FileExtension;
import org.springframework.core.convert.converter.Converter;

public class StringParamToEnumConverter implements Converter<String, FileExtension> {
    @Override
    public FileExtension convert(String source) {
        try{
            return FileExtension.fromString(source);
        }catch (Exception e){
            return null;
        }
    }
}
