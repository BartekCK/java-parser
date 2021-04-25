package com.parser.power.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class XmlConverterTest {

    @Test
    void test() throws IOException, SAXException, ParserConfigurationException {
        String file = new String(getClass().getClassLoader().getResourceAsStream("xml/example.xml").readAllBytes());
        String xml = "<book>\n" +
                "    <title edited=\"whenever\">Some title</title>\n" +
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
                "</book>";

                XmlConverter xmlConverter = new XmlConverter(xml);
               String result = xmlConverter.convertFromXmlToJson();
        System.out.println(result);
    }
}
