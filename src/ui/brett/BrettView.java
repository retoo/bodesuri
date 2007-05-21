/**
 * @(#) BrettView.java
 */

package ui.brett;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;
import java.util.Vector;

import pd.Spiel;
import pd.brett.Feld;
import pd.spieler.Figur;
import pd.spieler.Spieler;
import spielplatz.FelderXML;
import ui.HauptView;
import dienste.netzwerk.EndPunkt;

public class BrettView extends HauptView {

	private static final long serialVersionUID = 1L;
	List<Feld2d> felder = new Vector<Feld2d>();

	public BrettView(Spiel spiel, Spieler spielerIch, EndPunkt server) {
		// Nur vorübergehend, damit man sehen kann wie gross das Panel ist...
		setBackground(new Color(0, 150, 0));

		setLayout(null);
		Dimension groesse = new Dimension(600, 600);
		setPreferredSize(groesse);
		setMaximumSize(groesse);
		setMinimumSize(groesse);
		setAlignmentX(Component.LEFT_ALIGNMENT);

		Feld startFeld = spiel.getBrett().getBankFeldVon(spielerIch);
		FelderXML felderXML = new FelderXML();

		Feld feld = startFeld;
		int i = 0;
		do {
			Feld2d feld2d = new Feld2d(felderXML.getKoordinaten(i), feld);
			felder.add(feld2d);
			System.out.println(i);
			System.out.println(feld);
			if (feld.istBesetzt()) {
				new Figur2d(feld2d, this);
			}

			feld = feld.getNaechstes();
			i++;
		} while (feld != startFeld);

		for (Feld2d feld2 : felder) {
			this.add(feld2);

		}
	}

	public Feld2d getFigur2d(Figur figur) {
		// TODO Auto-generated method stub
		return null;
	}
}
