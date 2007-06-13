package ui.ressourcen;

import java.awt.Point;
import java.io.IOException;
import java.net.URL;
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

public class BrettXML {
	private Map<Integer, Point> felder;
	private Map<Integer, Point> spielerViews;
	private Point hinweis;

	public BrettXML(String pfad)
			throws ParserConfigurationException, SAXException, IOException {
		URL url = BrettXML.class.getResource(pfad);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(url.toExternalForm());
		lade(document);
	}
	
	private void lade(Document document) {
		Element brett = document.getDocumentElement();
		felder = positionenAuslesen("feld", brett);
		spielerViews = positionenAuslesen("spielerView", brett);
		hinweis = positionenAuslesen("hinweis", brett).get(0);
	}
	
	private static Map<Integer, Point> positionenAuslesen(String element, Element brett) {
		Map<Integer, Point> map = new HashMap<Integer, Point>();
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
		return map;
	}

	public Map<Integer, Point> getFelder() {
		return felder;
	}

	public Point getHinweis() {
		return hinweis;
	}

	public Map<Integer, Point> getSpielerViews() {
		return spielerViews;
	}
}
