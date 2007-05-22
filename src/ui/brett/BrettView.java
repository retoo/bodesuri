package ui.brett;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.Map;

import javax.swing.JPanel;

import pd.Spiel;
import pd.brett.Feld;
import pd.spieler.Figur;
import pd.spieler.Spieler;
import ui.ressourcen.BrettLader;
import dienste.netzwerk.EndPunkt;

public class BrettView extends JPanel {

	private static final long serialVersionUID = 1L;

	public BrettView(Spiel spiel, Spieler spielerIch, EndPunkt server) {
		// Nur vorübergehend, damit man sehen kann wie gross das Panel ist...
		setBackground(new Color(0, 150, 0));

		setLayout(null);
		setPreferredSize(new Dimension(600, 600));
		setMinimumSize(new Dimension(600, 600));

		Map<Integer, Point> koordinaten = null;
		try {
			koordinaten = BrettLader.ladeXML("src/ui/ressourcen/brett.xml");
		} catch (Exception e) {
			// Checked Exception in unchecked umwandeln
			throw new RuntimeException(e);
		}

		for (Feld feld : spiel.getBrett().getAlleFelder()) {
			Feld2d feld2d = new Feld2d(koordinaten.get(feld.getNummer()), feld);
			this.add(feld2d);
			System.out.println(feld);
			if (feld.istBesetzt()) {
				this.setComponentZOrder(new Figur2d(feld2d, this), 0);
			}
		}
	}

	public Feld2d getFigur2d(Figur figur) {
		// TODO Auto-generated method stub
		return null;
	}
}
