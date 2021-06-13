package com.parser.power.controllers;

import com.parser.power.services.CsvXmlConverter;
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
    private final CsvXmlConverter csvConverter;

    @PostMapping("/xml/converter/json")
    public String convertXmlToJson(@RequestBody String xml) throws IOException, SAXException, ParserConfigurationException {
        return jsonConverter.convertFromXmlToJson(xml);
    }

    @PostMapping("/json/converter/xml")
    public String convertJsonToXml(@RequestBody String json) throws JSONException {
        return jsonConverter.convertFromJsonToXml(json);
    }

    @PostMapping("/csv/converter/json/{mainNode}")
    public String convertCsvToJson(@PathVariable(name = "mainNode") String mainNode, @RequestBody String csv) {
        return jsonConverter.convertFromCsvToJson(mainNode, csv);
    }

    @PostMapping("/json/converter/csv/{mainNode}")
    public String convertJsonToCsv(@PathVariable(name = "mainNode") String mainNode, @RequestBody String json) throws JSONException {
        return jsonConverter.convertFromJsonToCsv(json, mainNode);
    }

    @PostMapping("/csv/converter/xml/{mainNode}")
    public String convertFromCsvToXml(@PathVariable(name = "mainNode") String mainNode, @RequestBody String xml) throws JSONException {
        return csvConverter.convertFromCsvToXml(mainNode, xml);
    }

    @PostMapping("/xml/converter/csv/{mainNode}")
    public String convertFromXmlToCsv(@PathVariable(name = "mainNode") String mainNode, @RequestBody String xml) throws SAXException, ParserConfigurationException, JSONException, IOException {
        return csvConverter.convertFromXmlToCsv(mainNode, xml);
    }

}
