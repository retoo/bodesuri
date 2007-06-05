package ui.spiel.karten;

import java.awt.event.MouseAdapter;

import applikation.client.Controller;

class KarteMouseAdapter extends MouseAdapter {
	private Controller controller;

	private DeckView deckView;
	private KartenAuswahl kartenAuswahl;

	public KarteMouseAdapter(Controller controller, DeckView view,
	        KartenAuswahl kartenAuswahl) {
		this.controller = controller;
		this.deckView = view;
		this.kartenAuswahl = kartenAuswahl;
	}

	public void mouseClicked(java.awt.event.MouseEvent evt) {
		controller.karteGewaehlt(((KarteView) evt.getComponent()).karte);
		
		kartenAuswahl.setPosition(((KarteView) evt.getComponent()).getKoordinaten());
		deckView.add(kartenAuswahl);
		deckView.updateUI();
	}
}
