package ui.spiel.karten;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import applikation.client.controller.Steuerung;
import applikation.client.pd.Spiel;

public class SteuerungsView extends JPanel {
	public SteuerungsView(Steuerung steuerung, Spiel spiel) {
		setLayout(new GridBagLayout());
		setOpaque(false);

		// Views
		DeckView deckView = new DeckView(steuerung, spiel.spielerIch.getKarten());
		KarteGewaehltView karteGewaehltView = new KarteGewaehltView(steuerung, spiel.spielerIch.getKarten());
		SteuerungsButtonView steuerungsButtonView = new SteuerungsButtonView(steuerung, spiel);

		// Layout zusammenstellen
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = 1;
		c.gridx = 0;
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.HORIZONTAL;

		c.weighty = 0.0;
		add(deckView, c);

		c.weighty = 0.0;
		add(karteGewaehltView, c);

		c.weighty = 1.0;
		add(steuerungsButtonView, c);
	}
}
