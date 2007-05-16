package spielplatz;

import java.io.IOException;
import java.util.Iterator;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class FelderXML {
	
	public static void main(String[] args) throws JDOMException, IOException{
		String filename = "src/spielplatz/felder.xml";
		Document doc = (Document) new SAXBuilder().build(filename);
		Element brett = doc.getRootElement();
		
		Iterator felder = brett.getChildren("feld").iterator();
		while(felder.hasNext()){
			Element feld = (Element)felder.next();
			System.out.println(feld.getAttributeValue("name"));
			System.out.println("x-Wert: " + feld.getChild("koordinaten").getChild("x").getValue());
			System.out.println("y-Wert: " + feld.getChild("koordinaten").getChild("y").getValue());
		}
		  
	}
}
