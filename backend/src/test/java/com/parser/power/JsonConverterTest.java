package com.parser.power;

import com.parser.power.services.JsonConverter;
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
        String xml = "<book>\n" +
                "    <title>Some title</title>\n" +
                "    <description>some description </description>\n" +
                "    <author>\n" +
                "        <id>1</id>\n" +
                "        <name>some author name</name>\n" +
                "    </author>\n" +
                "    <buyer>\n" +
                "        <id>2</id>\n" +
                "        <name>some buyer name</name>\n" +
                "    </buyer>\n" +
                "    <review>nice book</review>\n" +
                "    <review>this book sucks</review>\n" +
                "    <review>amazing work</review>\n" +
                "</book>"
;
               String result = jsonConverter.convertFromXmlToJson(xml);
        System.out.println(result);
    }
}
