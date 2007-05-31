package ui.spiel.brett;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import pd.brett.BankFeld;
import pd.brett.Feld;
import ui.ressourcen.BrettLader;
import applikation.client.ClientController;

/**
 * JPanel, Graphische Darstellung des Spielbrettes.
 */
public class BrettView extends JPanel {
	private static final long serialVersionUID = 1L;
	private Map<Feld, Feld2d> felder = new HashMap<Feld, Feld2d>();

	public BrettView(ClientController controller) {
		// Nur vorübergehend, damit man sehen kann wie gross das Panel ist...
		setBackground(new Color(0, 150, 0));

		setLayout(null);
		setPreferredSize(new Dimension(600, 600));
		setMinimumSize(new Dimension(600, 600));

		Map<Integer, Point> koordinaten = null;
		try {
			koordinaten = BrettLader.ladeXML("bin/ui/ressourcen/brett.xml");
		} catch (Exception e) {
			// Checked Exception in unchecked umwandeln
			throw new RuntimeException(e);
		}

		FeldMouseAdapter mouseAdapter = new FeldMouseAdapter(controller);
		for (Feld feld : controller.getSpiel().getBrett().getAlleFelder()) {
			Feld2d feld2d;
			if (feld instanceof BankFeld) {
				feld2d = new BankFeld2d(koordinaten.get(feld.getNummer()),
				                        feld, mouseAdapter);
			} else {
				feld2d = new NormalesFeld2d(koordinaten.get(feld.getNummer()),
				                            feld, mouseAdapter);
			}
			felder.put(feld, feld2d);
			this.add(feld2d);
			if (feld.istBesetzt()) {
				Figur2d figur2d = new Figur2d(feld2d, this);
				this.setComponentZOrder(figur2d, 0);
				feld.getFigur().addObserver(figur2d);
			}
		}
	}

	public Feld2d getFeld2d(Feld feld) {
		return felder.get(feld);
	}
}
