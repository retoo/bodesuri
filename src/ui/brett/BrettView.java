/**
 * @(#) BrettView.java
 */

package ui.brett;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import java.awt.Point;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jdom.Element;

import spielplatz.FelderXML;

import pd.brett.Feld;
import pd.spieler.Figur;

public class BrettView extends JPanel {

	private static final long serialVersionUID = 1L;

	Map<Feld, Feld2d> felder = new HashMap<Feld, Feld2d>();

	private JLabel debug;

	public BrettView() {
		// Nur vorübergehend, damit man sehen kann wie gross das Panel ist...
		setBackground(new Color(0, 150, 0));

		setLayout(null);
		Dimension groesse = new Dimension(600, 600);
		setPreferredSize(groesse);
		setMaximumSize(groesse);
		setMinimumSize(groesse);
		setAlignmentX(Component.LEFT_ALIGNMENT);

		debug = new JLabel();
		debug.setBounds(20, 570, 200, 30);
		add(debug);


		// Point q = new Point(430, 300);
		// for (int i = 0; i < 20; i++) {
		// Feld2d feld = new Feld2d(q);
		// felder.add(feld);
		// }
		Feld feld = null;

		for (int i = 0; i < 4; i++) {
			FelderXML felderXML = new FelderXML();
			while (felderXML.hasNext()) {
				Point point = new Point();
				Element feldXML = (Element) felderXML.getNext();
				if (i == 0) {
					point.x = felderXML.getX(feldXML);
					point.y = felderXML.getY(feldXML);
				}
				if (i == 1) {
					point.x = felderXML.getY(feldXML);
					point.y = 600 - felderXML.getX(feldXML);
				}
				if (i == 2) {
					point.x = 600 - felderXML.getX(feldXML);
					point.y = 600 - felderXML.getY(feldXML);
				}
				if (i == 3) {
					point.x = 600 - felderXML.getY(feldXML);
					point.y = felderXML.getX(feldXML);
				}

				Feld2d feld2d = new Feld2d(point);
				felder.put(feld, feld2d);
			}


		}


//		add(new Figur2d(felder.get(0)));
//		add(new Figur2d(felder.get(16)));
//		add(new Figur2d(felder.get(32)));
//		add(new Figur2d(felder.get(48)));

		add(new Figur2d(felder.get(0), this));

		for (Feld2d feld2d : felder.values()) {
			this.add(feld2d);

		}
	}

	public Feld2d getFigur2d(Figur figur) {
		// TODO Auto-generated method stub
		return null;
	}
}
