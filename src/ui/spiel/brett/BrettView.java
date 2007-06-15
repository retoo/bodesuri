package ui.spiel.brett;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import pd.brett.SpielerFeld;
import pd.spieler.Figur;
import pd.spieler.SpielerFarbe;
import ui.ressourcen.BrettXML;
import ui.ressourcen.Icons;
import ui.spiel.brett.felder.Feld2d;
import ui.spiel.brett.felder.FeldMouseAdapter;
import ui.spiel.brett.felder.Figur2d;
import ui.spiel.brett.felder.FigurenManager;
import ui.spiel.brett.felder.NormalesFeld2d;
import ui.spiel.brett.felder.SpielerFeld2d;
import applikation.client.controller.Steuerung;
import applikation.client.pd.Feld;
import applikation.client.pd.Spiel;
import applikation.client.pd.Spieler;

/**
 * JPanel, Graphische Darstellung des Spielbrettes.
 */
public class BrettView extends JPanel implements Observer {
	private BrettXML brettXML;
	private FigurenManager figurenManager;
	private JLabel hinweisLabel;
	private Spiel spiel;

	public BrettView(Steuerung steuerung, Spiel spiel) {
		this.spiel = spiel;

		setLayout(null);
		setPreferredSize(new Dimension(600, 600));
		setMinimumSize(new Dimension(600, 600));

		try {
			brettXML = new BrettXML("/ui/ressourcen/brett.xml");
		} catch (Exception e) {
			// Checked Exception in unchecked umwandeln
			throw new RuntimeException(e);
		}

		/* Figuren bereitstellen */
		IdentityHashMap<SpielerFarbe, Icon> farbeFigurMap = new IdentityHashMap<SpielerFarbe, Icon>();
		farbeFigurMap.put(SpielerFarbe.values()[0], Icons.FIGUR_ROT);
		farbeFigurMap.put(SpielerFarbe.values()[1], Icons.FIGUR_GRUEN);
		farbeFigurMap.put(SpielerFarbe.values()[2], Icons.FIGUR_BLAU);
		farbeFigurMap.put(SpielerFarbe.values()[3], Icons.FIGUR_GELB);

		figurenManager = new FigurenManager();
		for (Spieler spieler : spiel.getSpieler()) {
			for (Figur figur : spieler.getFiguren()) {
				Icon icon = farbeFigurMap.get(spieler.getFarbe());
				Figur2d figur2d = new Figur2d(figur, icon);
				this.setComponentZOrder(figur2d, 0);

				figurenManager.registriere(figur, figur2d);
			}
		}

		FeldMouseAdapter mouseAdapter = new FeldMouseAdapter(steuerung);

		// TODO: In ressourcen.Icons lösen (ähnlich wie bei Karten)
		IdentityHashMap<SpielerFarbe, Icon> farbeFeldMap = new IdentityHashMap<SpielerFarbe, Icon>();
		farbeFeldMap.put(SpielerFarbe.values()[0], Icons.FELD_ROT);
		farbeFeldMap.put(SpielerFarbe.values()[1], Icons.FELD_GRUEN);
		farbeFeldMap.put(SpielerFarbe.values()[2], Icons.FELD_BLAU);
		farbeFeldMap.put(SpielerFarbe.values()[3], Icons.FELD_GELB);

		for (Feld feld : spiel.getBrett().getAlleFelder()) {
			Feld2d feld2d;

			Point position = brettXML.getFelder().get(feld.getNummer());

			if (feld.istNormal()) {
				feld2d = new NormalesFeld2d(position, feld, mouseAdapter,
				                            figurenManager);
			} else {
				// TODO Ohne Cast möglich? --Philippe -- glaubs nein (-reto)
				SpielerFeld f = (SpielerFeld) feld.getFeld();
				Icon icon = farbeFeldMap.get(f.getSpieler().getFarbe());
				feld2d = new SpielerFeld2d(position, feld, mouseAdapter, icon,
				                           figurenManager);
			}

			this.add(feld2d);
		}

		// SpielerViews darstellen
		zeichneSpielerView(steuerung, spiel.getSpieler());

		erstelleHinweis();

		BrettMouseAdapter brettAdapter = new BrettMouseAdapter(steuerung);
		add(new SpielBrett2d(brettAdapter));

		spiel.addObserver(this);
	}

	private void zeichneSpielerView(Steuerung steuerung, List<Spieler> spielers) {
		int i = 0;
		for (Spieler spieler : spielers) {

			JPanel spielerView = new SpielerView(spieler);

			// Views
			JPanel hinweisView = new JPanel();
			hinweisView.setOpaque(false);

			GridBagLayout gbl = new GridBagLayout();
			hinweisView.setLayout(gbl);

			// Spezielles Verfahren, um ein JPanel zu zentrieren
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.CENTER;
			gbl.setConstraints(spielerView, gbc);
			hinweisView.add(spielerView);
			hinweisView.setBounds((int) brettXML.getSpielerViews().get(i)
			                                    .getX(),
			                      (int) brettXML.getSpielerViews().get(i)
			                                    .getY(), 170, 30);
			add(hinweisView);

			i++;
		}
	}

	private void erstelleHinweis() {
		// JLabel
		hinweisLabel = new JLabel("test");
		hinweisLabel.setFont(hinweisLabel.getFont().deriveFont(1));
		hinweisLabel.setHorizontalAlignment(SwingConstants.CENTER);

		Point hinweisPos = brettXML.getHinweis();

		hinweisLabel.setBounds(hinweisPos.x, hinweisPos.y, 211, 40);
		add(hinweisLabel);

		JLabel hinweisVertiefung = new JLabel(Icons.HINWEIS);
		hinweisVertiefung.setBounds(hinweisPos.x - 6, hinweisPos.y, 222, 41);
		add(hinweisVertiefung);
	}

	public void update(Observable o, Object arg) {
		/* Hinweisfeld updaten */
		hinweisLabel.setText(spiel.getHinweis());
	}
}
