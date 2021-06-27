package com.parser.power.services;

import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.xml.sax.SAXException;
import org.xmlunit.matchers.CompareMatcher;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class JsonConverterTest {

    @Autowired
    private JsonConverter jsonConverter;

    @Autowired
    private CsvXmlConverter csvXmlConverter;

    @Test
    void shouldConvertFromXmlToCsv() throws IOException, ParserConfigurationException, SAXException, org.springframework.boot.configurationprocessor.json.JSONException {
        //given
        String request = new String(getClass().getClassLoader().getResourceAsStream("xml/example3.xml").readAllBytes());
        String expectedResponse = new String(getClass().getClassLoader().getResourceAsStream("csv/example3.csv").readAllBytes());
        //when
        String result = csvXmlConverter.convertFromXmlToCsv("root", "row",request);
        //then
        Assertions.assertEquals(expectedResponse, result);
    }

    @Test
    void shouldConvertFromCsvToXml() throws IOException, JSONException {
        //given
        String request = new String(getClass().getClassLoader().getResourceAsStream("csv/example3.csv").readAllBytes());
        String expectedResponse = new String(getClass().getClassLoader().getResourceAsStream("xml/example3.xml").readAllBytes());
        //when
        String result = csvXmlConverter.convertFromCsvToXml("root", "row", request);
        //then
        JSONAssert.assertEquals(expectedResponse, result, false);
    }

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

/*    @Test
    void shouldConvertFromJsonToXml() throws IOException, org.springframework.boot.configurationprocessor.json.JSONException {
        //given
        String request = new String(getClass().getClassLoader().getResourceAsStream("json/example4.json").readAllBytes());
        String expectedResponse = new String(getClass().getClassLoader().getResourceAsStream("xml/example4.xml").readAllBytes());
        //when
        String result = jsonConverter.convertFromJsonToXml(request);
        //then
        assertThat(expectedResponse, CompareMatcher.isIdenticalTo(result).ignoreWhitespace());
    }*/

    @Test
    void shouldConvertFromCsvToJson() throws IOException, JSONException {
        //given
        String request = new String(getClass().getClassLoader()
                .getResourceAsStream("csv/example3.csv").readAllBytes());
        String expectedResponse = new String(getClass().getClassLoader()
                .getResourceAsStream("json/example3.json").readAllBytes());
        //when
        String result = jsonConverter.convertFromCsvToJson("employees", request);
        //then
        JSONAssert.assertEquals(expectedResponse, result, false);
    }

    @Test
    void shouldConvertFromJsonToCsv() throws IOException, org.springframework.boot.configurationprocessor.json.JSONException {
        //given
        String request = new String(getClass().getClassLoader().getResourceAsStream("json/example3.json").readAllBytes());
        String expectedResponse = new String(getClass().getClassLoader().getResourceAsStream("csv/example3.csv").readAllBytes());
        //when
        String result = jsonConverter.convertFromJsonToCsv(request, "employees");
        //then
        Assertions.assertEquals(expectedResponse, result);
    }
}
