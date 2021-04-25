package com.parser.power.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.parser.power.models.JsonConverter;
import com.parser.power.models.YamlConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class YamlConverterTest {

    @Test
    void shouldConvertYamlToJSON() throws JsonProcessingException {
        String yaml = "receipt: \"Oz-Ware Purchase Invoice\"\n" +
                "number: 2007\n" +
                "customer:\n" +
                "  given: 1\n" +
                "  family: \"Gale\"";

        YamlConverter yamlConverter = new YamlConverter(yaml);
        String json = yamlConverter.convertYamlToJson();

        JsonConverter jsonConverter = new JsonConverter();
        String convertedYaml = jsonConverter.convertFromJsonToYaml(json);

       assertTrue(yaml.trim().equals(convertedYaml.trim()));
    }

}