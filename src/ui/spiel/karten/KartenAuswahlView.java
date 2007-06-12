package ui.spiel.karten;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import ui.GUIController;

public class KartenAuswahlView extends JPanel {
	private KarteGewaehltView karteGewaehltView;
	private DeckView deckView;
	
	public KartenAuswahlView(GUIController controller) {
		setOpaque(false);
		setLayout(new BorderLayout());
		
		// Views
		deckView = new DeckView(controller, this);
		karteGewaehltView = new KarteGewaehltView(controller);
		SteuerungsView steuerungsView = new SteuerungsView(controller);
		
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
