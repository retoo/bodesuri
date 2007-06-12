package ui.spiel.brett;

import java.awt.Dimension;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;

import pd.Spiel;
import pd.brett.Feld;
import pd.brett.SpielerFeld;
import pd.spieler.Figur;
import ui.GUIController;
import ui.ressourcen.BrettLader;
import ui.spiel.brett.felder.Feld2d;
import ui.spiel.brett.felder.FeldMouseAdapter;
import ui.spiel.brett.felder.NormalesFeld2d;
import ui.spiel.brett.felder.SpielerFeld2d;
import applikation.client.pd.Spieler;

/**
 * JPanel, Graphische Darstellung des Spielbrettes.
 */
public class BrettView extends JPanel {
	private Map<Feld, Feld2d> felder = new HashMap<Feld, Feld2d>();
	private Map<Figur, Figur2d> figuren = new HashMap<Figur, Figur2d>();

	public BrettView(GUIController controller, Spiel spiel, Map<pd.spieler.Spieler, Spieler> spielers) {
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

		for (Feld feld : spiel.getBrett().getAlleFelder()) {
			Feld2d feld2d;
			if (feld instanceof SpielerFeld){
				feld2d = new SpielerFeld2d(koordinaten.get(feld.getNummer()),
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
		
		//TODO: Das muss ins XML!
		Vector<Point> spielerViewPos = new Vector<Point>();
		spielerViewPos.add(new Point(460, 20));
		spielerViewPos.add(new Point(60, 20));
		spielerViewPos.add(new Point(60, 560));
		spielerViewPos.add(new Point(460, 560));
		int i = 0;
		
		for (Spieler spieler : spielers.values()) {
			add(new SpielerView(spieler, spielerViewPos.get(i)));
			i++;
		}
		
		JLabel hinweis = new JLabel();
		//TODO: Position muss auch ins XML!
		hinweis.setBounds(200, 280, 400, 30);
		controller.registriereHinweisFeld(hinweis);
		add(hinweis);
		
		
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
