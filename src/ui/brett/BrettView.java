package ui.brett;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JPanel;

import pd.Spiel;
import pd.brett.Feld;
import pd.spieler.Figur;
import pd.spieler.Spieler;
import ui.ressourcen.BrettLader;
import dienste.netzwerk.EndPunkt;

public class BrettView extends JPanel {

	private static final long serialVersionUID = 1L;
	// Wenn wir von Feld nach Feld2d und umgekehrt abbilden wollen:
	// HashIdentityMap
	// Evtl. auch noch eine Struktur von Figur2d
	List<Feld2d> felder = new Vector<Feld2d>();

	public BrettView(Spiel spiel, Spieler spielerIch, EndPunkt server) {
		// Nur vorübergehend, damit man sehen kann wie gross das Panel ist...
		setBackground(new Color(0, 150, 0));

		setLayout(null);
		setPreferredSize(new Dimension(600, 600));
		setMinimumSize(new Dimension(600, 600));

		Map<Integer, Point> koordinaten = null;
		try {
			koordinaten = BrettLader.ladeXML("src/spielplatz/brett.xml");
		} catch (Exception e) {
			// Checked Exception in unchecked umwandeln
			throw new RuntimeException(e);
		}

		for (Feld feld : spiel.getBrett().getAlleFelder()) {
			Feld2d feld2d = new Feld2d(koordinaten.get(feld.getNummer()), feld);
			felder.add(feld2d);
			System.out.println(feld);
			if (feld.istBesetzt()) {
				new Figur2d(feld2d, this);
			}
		}

		// TODO Diese For-Schaufe sollte durch setComponentZOrder anstelle von
		// add obsolet werden
		for (Feld2d feld2 : felder) {
			this.add(feld2);
		}
	}

	public Feld2d getFigur2d(Figur figur) {
		// TODO Auto-generated method stub
		return null;
	}
}
