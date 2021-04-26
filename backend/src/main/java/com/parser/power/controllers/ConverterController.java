package com.parser.power.controllers;

import com.parser.power.services.JsonConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@RestController
@RequestMapping("/xml/converter")
@RequiredArgsConstructor
public class ConverterController {

    private final JsonConverter jsonConverter;

    @PostMapping("/json")
    public String convertXmlToJson(@RequestBody String xml) throws IOException, SAXException, ParserConfigurationException {
        return jsonConverter.convertFromXmlToJson(xml);
    }
}
