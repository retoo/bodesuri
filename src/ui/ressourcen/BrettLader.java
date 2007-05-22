package ui.ressourcen;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class BrettLader {
	public static Map<Integer, Point> ladeXML(String datei)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		Map<Integer, Point> map = new HashMap<Integer, Point>();
		
		Document document = builder.parse(new File(datei));
		Element brett = document.getDocumentElement();
		
		NodeList felder = brett.getElementsByTagName("feld");
		for (int i = 0; i < felder.getLength(); ++i) {
			Element feld = (Element)felder.item(i);
			
			Integer nummer = Integer.valueOf(feld.getAttribute("nummer"));
			
			Point point = new Point();
			Node x = feld.getElementsByTagName("x").item(0);
			Node y = feld.getElementsByTagName("y").item(0);
			point.x = Integer.valueOf(x.getTextContent());
			point.y = Integer.valueOf(y.getTextContent());
			
			map.put(nummer, point);
		}
		
		return map;
	}
	
	// TODO: Unit-Test draus machen
	public static void main(String[] args)
			throws ParserConfigurationException, SAXException, IOException {
		String datei = "src/spielplatz/brett.xml";
		Map<Integer, Point> felder = BrettLader.ladeXML(datei);
		System.out.println(felder.size());
		System.out.println(felder);
	}
}
