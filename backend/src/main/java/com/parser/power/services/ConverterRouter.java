package com.parser.power.services;

import com.parser.power.models.ConvertType;
import com.parser.power.models.JsonConverter;
import com.parser.power.models.XmlConverter;
import com.parser.power.models.YamlConverter;

public class ConverterRouter {
    private String json;
    private YamlConverter yamlConverter;

    public ConverterRouter(String text, ConvertType current) throws Exception {
        switch (current) {
            case YAML:
                yamlConverter = new YamlConverter(text);
                this.json = yamlConverter.convertYamlToJson();
                break;

            case XML:
                XmlConverter xmlConverter = new XmlConverter(text);
                this.json = xmlConverter.convertFromXmlToJson();
                break;

            case CSV:
                throw new Exception("Not implemented yet");

            default:
                this.json = text;
        }
    }

    public String convertTo(ConvertType target) throws Exception {
        switch (target) {
            case YAML:
                return new JsonConverter().convertFromJsonToYaml(this.json);

            case XML:
            case CSV:
                throw new Exception("Not implemented yet");

            default:
                return this.json;
        }
    }

    public String getJson() {
        return json;
    }
}
