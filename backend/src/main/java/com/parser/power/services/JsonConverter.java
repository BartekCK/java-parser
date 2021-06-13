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
    public List<String> visitedJsonXmlNodes = new ArrayList<>();

    public String convertFromCsvToJson(String mainNodeName, String csv) {
        List<CsvNodeDto> csvNodes = getCsvNodesObjects(csv);
        String json;
        json = new StringBuilder("{\n\"" + mainNodeName + "\" : [\n").toString();
        //csv = csv.replace("\n","\r\n");
        String[] lines = csv.split("\n");
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
        visitXmlToJsonChildNodes(nList);
        json = new StringBuilder(json).append("}\n").toString();
        json = json.replace(",\n}", "\n}");
        return json;
    }

    public String convertFromJsonToCsv(String json, String mainNodeName) throws JSONException {
        JSONObject jsonObj = new JSONObject(json);
        JSONArray docs = jsonObj.getJSONArray(mainNodeName);
        return getJsonCsvDocs(docs);

    }

    public String convertFromJsonToXml(String json) throws JSONException {
        JSONObject jsonObj = new JSONObject(json);
        return getJsonXmlDocs(jsonObj, "book");
    }

    public String getJsonXmlDocs(JSONObject ja, String main) throws JSONException {
        String result = "";
        Map<String, List<String>> map = new LinkedHashMap<>();
        result += "<" + main + ">\r\n";
        for (int i = 0; i < ja.length(); i++) {
            JSONObject jo = ja.optJSONObject(main);
            if (jo != null) {
                getAllTopKeyAndValueJsonToXml(jo, map, "");
                result = getResult(result, "", map);
            }
        }
        result += "</" + main + ">";
        return result;
    }

    private String getResult(String result, String replacement, Map<String, List<String>> map) {
        Iterator<Map.Entry<String, List<String>>> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, List<String>> entry = iter.next();
            if (visitedJsonXmlNodes.contains(entry.getKey())) {
                continue;
            }
            if (!entry.getKey().replace(replacement, "").contains("_")) {
                for (int k = 0; k < entry.getValue().size(); k++) {
                    result += "<" + entry.getKey().replace(replacement, "") + ">";
                    result += entry.getValue().get(k);
                    result += "</" + entry.getKey().replace(replacement, "") + ">\r\n";
                }
                visitedJsonXmlNodes.add(entry.getKey());
            } else {
                String key = entry.getKey().substring(0, entry.getKey().indexOf("_"));
                Map<String, List<String>> childs = new LinkedHashMap<>();
                for (Map.Entry<String, List<String>> child : map.entrySet()) {
                    String replacedKey = child.getKey().replace(replacement, "");
                    if (replacedKey.contains(key)) {
                        childs.put(child.getKey(), child.getValue());
                    }
                }
                replacement = entry.getKey().substring(0, entry.getKey().indexOf("_") + 1);
                result += "<" + key + ">\r\n";
                if (childs.size() > 0) {
                    result = getResult(result, replacement, childs);
                }
                result += "</" + key + ">\r\n";
            }
        }
        return result;
    }

    public String getJsonCsvDocs(JSONArray ja) throws JSONException {
        String result = "";
        Map<String, String> map = new LinkedHashMap<>();
        for (int i = 0; i < ja.length(); i++) {
            JSONObject jo = ja.optJSONObject(i);
            if (jo != null) {
                getAllTopKeyAndValueToXml(jo, map, "");
                if (i == 0) {
                    result += keyOfMap2String(map) + "\r\n";
                }
                result += valueOfMap2String(map) + "\r\n";
            }
        }
        return result;
    }

    public void getAllTopKeyAndValueToXml(JSONObject jo, Map<String, String> map, String parentName) throws JSONException {
        if (jo != null) {
            JSONArray names = jo.names();
            String string = "";
            List integers = new ArrayList<>();
            if (names != null) {
                for (int i = 0; i < names.length(); i++) {
                    String name = names.getString(i);
                    JSONObject object = jo.optJSONObject(name);
                    if (object != null) {
                        getAllTopKeyAndValue(object, map, parentName + name + "_");
                    } else {
                        map.put(parentName + name, (String) jo.get(name));
                    }
                }
            }
        }
    }

    public void getAllTopKeyAndValueJsonToXml(JSONObject jo, Map<String, List<String>> map, String parentName) throws JSONException {
        if (jo != null) {
            JSONArray names = jo.names();
            String string = "";
            List integers = new ArrayList<>();
            if (names != null) {
                for (int i = 0; i < names.length(); i++) {
                    String name = names.getString(i);
                    JSONObject object = jo.optJSONObject(name);
                    JSONArray array = jo.optJSONArray(name);
                    if (object != null) {
                        getAllTopKeyAndValueJsonToXml(object, map, parentName + name + "_");
                    } else if (array != null) {
                        List<String> elements = new ArrayList<>(Arrays.asList(toStringArray(array)));
                        map.put(parentName + name, elements);
                    } else {
                        map.put(parentName + name, Collections.singletonList((String) jo.get(name)));
                    }
                }
            }
        }
    }

    public static String[] toStringArray(JSONArray array) {
        if (array == null)
            return null;

        String[] arr = new String[array.length()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = array.optString(i);
        }
        return arr;
    }

    public void getAllTopKeyAndValue(JSONObject jo, Map<String, String> map, String parentName) throws JSONException {
        if (jo != null) {
            JSONArray names = jo.names();
            String string = "";
            List integers = new ArrayList<>();
            if (names != null) {
                for (int i = 0; i < names.length(); i++) {
                    String name = names.getString(i);
                    JSONObject object = jo.optJSONObject(name);
                    if (object != null) {
                        getAllTopKeyAndValue(object, map, parentName + name + "_");
                    } else {
                        map.put(parentName + name, (String) jo.get(name));
                    }
                }
            }
        }
    }

    public String keyOfMap2String(Map<String, String> map) {
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

    public String valueOfMap2String(Map<String, String> map) {
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

    public String convertFromJsonToYaml(String json) throws JsonProcessingException {
        JsonNode jsonNode = new ObjectMapper().readTree(json);
        String badYaml = new YAMLMapper().writeValueAsString(jsonNode);
        return badYaml.substring(4);
    }

    private void visitXmlToJsonChildNodes(NodeList nList) {
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
                    visitXmlToJsonChildNodes(nl);
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
