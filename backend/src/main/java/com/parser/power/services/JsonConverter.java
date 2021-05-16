package com.parser.power.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.parser.power.models.CsvNodeDto;
import lombok.RequiredArgsConstructor;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JsonConverter {

    public String convertFromCsvToJson(String mainNodeName, String csv) {
        List<CsvNodeDto> csvNodes = getCsvNodesObjects(csv);
        String json;
        json = "{\n\"" + mainNodeName + "\" : [\n";
        json = new StringBuilder(json).append("]\n}\n").toString();
        System.out.println(json);
        String[] lines = csv.split("\r\n", 2);
        for(int i = 1; i<lines.length;i++){
            String[] columns = lines[i].split(",");
            //wypisanie elementow
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
                            .name(element)
                            .build();
                        node.setColumn(counter++);
                        node.setFullName(column);
                        node.setParentName(parent);

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


    public String convertFromJsonToCsv(String json) {
        return null;
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
