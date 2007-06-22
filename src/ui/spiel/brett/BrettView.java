package ui.spiel.brett;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import pd.brett.SpielerFeld;
import pd.spieler.Figur;
import ui.erweiterungen.BLabel;
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
		setOpaque(false);

		try {
			brettXML = new BrettXML("/ui/ressourcen/brett.xml");
		} catch (Exception e) {
			// Checked Exception in unchecked umwandeln
			throw new RuntimeException(e);
		}

		/* Figuren bereitstellen */
		figurenManager = new FigurenManager();
		for (Spieler spieler : spiel.getSpieler()) {
			for (Figur figur : spieler.getFiguren()) {
				Icon icon = Icons.getFigurIcon(spieler.getFarbe());
				Figur2d figur2d = new Figur2d(figur, icon);
				this.setComponentZOrder(figur2d, 0);

				figurenManager.registriere(figur, figur2d);
			}
		}

		FeldMouseAdapter mouseAdapter = new FeldMouseAdapter(steuerung);

		BLabel hover = new BLabel(Icons.FELD_HOVER);
		hover.setVisible(false);

		this.setComponentZOrder(hover, getComponentCount());

		int i = 0;
		for (Feld feld : spiel.getBrett().getAlleFelder()) {
			Feld2d feld2d;

			Point position = brettXML.getFelder().get(feld.getNummer());

			if (feld.istNormal()) {
				feld2d = new NormalesFeld2d(position, feld, mouseAdapter, hover,
				                            figurenManager);
			} else {
				SpielerFeld f = (SpielerFeld) feld.getFeld();
				Icon icon = Icons.getSpielerFeldIcon(f.getSpieler().getFarbe());
				feld2d = new SpielerFeld2d(position, feld, mouseAdapter, icon, hover,
				                           figurenManager);
			}

			this.setComponentZOrder(feld2d, this.getComponentCount());
			i++;
		}


		// SpielerViews darstellen
		zeichneSpielerView(steuerung, spiel.getSpieler());

		erstelleHinweis();

		BrettMouseAdapter brettAdapter = new BrettMouseAdapter(steuerung);
		Brett2d brett2d = new Brett2d(brettAdapter);
		setPreferredSize(brett2d.getPreferredSize());
		setMinimumSize(brett2d.getMinimumSize());
		setMaximumSize(brett2d.getMaximumSize());

		add(brett2d);

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
			hinweisView.setBounds(brettXML.getSpielerViews().get(i).x,
			                      brettXML.getSpielerViews().get(i).y,
			                      170, 30);
			add(hinweisView);

			i++;
		}
	}

	private void erstelleHinweis() {
		// JLabel
		hinweisLabel = new JLabel();
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
		String zaehlerString = "";
		int zaehler = spiel.getZaehler();
		if (zaehler == 0) {
			zaehlerString = "      ";
		} else if (zaehler > 0) {
			zaehlerString = " (" + zaehler + ")";
		}

		/* Hinweisfeld updaten */
		hinweisLabel.setText(spiel.getHinweis() + zaehlerString);
	}
}
