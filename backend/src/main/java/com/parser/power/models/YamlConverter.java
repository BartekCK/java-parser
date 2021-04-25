package com.parser.power.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class YamlConverter {

    private String yaml;
    private ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());

    public YamlConverter(String yaml) {
        this.yaml = yaml;
    }

    public String convertYamlToJson() throws JsonProcessingException {
        Object obj = yamlReader.readValue(this.yaml, Object.class);
        return new ObjectMapper().writeValueAsString(obj);
    }

}