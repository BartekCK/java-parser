package com.parser.power.controllers;

import com.parser.power.services.CsvXmlConverter;
import com.parser.power.services.JsonConverter;
import com.parser.power.services.YamlConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@RestController
@RequestMapping
@RequiredArgsConstructor
@CrossOrigin
public class ConverterController {

    private final JsonConverter jsonConverter;
    private final CsvXmlConverter csvConverter;
    private final YamlConverter yamlConverter;

    @PostMapping(value = "/yaml/converter/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public String convertYamlToJson(@RequestBody String yaml) throws IOException, JSONException {
        return yamlConverter.convertYamlToJson(yaml);
    }

    @PostMapping(value = "/json/converter/yaml")
    public String convertJsonToYaml(@RequestBody String json) throws IOException {
        return yamlConverter.convertJsonToYaml(json);
    }

    @PostMapping(value = "/xml/converter/yaml", produces = MediaType.APPLICATION_JSON_VALUE)
    public String convertXmlToYaml(@RequestBody String xml) throws IOException, SAXException, ParserConfigurationException {
        return yamlConverter.convertXmlToYaml(xml);
    }

    @PostMapping(value = "/yaml/converter/xml/{mainNode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String convertYamlToXml(@PathVariable(name = "mainNode") String mainNode,@RequestBody String yaml) throws IOException, JSONException {
        return yamlConverter.convertYamlToXml(mainNode, yaml);
    }

    @PostMapping(value = "/xml/converter/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public String convertXmlToJson(@RequestBody String xml) throws IOException, SAXException, ParserConfigurationException {
        return jsonConverter.convertFromXmlToJson(xml);
    }

    @PostMapping(value = "/json/converter/xml/{mainNode}", produces = MediaType.APPLICATION_XML_VALUE)
    public String convertJsonToXml(@PathVariable(name = "mainNode") String mainNode,@RequestBody String json) throws JSONException {
        return jsonConverter.convertFromJsonToXml(mainNode, json);
    }

    @PostMapping(value = "/csv/converter/json/{mainNode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String convertCsvToJson(@PathVariable(name = "mainNode") String mainNode, @RequestBody String csv) {
        return jsonConverter.convertFromCsvToJson(mainNode, csv);
    }

    @PostMapping(value = "/csv/converter/yaml/{mainNode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String convertCsvToYaml(@PathVariable(name = "mainNode") String mainNode, @RequestBody String csv) throws JSONException, IOException {
        return yamlConverter.convertCsvToYaml(mainNode, csv);
    }

    @PostMapping("/json/converter/csv/{mainNode}")
    public String convertJsonToCsv(@PathVariable(name = "mainNode") String mainNode, @RequestBody String json) throws JSONException {
        return jsonConverter.convertFromJsonToCsv(json, mainNode);
    }

    @PostMapping("/yaml/converter/csv/{mainNode}")
    public String convertYamlToCsv(@PathVariable(name = "mainNode") String mainNode, @RequestBody String yaml) throws JSONException, IOException {
        return yamlConverter.convertYamlToCsv(mainNode, yaml);
    }

    @PostMapping(value = "/csv/converter/xml/{mainNode}/{elementName}", produces = MediaType.APPLICATION_XML_VALUE)
    public String convertFromCsvToXml(@PathVariable(name = "mainNode") String mainNode,
                                      @PathVariable(name = "elementName") String elementName,
                                      @RequestBody String xml) throws JSONException {
        return csvConverter.convertFromCsvToXml(mainNode, elementName, xml);
    }

    @PostMapping("/xml/converter/csv/{mainNode}/{elementName}")
    public String convertFromXmlToCsv(@PathVariable(name = "mainNode") String mainNode,
                                      @PathVariable(name = "elementName") String elementName,
                                      @RequestBody String xml) throws SAXException, ParserConfigurationException, JSONException, IOException {
        return csvConverter.convertFromXmlToCsv(mainNode, elementName, xml);
    }

}
