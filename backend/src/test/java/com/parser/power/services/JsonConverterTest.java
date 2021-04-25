package com.parser.power.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class JsonConverterTest {
    @Autowired
    private JsonConverter jsonConverter;

    @Test
    void test() throws IOException, SAXException, ParserConfigurationException {
        String file = new String(getClass().getClassLoader().getResourceAsStream("xml/example2.xml").readAllBytes());
        String result = jsonConverter.convertFromXmlToJson(file);
        System.out.println(result);
    }
}
