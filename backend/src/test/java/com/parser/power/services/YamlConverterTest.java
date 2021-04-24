package com.parser.power.services;

import com.fasterxml.jackson.core.JsonProcessingException;
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
        String yaml =
                "receipt: Oz-Ware Purchase Invoice\n" +
                        "date: 2007-08-06\n" +
                        "customer:\n" +
                        "    given:   Dorothy\n" +
                        "    family:  Gale";
        YamlConverter yamlConverter = new YamlConverter(yaml);
        String json = yamlConverter.convertYamlToJson();
        System.out.println(json);
    }

}