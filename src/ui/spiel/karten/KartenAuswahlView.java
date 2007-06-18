package ui.spiel.karten;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import applikation.client.controller.Steuerung;
import applikation.client.pd.Spiel;

public class KartenAuswahlView extends JPanel {
	private KarteGewaehltView karteGewaehltView;
	private DeckView deckView;

	public KartenAuswahlView(Steuerung steuerung, Spiel spiel) {
		setLayout(new GridBagLayout());
		setOpaque(false);

		// Views
		deckView = new DeckView(steuerung, this, spiel.spielerIch);
		karteGewaehltView = new KarteGewaehltView(steuerung, spiel.spielerIch.getKarten());
		SteuerungsView steuerungsView = new SteuerungsView(steuerung, spiel);

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
		add(steuerungsView, c);
	}
}
