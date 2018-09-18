package utils;

import exception.InesperadoException;
import io.restassured.response.ValidatableResponse;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Xml {

    private Xml() {
    }

    public static Map<String, Object> filter(final ValidatableResponse response, final String xPath) {
        Document document;
        try {
            document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(new ByteArrayInputStream(response.extract().body().asString().getBytes(StandardCharsets.UTF_8)));
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new InesperadoException(e);
        }

        NodeList urls;
        try {
            XPathExpression expr = XPathFactory.newInstance().newXPath().compile(xPath);
            urls = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            throw new InesperadoException(e);
        }

        Node node = urls.item(0);

        Map<String, Object> values = new HashMap<>();
        NodeList childList = node.getChildNodes();
        Node child;
        for (int i = 0; i < childList.getLength(); i++) {
            child = childList.item(i);
            if(child.getNodeName().contains("#")) {
                values.put(node.getNodeName(), child.getTextContent());
            } else {
                values.put(child.getNodeName(), child.getTextContent());
            }
        }
        return values;
    }

}
