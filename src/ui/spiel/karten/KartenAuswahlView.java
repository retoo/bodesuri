package ui.spiel.karten;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import ui.GUIController;

public class KartenAuswahlView extends JPanel{
	public KartenAuswahlView(GUIController controller) {
		// Layout setzen
		setLayout(new BorderLayout());
		
		// Views
		KarteGewaehltView karteGewaehltView = new KarteGewaehltView(controller);
		DeckView deckView = new DeckView(controller);
		SteuerungsView steuerungsView = new SteuerungsView(controller);
		
		// Layout zusammenstellen
		add(karteGewaehltView, BorderLayout.NORTH);
		add(deckView, BorderLayout.CENTER);
		add(steuerungsView, BorderLayout.SOUTH);
	}
}
