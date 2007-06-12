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

	public static Map<Integer, Point> felderMap;
	public static Map<Integer, Point> spielerViewMap;
	private static HashMap<Integer, Point> hinweisMap;

	static DocumentBuilderFactory factory = DocumentBuilderFactory
			.newInstance();
	static DocumentBuilder builder;
	
	public static Map<Integer, Point> ladeFelder(String datei)
			throws ParserConfigurationException, SAXException, IOException {
		builder = factory.newDocumentBuilder();
		felderMap = new HashMap<Integer, Point>();
		Document document = builder.parse(new File(datei));
		Element brett = document.getDocumentElement();
		mapFuellen("feld", felderMap, brett);
		return felderMap;
	}

	public static Map<Integer, Point> ladeSpielerView(String datei)
			throws ParserConfigurationException, SAXException, IOException {
		builder = factory.newDocumentBuilder();
		spielerViewMap = new HashMap<Integer, Point>();
		Document document = builder.parse(new File(datei));
		Element brett = document.getDocumentElement();
		mapFuellen("spielerView", spielerViewMap, brett);
		return spielerViewMap;
	}

	public static Map<Integer, Point> ladeHinweis(String datei)
			throws ParserConfigurationException, SAXException, IOException {
		builder = factory.newDocumentBuilder();
		hinweisMap = new HashMap<Integer, Point>();
		Document document = builder.parse(new File(datei));
		Element brett = document.getDocumentElement();
		mapFuellen("hinweis", hinweisMap, brett);
		return hinweisMap;
	}

	private static void mapFuellen(String element, Map<Integer, Point> map,
			Element brett) {
		NodeList felder = brett.getElementsByTagName(element);
		for (int i = 0; i < felder.getLength(); ++i) {
			Element feld = (Element) felder.item(i);

			Integer nummer = Integer.valueOf(feld.getAttribute("nummer"));

			Point point = new Point();
			Node x = feld.getElementsByTagName("x").item(0);
			Node y = feld.getElementsByTagName("y").item(0);
			point.x = Integer.valueOf(x.getTextContent());
			point.y = Integer.valueOf(y.getTextContent());

			map.put(nummer, point);
		}
	}
}
