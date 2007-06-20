package ui.spiel.brett;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import ui.spiel.karten.KartenAuswahlView;
import applikation.client.controller.Steuerung;
import applikation.client.pd.Spiel;

public class SpielView extends JPanel {
	public SpielView(Steuerung steuerung, Spiel spiel) {
		// Layout setzen
		setLayout(new GridBagLayout());
		setOpaque(false);

		// Views
		BrettView brettView = new BrettView(steuerung, spiel);
		KartenAuswahlView kartenAuswahlView = new KartenAuswahlView(steuerung, spiel);

		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.BOTH;
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		add(brettView, c);
		add(kartenAuswahlView, c);
	}
}
