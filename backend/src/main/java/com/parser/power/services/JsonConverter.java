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

@Service
@RequiredArgsConstructor
public class JsonConverter {

    public String convertFromCsvToJson(String csv) {
        return null;
    }

    public String convertFromXmlToJson(String xml) throws ParserConfigurationException, IOException, SAXException {
        //Get Document Builder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        //Build Document
        File temp = File.createTempFile("name", ".xml");

        // Delete temp file when program exits.
        temp.deleteOnExit();

        // Write to temp file
        BufferedWriter out = new BufferedWriter(new FileWriter(temp));
        out.write(xml);
        out.close();
        Document document = builder.parse(temp);

        //Normalize the XML Structure; It's just too important !!
        document.getDocumentElement().normalize();

        //Here comes the root node
        Element root = document.getDocumentElement();
        System.out.println(root.getNodeName());

        //Get all employees
        NodeList nList = document.getElementsByTagName(root.getNodeName());

        System.out.println("============================");

        visitChildNodes(nList);
        return "" ;
    }

    private static void visitChildNodes(NodeList nList) {
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node node = nList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                XPathExpression path = null;
                try {
                    path = XPathFactory.newInstance().newXPath().compile("/"+node.getNodeName()+"/*");
                } catch (XPathExpressionException e) {
                    e.printStackTrace();
                }
                NodeList nodeList = null;
                try {
                    nodeList = (NodeList) path.evaluate(node, XPathConstants.NODESET);
                } catch (XPathExpressionException e) {
                    e.printStackTrace();
                }
                if (node.hasAttributes()) {
                    NamedNodeMap nodeMap = node.getAttributes();
                    for (int i = 0; i < nodeMap.getLength(); i++) {
                        Node tempNode = nodeMap.item(i);
                        System.out.println("Attr name : " + tempNode.getNodeName() + "; Value = " + tempNode.getNodeValue());
                    }
                    if (node.hasChildNodes()) {
                        visitChildNodes(node.getChildNodes());
                    }
                }
                if (nodeList.getLength()>0) {
                    System.out.println(node.getNodeName() + ": {");
                    visitChildNodes(node.getChildNodes());
                    System.out.println("}");
                }else
                    System.out.println("\"" +node.getNodeName() + "\" : \"" + node.getTextContent()+"\"");
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
