package spielplatz;

import java.awt.Point;
import java.io.IOException;
import java.util.Iterator;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class FelderXML {
	Document doc;

	Element brett;

	String filename;

	Iterator felder;

	public FelderXML() {
		this.filename = "src/spielplatz/felder.xml";

		try {
			this.doc = (Document) new SAXBuilder().build(filename);
			this.brett = doc.getRootElement();
			this.felder = this.brett.getChildren("feld").iterator();
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Object getNext() {
		return this.felder.next();
	}

	public boolean hasNext() {
		return this.felder.hasNext();
	}

	public int getX(Element feld) {
		return Integer.valueOf(feld.getChild("koordinaten").getChild("x")
				.getValue());
	}

	public int getY(Element feld) {
		return Integer.valueOf(feld.getChild("koordinaten").getChild("y")
				.getValue());
	}

	public Point getKoordinaten(int i) {
		Element feld;
		if (hasNext()) {
			feld = (Element) this.getNext();
		} else {
			felder = this.brett.getChildren("feld").iterator();
			feld = (Element) this.getNext();
		}

		if (i / 24 == 1) {
			return new Point(getY(feld), 600 - getX(feld));
		}
		if (i / 24 == 2) {
			return new Point(600 - getX(feld), 600 - getY(feld));
		}
		if (i / 24 == 3) {
			return new Point(600 - getY(feld), getX(feld));
		}

		return new Point(getX(feld), getY(feld));
	}

	public static void main(String[] args) throws JDOMException, IOException {

		FelderXML felder = new FelderXML();

		while (felder.hasNext()) {
			Element feld = (Element) felder.getNext();
			System.out.println(feld.getAttributeValue("name"));
			System.out.println("x-Wert: "
					+ feld.getChild("koordinaten").getChild("x").getValue());
			System.out.println("y-Wert: "
					+ feld.getChild("koordinaten").getChild("y").getValue());
		}

	}

}
