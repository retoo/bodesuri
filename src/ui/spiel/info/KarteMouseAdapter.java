package ui.spiel.info;

import java.awt.event.MouseAdapter;

import applikation.client.ClientController;

class KarteMouseAdapter extends MouseAdapter {
	private ClientController controller;

	private DeckView deckView;
	private KartenAuswahl kartenAuswahl;

	public KarteMouseAdapter(ClientController controller, DeckView view, KartenAuswahl kartenAuswahl) {
		this.controller = controller;
		this.deckView = view;
		this.kartenAuswahl = kartenAuswahl;
	}

	public void mouseClicked(java.awt.event.MouseEvent evt) {
		if (controller.isZugAutomatControllerVorhanden()) {
			controller.klickKarte( ((KarteView) evt.getComponent()).karte );
			kartenAuswahl.setPosition(((KarteView) evt.getComponent()).getKoordinaten());
			
			deckView.add(kartenAuswahl);
			deckView.updateUI();
		}
	}
}
