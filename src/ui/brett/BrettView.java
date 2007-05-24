package ui.brett;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.Map;

import javax.swing.JPanel;

import pd.brett.Feld;
import pd.spieler.Figur;
import ui.ressourcen.BrettLader;
import applikation.client.BodesuriClient;

public class BrettView extends JPanel {

	private static final long serialVersionUID = 1L;

	public BrettView(BodesuriClient automat) {
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

		FeldMouseAdapter mouseAdapter = new FeldMouseAdapter(automat);
		for (Feld feld : automat.spiel.getBrett().getAlleFelder()) {
			Feld2d feld2d = new Feld2d(koordinaten.get(feld.getNummer()), feld, mouseAdapter);
			this.add(feld2d);
			if (feld.istBesetzt()) {
				this.setComponentZOrder(new Figur2d(feld2d, this), 0);
			}
		}
	}

	public Feld2d getFigur2d(Figur figur) {
		System.out.println("TODO!!!!!!");
	    // TODO Auto-generated method stub
	    return null;
    }
}
