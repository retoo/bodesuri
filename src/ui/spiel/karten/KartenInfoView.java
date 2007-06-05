package ui.spiel.karten;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import applikation.client.Controller;

public class KartenInfoView extends JPanel{
	public KartenInfoView(Controller controller) {
		// Layout setzen
		setLayout(new BorderLayout());
		
		// Views
		KarteGezogenView karteGezogenView = new KarteGezogenView(controller);
		DeckView deckView = new DeckView(controller);
		SteuerungsView steuerungsView = new SteuerungsView(controller);
		
		// Layout zusammenstellen
		add(karteGezogenView, BorderLayout.NORTH);
		add(deckView, BorderLayout.CENTER);
		add(steuerungsView, BorderLayout.SOUTH);
	}
}
