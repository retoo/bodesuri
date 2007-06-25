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
import pd.spieler.SpielerFarbe;
import ui.geteiltes.BLabel;
import ui.geteiltes.SpielerView;
import ui.ressourcen.BrettXML;
import ui.ressourcen.Icons;
import ui.spiel.brett.felder.Feld2d;
import ui.spiel.brett.felder.FeldMouseAdapter;
import ui.spiel.brett.felder.Figur2d;
import ui.spiel.brett.felder.FigurenManager;
import ui.spiel.brett.felder.NormalesFeld2d;
import ui.spiel.brett.felder.SpielerFeld2d;
import ui.spiel.brett.felder.Feld2d.Feld2dKonfiguration;
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

		SpielerFarbe farbeIch = spiel.spielerIch.getFarbe();

		for (Feld feld : spiel.getBrett().getAlleFelder()) {
			Feld2d feld2d;
			Point position = brettXML.getFelder().get(feld.getNummer());


			Feld2dKonfiguration konfig = new Feld2dKonfiguration(position,
			                                                     feld,
			                                                     mouseAdapter,
			                                                     hover,
			                                                     farbeIch,
			                                                     figurenManager);

			if (feld.istNormal()) {
				feld2d = new NormalesFeld2d(konfig);
			} else {
				SpielerFeld f = (SpielerFeld) feld.getFeld();
				Icon icon = Icons.getSpielerFeldIcon(f.getSpieler().getFarbe());

				feld2d = new SpielerFeld2d(icon, konfig);
			}

			this.setComponentZOrder(feld2d, this.getComponentCount());
		}


		// Views für Spieler
		erstelleSpielerViews(spiel.getSpieler());

		erstelleHinweis();

		BrettMouseAdapter brettAdapter = new BrettMouseAdapter(steuerung);
		Brett2d brett2d = new Brett2d(brettAdapter);
		setPreferredSize(brett2d.getPreferredSize());
		setMinimumSize(brett2d.getMinimumSize());
		setMaximumSize(brett2d.getMaximumSize());

		add(brett2d);

		spiel.addObserver(this);
	}

	private void erstelleSpielerViews(List<Spieler> spielers) {
		for (int i = 0; i < spielers.size(); ++i) {
			Spieler spieler = spielers.get(i);

			// SpielerView
			JPanel spielerView = new SpielerView(spieler);

			JPanel spielerViewPanel = new JPanel();
			spielerViewPanel.setOpaque(false);
			spielerViewPanel.setLayout(new GridBagLayout());

			// Spezielles Verfahren, um ein JPanel zu zentrieren
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.CENTER;
			spielerViewPanel.add(spielerView, gbc);

			spielerViewPanel.setLocation(brettXML.getSpielerViews().get(i));
			spielerViewPanel.setSize(170, 30);

			add(spielerViewPanel);

			// SpielerZustandsView
			Point zustandPos = brettXML.getSpielerZustandsViews().get(i);
			JPanel spielerZustandsView = new SpielerZustandView(spieler, zustandPos);
			add(spielerZustandsView);
		}
	}

	private void erstelleHinweis() {
		Point pos = brettXML.getHinweis();

		hinweisLabel = new JLabel();
		hinweisLabel.setFont(hinweisLabel.getFont().deriveFont(1));
		hinweisLabel.setHorizontalAlignment(SwingConstants.CENTER);

		int w = 210;
		int h = 40;
		hinweisLabel.setSize(w, h);
		hinweisLabel.setLocation(pos.x - (w / 2), pos.y - (h / 2));
		add(hinweisLabel);

		BLabel hinweisVertiefung = new BLabel(Icons.HINWEIS);
		hinweisVertiefung.zentriereAuf(pos);
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
