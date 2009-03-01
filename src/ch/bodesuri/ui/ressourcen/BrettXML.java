/*
 * Copyright (C) 2007  Danilo Couto, Philippe Eberli,
 *                     Pascal Hobus, Reto Schüttel, Robin Stocker
 *
 * This file is part of Bodesuri.
 *
 * Bodesuri is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.
 *
 * Bodesuri is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Bodesuri; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


package ch.bodesuri.ui.ressourcen;

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

/**
 * Diese Klasse dient zum auslesen der Koordinatenpunkte aus der XML-Datei.
 * Diese Koordinaten werden für die Platzierung der verschiedenen Grafikelementen benötigt.
 */
public class BrettXML {
	private Map<Integer, Point> felder;
	private Map<Integer, Point> spielerViews;
	private Map<Integer, Point> spielerZustandsViews;
	private Map<Integer, Point> jokerKarten;
	private Point zentrum;

	public BrettXML(String pfad)
			throws ParserConfigurationException, SAXException, IOException {
		URL url = BrettXML.class.getResource(pfad);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(url.toExternalForm());
		lade(document);
	}
	
	/**
	 * Benötigte XML-Datei laden.
	 * 
	 * @param document XML-Datei
	 */
	private void lade(Document document) {
		Element brett = document.getDocumentElement();
		felder = positionenAuslesen("feld", brett);
		spielerViews = positionenAuslesen("spielerView", brett);
		spielerZustandsViews = positionenAuslesen("spielerZustandsView", brett);
		zentrum = positionenAuslesen("zentrum", brett).get(0);
		jokerKarten = positionenAuslesen("jokerKarten", brett);
	}
	
	/**
	 * Hier werden die einzelnen Koordinatenpünkte nach den Parametern ausgelesen.
	 * 
	 * @param element auszulesende Felder
	 * @param brett Dokument welches durchiteriert werden soll.
	 * @return HashMap mit den jeweiligen Koordinatenpünkten
	 */
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

	public Point getZentrum() {
		return zentrum;
	}

	public Map<Integer, Point> getSpielerViews() {
		return spielerViews;
	}

	public Map<Integer, Point> getSpielerZustandsViews() {
		return spielerZustandsViews;
	}

	public Map<Integer, Point> getJokerKarten(){
		return jokerKarten;
	}
}
