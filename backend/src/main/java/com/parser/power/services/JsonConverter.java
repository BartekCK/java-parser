package com.parser.power.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JsonConverter {

    public String convertFromCsvToJson(String csv) {
        return null;
    }

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

        visitChildNodes(nList);
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
                        json = new StringBuilder(json).append("\"" + tempNode.getNodeName() + "\" : \"" + tempNode.getNodeValue() + "\",\n").toString();
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

    public String convertFromYamlToJson(String yaml) {
        return null;
    }

    public String convertFromJsonToCsv(String json) {
        return null;
    }

    public String convertFromJsonToXml(String json) {
        return null;
    }

    public String convertFromJsonToYaml(String json) {
        return null;
    }

}
