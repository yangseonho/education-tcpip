package kr.nanoit.agent.education.tcpip;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class XMLParser {
    DocumentBuilderFactory documentBuilderFactory = null;
    DocumentBuilder documentBuilder = null;
    Document document = null;
    Element serverInfo = null;
    Node severInfoChildNodes = null;
    NodeList nodeList = null;
    Map<String, String> parsedData = new HashMap<>();





    String xmlString = "";
    XMLParser(String xmlString){
        this.xmlString = xmlString;
    }


    public Map<String, String> parsing() throws ParserConfigurationException, IOException, SAXException {


            documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(new InputSource(new StringReader(xmlString)));
            //nodeList = document.getElementsByTagName("IP");



            serverInfo = document.getDocumentElement();//ROOT 요소 가져오기(<ServerInfo>)
            System.out.println(serverInfo.getNodeName()); // < ServerInfo가 찍힘
            severInfoChildNodes = (Node) serverInfo.getChildNodes(); // ServerInfo의 자식 요소들을 담음 (IP, PORT)
            nodeList = severInfoChildNodes.getChildNodes();//node를 list화



            for (int i=0; i < nodeList.getLength(); i++){
                Node item = nodeList.item(i);
                if(item.getNodeType() == Node.ELEMENT_NODE){ // 이 조건식을 넣는 이유는 XML 파일의 빈 공백도 Node로 인식하기 때문에 노드의 타입이 Element일 경우(공백이 아닌 경우)에만 가져가겠다는 조건
                    System.out.println("Key : " + item.getNodeName());//Key
                    System.out.println("value : " + item.getTextContent());//value
                    parsedData.put(item.getNodeName(), item.getTextContent()); //결과값을 return해주기 위해 Map으로 만들기

                }
            }

            return parsedData;
    }

}
