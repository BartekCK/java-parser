package com.parser.power.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
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

    public String convertFromCsvToJson(String csv) {
        return null;
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
        visitChildNodes(nList);
        json = new StringBuilder(json).append("}\n").toString();
        json = json.replace(",\n}", "\n}");
        return json;
    }

    private void visitChildNodes(NodeList nList) {
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node node = nList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                if (node.hasAttributes()) {
                    NamedNodeMap nodeMap = node.getAttributes();
                    for (int i = 0; i < nodeMap.getLength(); i++) {
                        Node tempNode = nodeMap.item(i);
                        if (!alreadyVisitedAttr.containsKey(node) || !alreadyVisitedAttr.get(node).contains(tempNode)) {
                            json = new StringBuilder(json).append("\"" + tempNode.getNodeName() + "\" : \"" + tempNode.getNodeValue() + "\",\n").toString();
                            if(!alreadyVisitedAttr.containsKey(node)) {
                                alreadyVisitedAttr.put(node, new ArrayList<>());
                            }
                            alreadyVisitedAttr.get(node).add(tempNode);
                        }
                    }
                }
                NodeList nl = ((Element) node).getElementsByTagName("*");
                NodeList siblings = ((Element) node).getElementsByTagName(node.getNodeName());
                if (nl.getLength() > 0) {
                    json = new StringBuilder(json).append("\"" + node.getNodeName() + "\" : {\n").toString();
                    visitChildNodes(nl);
                    json = new StringBuilder(json).append("},\n").toString();
                } else {
                    if (!alreadyVisited.contains(node)) {
                        if (siblings.getLength() > 1) {
                            for (int temp2 = 0; temp2 < siblings.getLength(); temp2++) {
                                //System.out.println(siblings);
                            }
                        }
                        json = new StringBuilder(json).append("\"" + node.getNodeName() + "\" : \"" + node.getTextContent() + "\",\n").toString();
                        this.alreadyVisited.add(node);
                    }
                }
            }
        }
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

}
