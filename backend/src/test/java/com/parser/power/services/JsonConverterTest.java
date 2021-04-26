package com.parser.power.services;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
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
    void shouldConvertFromXmlToJson() throws IOException, SAXException, ParserConfigurationException, JSONException {
        //given
        String request = new String(getClass().getClassLoader().getResourceAsStream("xml/example2.xml").readAllBytes());
        String expectedResponse = new String(getClass().getClassLoader().getResourceAsStream("json/example2.json").readAllBytes());
        //when
        String result = jsonConverter.convertFromXmlToJson(request);
        //then
        JSONAssert.assertEquals(expectedResponse, result, false);
    }
}
