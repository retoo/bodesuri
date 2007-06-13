package ui.spiel.brett;

import java.awt.Dimension;
import java.awt.Point;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import pd.Spiel;
import pd.brett.Feld;
import pd.brett.NormalesFeld;
import pd.brett.SpielerFeld;
import pd.spieler.Figur;
import pd.spieler.SpielerFarbe;
import ui.GUIController;
import ui.ressourcen.BrettXML;
import ui.ressourcen.Icons;
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

	public BrettView(GUIController controller, Spiel spiel,
			Map<pd.spieler.Spieler, Spieler> spielers) {
		setLayout(null);
		setPreferredSize(new Dimension(600, 600));
		setMinimumSize(new Dimension(600, 600));

		BrettXML brettXML;
 		try {
 			brettXML = new BrettXML("/ui/ressourcen/brett.xml");
		} catch (Exception e) {
			// Checked Exception in unchecked umwandeln
			throw new RuntimeException(e);
		}

		FeldMouseAdapter mouseAdapter = new FeldMouseAdapter(this, controller);

		// TODO: In ressourcen.Icons lösen (ähnlich wie bei Karten)
		IdentityHashMap<SpielerFarbe, Icon> farbeFeldMap = new IdentityHashMap<SpielerFarbe, Icon>();
		farbeFeldMap.put(SpielerFarbe.values()[0], Icons.FELD_ROT);
		farbeFeldMap.put(SpielerFarbe.values()[1], Icons.FELD_GRUEN);
		farbeFeldMap.put(SpielerFarbe.values()[2], Icons.FELD_BLAU);
		farbeFeldMap.put(SpielerFarbe.values()[3], Icons.FELD_GELB);
		
		IdentityHashMap<SpielerFarbe, Icon> farbeFigurMap = new IdentityHashMap<SpielerFarbe, Icon>();
		farbeFigurMap.put(SpielerFarbe.values()[0], Icons.FIGUR_ROT);
		farbeFigurMap.put(SpielerFarbe.values()[1], Icons.FIGUR_GRUEN);
		farbeFigurMap.put(SpielerFarbe.values()[2], Icons.FIGUR_BLAU);
		farbeFigurMap.put(SpielerFarbe.values()[3], Icons.FIGUR_GELB);

		for (Feld feld : spiel.getBrett().getAlleFelder()) {
			Feld2d feld2d;
			Point position = brettXML.getFelder().get(feld.getNummer());
			if (feld instanceof NormalesFeld) {
				feld2d = new NormalesFeld2d(position, feld, mouseAdapter);
			} else {
				Icon icon = farbeFeldMap.get(((SpielerFeld) feld).getSpieler().getFarbe());
				feld2d = new SpielerFeld2d(position, feld, mouseAdapter, icon);
			}
			felder.put(feld, feld2d);
			this.add(feld2d);

			if (feld.istBesetzt()) {
				Figur figur = feld.getFigur();
				Icon icon = farbeFigurMap.get(((SpielerFeld) feld).getSpieler().getFarbe());
				Figur2d figur2d = new Figur2d(figur, icon, this);
				this.setComponentZOrder(figur2d, 0);
				figuren.put(figur, figur2d);
			}
		}

		int i = 0;
		for (Spieler spieler : spielers.values()) {
			add(new SpielerView(spieler, brettXML.getSpielerViews().get(i)));
			i++;
		}

		JLabel hinweis = new JLabel();
		Point hinweisPos = brettXML.getHinweis();
		hinweis.setBounds(hinweisPos.x, hinweisPos.y, 400, 30);
		controller.registriereHinweisFeld(hinweis);
		add(hinweis);

		BrettMouseAdapter brettAdapter = new BrettMouseAdapter(this, controller);
		add(new SpielBrett2d(brettAdapter));
	}

	public Feld2d getFeld2d(Feld feld) {
		return felder.get(feld);
	}

	public Figur2d getFigur2d(Figur figur) {
		return figuren.get(figur);
	}
}
