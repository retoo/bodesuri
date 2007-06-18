package ui.spiel.karten;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import applikation.client.controller.Steuerung;
import applikation.client.pd.Spiel;

public class KartenAuswahlView extends JPanel {
	private KarteGewaehltView karteGewaehltView;
	private DeckView deckView;

	public KartenAuswahlView(Steuerung steuerung, Spiel spiel) {
		setOpaque(false);
		setLayout(new BorderLayout());

		// Views
		deckView = new DeckView(steuerung, this, spiel.spielerIch);
		karteGewaehltView = new KarteGewaehltView(steuerung);
		SteuerungsView steuerungsView = new SteuerungsView(steuerung, spiel);

		// Layout zusammenstellen
		add(deckView, BorderLayout.NORTH);
		add(karteGewaehltView, BorderLayout.CENTER);
		add(steuerungsView, BorderLayout.SOUTH);
	}

	public DeckView getDeckView() {
    	return deckView;
    }

	public KarteGewaehltView getKarteGewaehltView() {
    	return karteGewaehltView;
    }
}
