package com.parser.power.services;

import com.parser.power.models.CsvNodeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CsvXmlConverter {

    private final JsonConverter jsonConverter;

    private String json = "";
    public Map<Node, List<Node>> alreadyVisitedAttr = new HashMap<>();
    public List<Node> alreadyVisited = new ArrayList<>();
    public List<CsvNodeDto> alreadyVisitedCsvNodes = new ArrayList<>();
    public List<String> visitedColumns = new ArrayList<>();
    public List<String> visitedJsonXmlNodes = new ArrayList<>();

    public String convertFromCsvToXml(String mainNodeName, String elementName, String csv) {
        json = "";
        csv = csv.replace("\n", "\r\n");
        List<CsvNodeDto> csvNodes = getCsvNodesObjects(csv);
        String json;
        json = new StringBuilder("<" + mainNodeName + ">\n").toString();
        String[] lines = csv.split("\n");
        for (int i = 1; i < lines.length; i++) {
            String[] columns = lines[i].split(",");
            json = new StringBuilder(json).append(String.format("<%s>\n", elementName)).toString();
            json = visitChildNodes(csvNodes, csvNodes, json, columns);
            json = new StringBuilder(json).append(String.format("</%s>\n", elementName)).toString();
            alreadyVisitedCsvNodes = new ArrayList<>();
            visitedColumns = new ArrayList<>();
        }
        json = new StringBuilder(json).append("</" + mainNodeName + ">\n").toString();
        json = json.replace(",\n}", "\n}");
        json = json.replace("\n},\n]", "\n}\n]");
        System.out.println(json);
        return json;
    }

    private String visitChildNodes(List<CsvNodeDto> csvNodes, List<CsvNodeDto> childNodes, String json, String[] columns) {
        for (CsvNodeDto node : childNodes) {
            if (node.getColumn() != null && !visitedColumns.contains(node.getFullName())) {
                json = new StringBuilder(json).append("<").append(node.getName()).append(">").append(columns[node.getColumn()].trim()).append("</").append(node.getName()).append(">\n").toString();
                visitedColumns.add(node.getFullName());
            }
            if (node.getColumn() == null) {
                List<CsvNodeDto> kids = csvNodes.stream()
                        .filter(n -> n.getParentName() != null && n.getParentName().equals(node.getName()))
                        .collect(Collectors.toList());
                if (!alreadyVisitedCsvNodes.stream().anyMatch(e -> e.getName().equals(node.getName()))) {
                    json = new StringBuilder(json).append("<" + node.getName() + ">\n").toString();
                    alreadyVisitedCsvNodes.add(node);
                    json = visitChildNodes(childNodes, kids, json, columns);
                    json = new StringBuilder(json).append("</" + node.getName() + ">\n").toString();
                }
            }
        }
        return json;
    }

    private List<CsvNodeDto> getCsvNodesObjects(String csv) {
        String firstLine = csv.split("\r\n", 2)[0];
        String[] columns = firstLine.split(",");
        List<CsvNodeDto> csvNodes = new ArrayList<>();
        int counter = 0;
        for (String column : columns) {
            String[] nodeElements = column.split("_");
            int elementsCount = nodeElements.length;
            String parent = null;
            for (String element : nodeElements) {
                if (!csvNodes.stream().anyMatch(n -> n.getName().equals(element))) {
                    CsvNodeDto node = CsvNodeDto.builder()
                            .name(element.trim())
                            .build();
                    if (nodeElements[elementsCount - 1].equals(element)) {
                        node.setColumn(counter++);
                        node.setFullName(column == null ? null : column.trim());
                    }
                    node.setParentName(parent == null ? null : parent.trim());

                    parent = element;

                    csvNodes.add(node);
                }
                if (elementsCount > 1)
                    parent = element;
            }
        }
        return csvNodes;
    }

    public String generateRandomString(){
        return UUID.randomUUID().toString().replaceAll("[^a-zA-Z]", "");
    }

    public String convertFromXmlToCsv(String mainNode, String mainElement, String xml) throws JSONException, IOException, SAXException, ParserConfigurationException {
        List<String> jsonArrayNodesNames = new ArrayList<>();
        List<String> list = Arrays.asList(xml.split("\n"));
        List<String> result = new ArrayList<>();
        String generated = null;
        for (String element : list) {
            if (element.contains(String.format("<%s>", mainElement))) {
                generated = generateRandomString();
                element = element.replace((String.format("<%s>", mainElement)), String.format("<%s>", generated));
                jsonArrayNodesNames.add(generated);
            } else if (element.contains(String.format("</%s>", mainElement))) {
                element = element.replace((String.format("</%s>", mainElement)), String.format("</%s>", generated));
            }
            result.add(element);
        }
        xml = String.join("\n", result);
        String json = jsonConverter.convertFromXmlToJson( xml);
        JSONArray jsonArray = new JSONArray();
        JSONObject object = new JSONObject(json).getJSONObject(mainNode);
        for (String element : jsonArrayNodesNames) {
            jsonArray.put(object.getJSONObject(element));
        }
        JSONObject mainObj = new JSONObject();
        mainObj.put(mainNode, jsonArray);
        String diffJson = mainObj.toString(1);
        return jsonConverter.convertFromJsonToCsv(diffJson, mainNode);
    }
}
