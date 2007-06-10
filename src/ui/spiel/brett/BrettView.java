package ui.spiel.brett;

import java.awt.Dimension;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import pd.brett.Feld;
import pd.spieler.Figur;
import ui.GUIController;
import ui.ressourcen.BrettLader;

/**
 * JPanel, Graphische Darstellung des Spielbrettes.
 */
public class BrettView extends JPanel {
	private Map<Feld, Feld2d> felder = new HashMap<Feld, Feld2d>();
	private Map<Figur, Figur2d> figuren = new HashMap<Figur, Figur2d>();

	public BrettView(GUIController controller) {
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

		FeldMouseAdapter mouseAdapter = new FeldMouseAdapter(this, controller);
		
		for (Feld feld : controller.getSpiel().getBrett().getAlleFelder()) {
			Feld2d feld2d;
			if (feld.istBank()) {
				feld2d = new BankFeld2d(koordinaten.get(feld.getNummer()),
						feld, mouseAdapter);
			} else {
				feld2d = new NormalesFeld2d(koordinaten.get(feld.getNummer()),
						feld, mouseAdapter);
			}
			felder.put(feld, feld2d);
			this.add(feld2d);
			
			if (feld.istBesetzt()) {
				Figur figur = feld.getFigur();
				Figur2d figur2d = new Figur2d(figur, this);
				this.setComponentZOrder(figur2d, 0);
				figuren.put(figur, figur2d);
			}
		}
		
		BrettMouseAdapter brettAdapter = new BrettMouseAdapter(this, controller);
		add(new SpielBrett2d( brettAdapter ));
	}

	public Feld2d getFeld2d(Feld feld) {
		return felder.get(feld);
	}
	
	public Figur2d getFigur2d(Figur figur) {
		return figuren.get(figur);
	}
}
