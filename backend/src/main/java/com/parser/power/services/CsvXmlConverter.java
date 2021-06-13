package com.parser.power.services;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CsvXmlConverter {

    private final JsonConverter jsonConverter;

    public String convertFromCsvToXml(String mainNode, String csv) throws JSONException {
        String json = jsonConverter.convertFromCsvToJson(mainNode, csv);
        return jsonConverter.convertFromJsonToXml(json);
    }

    public String convertFromXmlToCsv(String mainNode, String xml) throws JSONException, IOException, SAXException, ParserConfigurationException {
        String json = jsonConverter.convertFromXmlToJson(xml);
        return jsonConverter.convertFromJsonToCsv(mainNode, json);
    }
}
