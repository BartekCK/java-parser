package com.parser.power.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.parser.power.models.CsvNodeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JsonConverter {

    public List<CsvNodeDto> alreadyVisitedCsvNodes = new ArrayList<>();
    public List<String> visitedColumns = new ArrayList<>();

    public String convertFromCsvToJson(String mainNodeName, String csv) {
        List<CsvNodeDto> csvNodes = getCsvNodesObjects(csv);
        String json;
        json = new StringBuilder("{\n\"" + mainNodeName + "\" : [\n").toString();
        String[] lines = csv.split("\r\n");
        for (int i = 1; i < lines.length; i++) {
            String[] columns = lines[i].split(",");
            json = new StringBuilder(json).append("{\n").toString();
            json = visitChildNodes(csvNodes, csvNodes, json, columns);
            json = new StringBuilder(json).append("},\n").toString();
            alreadyVisitedCsvNodes = new ArrayList<>();
            visitedColumns = new ArrayList<>();
        }
        json = new StringBuilder(json).append("]\n}\n").toString();
        json = json.replace(",\n}", "\n}");
        json = json.replace("\n},\n]", "\n}\n]");
        System.out.println(json);
        return json;
    }

    private String visitChildNodes(List<CsvNodeDto> csvNodes, List<CsvNodeDto> childNodes, String json, String[] columns) {
        for (CsvNodeDto node : childNodes) {
            if (node.getColumn() != null && !visitedColumns.contains(node.getFullName())) {
                json = new StringBuilder(json).append("\"").append(node.getName()).append("\" : \"").append(columns[node.getColumn()].trim()).append("\",\n").toString();
                visitedColumns.add(node.getFullName());
            }
            if (node.getColumn() == null) {
                List<CsvNodeDto> kids = csvNodes.stream()
                        .filter(n -> n.getParentName() != null && n.getParentName().equals(node.getName()))
                        .collect(Collectors.toList());
                if (!alreadyVisitedCsvNodes.stream().anyMatch(e -> e.getName().equals(node.getName()))) {
                    json = new StringBuilder(json).append("\"" + node.getName() + "\" : {\n").toString();
                    alreadyVisitedCsvNodes.add(node);
                    json = visitChildNodes(childNodes, kids, json, columns);
                    json = new StringBuilder(json).append("},\n").toString();
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

    public Map<Node, List<Node>> alreadyVisitedAttr = new HashMap<>();
    public List<Node> alreadyVisited = new ArrayList<>();
    public List<Node> nodes = new ArrayList<>();
    private String json = "";


    public String convertFromXmlToJson(String xml) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        File temp = File.createTempFile("name", ".xml");

        temp.deleteOnExit();

        BufferedWriter out = new BufferedWriter(new FileWriter(temp));
        out.write(xml);
        out.close();
        Document document = builder.parse(temp);

        document.getDocumentElement().normalize();

        Element root = document.getDocumentElement();
        NodeList nList = document.getElementsByTagName(root.getNodeName());
        json = new StringBuilder(json).append("{\n").toString();
        visitJsonToXmlChildNodes(nList);
        json = new StringBuilder(json).append("}\n").toString();
        json = json.replace(",\n}", "\n}");
        return json;
    }


    public String convertFromJsonToCsv(String json) throws JSONException {
        JSONObject jsonObj = new JSONObject(json);
        JSONArray docs = jsonObj.getJSONArray("employees");
        return getDocs(docs);

    }

    public static String getDocs(JSONArray ja) throws JSONException {
        String result = "";
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < ja.length(); i++) {
            JSONObject jo = ja.optJSONObject(i);
            if (jo != null) {
                getAllTopKeyAndValue(jo, map);
                if (i == 0) {
                    result += keyOfMap2String(map) + "\n";
                }
                result += valueOfMap2String(map) + "\n";
            }
        }
        return result;
    }


    public static void getAllTopKeyAndValue(JSONObject jo, Map<String, String> map) throws JSONException {
        if (jo != null) {
            JSONArray names = jo.names();
            String string = "";
            List integers = new ArrayList<>();
            if (names != null) {
                for (int i = 0; i < names.length(); i++) {
                    String name = names.getString(i);
                    JSONObject object = jo.optJSONObject(name);
                    if (object != null) {
                        getAllTopKeyAndValue(object, map);
                    } else {
                        map.put(name, (String) jo.get(name));
                    }
                }
            }
        }
    }

    public static String keyOfMap2String(Map<String, String> map) {
        String result = "";
        Iterator<Map.Entry<String, String>> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String> entry = iter.next();
            result += entry.getKey();
            if (iter.hasNext()) {
                result += ",";
            }
        }
        return result;
    }

    public static String valueOfMap2String(Map<String, String> map) {
        String result = "";
        Iterator<Map.Entry<String, String>> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String> entry = iter.next();
            result += entry.getValue();
            if (iter.hasNext()) {
                result += ",";
            }
        }
        return result;
    }

    public String convertFromJsonToXml(String json) {
        return null;
    }

    public String convertFromJsonToYaml(String json) throws JsonProcessingException {
        JsonNode jsonNode = new ObjectMapper().readTree(json);
        String badYaml = new YAMLMapper().writeValueAsString(jsonNode);
        return badYaml.substring(4);
    }

    private void visitJsonToXmlChildNodes(NodeList nList) {
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node node = nList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                if (node.hasAttributes()) {
                    NamedNodeMap nodeMap = node.getAttributes();
                    for (int i = 0; i < nodeMap.getLength(); i++) {
                        Node tempNode = nodeMap.item(i);
                        if (!alreadyVisitedAttr.containsKey(node) || !alreadyVisitedAttr.get(node).contains(tempNode)) {
                            json = new StringBuilder(json).append("\"" + tempNode.getNodeName() + "\" : \"" + tempNode.getNodeValue() + "\",\n").toString();
                            if (!alreadyVisitedAttr.containsKey(node)) {
                                alreadyVisitedAttr.put(node, new ArrayList<>());
                            }
                            alreadyVisitedAttr.get(node).add(tempNode);
                        }
                    }
                }
                NodeList nl = ((Element) node).getElementsByTagName("*");
                if (nl.getLength() > 0) {
                    json = new StringBuilder(json).append("\"" + node.getNodeName() + "\" : {\n").toString();
                    visitJsonToXmlChildNodes(nl);
                    json = new StringBuilder(json).append("},\n").toString();
                } else {
                    if (!alreadyVisited.contains(node)) {
                        json = new StringBuilder(json).append("\"" + node.getNodeName() + "\" : \"" + node.getTextContent() + "\",\n").toString();
                        this.alreadyVisited.add(node);
                    }
                }
            }
        }
    }

}
