package ui.spiel.brett;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
	private BrettXML brettXML;

	public BrettView(GUIController controller, Spiel spiel,
			Map<pd.spieler.Spieler, Spieler> spielers) {
		setLayout(null);
		setPreferredSize(new Dimension(600, 600));
		setMinimumSize(new Dimension(600, 600));
		
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

		// SpielerViews darstellen
		zeichneSpielerView(controller, spielers);

		// Hinweis darstellen
		zeichneHinweis(controller);

		BrettMouseAdapter brettAdapter = new BrettMouseAdapter(this, controller);
		add(new SpielBrett2d(brettAdapter));
	}

	private void zeichneSpielerView( GUIController controller, Map<pd.spieler.Spieler, Spieler> spielers) {
		int i = 0;
		for (Spieler spieler : spielers.values()) {
			
			JPanel spielerView = new SpielerView(controller, spieler);
			
			//	Views
			JPanel hinweisView = new JPanel();
			hinweisView.setOpaque(false);
			
			GridBagLayout gbl = new GridBagLayout();
			hinweisView.setLayout(gbl);
			
			// Spezielles Verfahren, um ein JPanel zu zentrieren
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.CENTER;		
			gbl.setConstraints(spielerView, gbc);
			hinweisView.add(spielerView);
			hinweisView.setBounds((int) brettXML.getSpielerViews().get(i).getX(), (int) brettXML.getSpielerViews().get(i).getY(), 170, 30);
			add(hinweisView);
			
			//add(new SpielerView(spieler, brettXML.getSpielerViews().get(i)));
			i++;
		}
	}
	
	private void zeichneHinweis(GUIController controller){
		// JLabel
		JLabel hinweisLabel = new JLabel();
		hinweisLabel.setFont(hinweisLabel.getFont().deriveFont(1));
		controller.registriereHinweisFeld(hinweisLabel);
		
		//	Views
		JPanel hinweisView = new JPanel();
		hinweisView.setOpaque(false);
		
		GridBagLayout gbl = new GridBagLayout();
		hinweisView.setLayout(gbl);
		
		// Spezielles Verfahren, um ein JPanel zu zentrieren
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.CENTER;		
		gbl.setConstraints(hinweisLabel, gbc);
		hinweisView.add(hinweisLabel);
		
		Point hinweisPos = brettXML.getHinweis();
		
		hinweisView.setBounds(hinweisPos.x, hinweisPos.y, 211, 40);
		add(hinweisView);
		
		JLabel hinweisVertiefung = new JLabel(Icons.HINWEIS);
		hinweisVertiefung.setBounds(hinweisPos.x - 6, hinweisPos.y, 222, 41);
		add(hinweisVertiefung);
		
	}
	

	public Feld2d getFeld2d(Feld feld) {
		return felder.get(feld);
	}

	public Figur2d getFigur2d(Figur figur) {
		return figuren.get(figur);
	}
}
