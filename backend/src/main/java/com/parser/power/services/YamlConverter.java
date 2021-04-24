package com.parser.power.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class YamlConverter {

    private String yamlInString;
    private ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());


    public YamlConverter(String yamlInString) {
        this.yamlInString = yamlInString;
    }

    public String convertYamlToJson() throws JsonProcessingException {
        Object obj = yamlReader.readValue(this.yamlInString, Object.class);
        return new ObjectMapper().writeValueAsString(obj);
    }

}
