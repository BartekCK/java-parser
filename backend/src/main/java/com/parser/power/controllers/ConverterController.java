package com.parser.power.controllers;

import com.parser.power.services.JsonConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ConverterController {

    private final JsonConverter jsonConverter;

    @PostMapping("/xml/converter/json")
    public String convertXmlToJson(@RequestBody String xml) throws IOException, SAXException, ParserConfigurationException {
        return jsonConverter.convertFromXmlToJson(xml);
    }

    @PostMapping("/csv/converter/json/{mainNode}")
    public String convertCsvToJson(@PathVariable(name = "mainNode") String mainNode, @RequestBody String xml) throws IOException, SAXException, ParserConfigurationException {
        return jsonConverter.convertFromCsvToJson(mainNode, xml);
    }

    @PostMapping("/json/converter/csv/{mainNode}")
    public String convertJsonToCsv(@PathVariable(name = "mainNode") String mainNode, @RequestBody String json) throws IOException, SAXException, ParserConfigurationException, JSONException {
        return jsonConverter.convertFromJsonToCsv(json, mainNode);
    }
}
