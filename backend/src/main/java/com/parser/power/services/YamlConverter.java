package com.parser.power.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class YamlConverter {

    private final JsonConverter jsonConverter;

    public String convertYamlToJson(String yaml) throws JsonProcessingException, JSONException {
        RestTemplate restTemplate = new RestTemplate();
        return new JSONObject(restTemplate.postForObject("http://localhost:3000/yaml/converter/json", yaml, String.class)).toString(1);
    }

    public String convertJsonToYaml(String json) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject("http://localhost:3000/json/converter/yaml", json, String.class);
    }

    public String convertXmlToYaml(String xml) throws IOException, ParserConfigurationException, SAXException {
        String json = jsonConverter.convertFromXmlToJson(xml);
        return convertJsonToYaml(json);
    }

    public String convertYamlToXml(String yaml) throws IOException, JSONException {
        String json = convertYamlToJson(yaml);
        return jsonConverter.convertFromJsonToXml(json);
    }

    public String convertYamlToCsv(String mainNodeName, String yaml) throws IOException, JSONException {
        String json = convertYamlToJson(yaml);
        return jsonConverter.convertFromJsonToCsv(json, mainNodeName);
    }

    public String convertCsvToYaml(String mainNodeName, String yaml) throws IOException {
        String json = jsonConverter.convertFromCsvToJson(mainNodeName, yaml);
        return convertJsonToYaml(json);
    }

}
