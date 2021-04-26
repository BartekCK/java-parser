package com.parser.power.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class YamlConverterTest {

    @Test
    void shouldConvertYamlToJson() throws JsonProcessingException {
        String yaml = "receipt: \"Oz-Ware Purchase Invoice\"\n" +
                "number: 2007\n" +
                "customer:\n" +
                "  given: 1\n" +
                "  family: \"Gale\"";

        YamlConverter yamlConverter = new YamlConverter(yaml);
        String json = yamlConverter.convertYamlToJson();

        JsonConverter jsonConverter = new JsonConverter();
        String convertedYaml = jsonConverter.convertFromJsonToYaml(json);

        assertEquals(convertedYaml.trim(), yaml.trim());
    }

}