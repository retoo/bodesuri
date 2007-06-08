package ui.spiel.karten;

import java.awt.event.MouseAdapter;

import ui.GUIController;
import applikation.client.Controller;

public class KarteMouseAdapter extends MouseAdapter {
	private Controller controller;

	private Boolean aktiv;

	private DeckView deckView;
	private KartenAuswahl kartenAuswahl;

	public KarteMouseAdapter(GUIController controller, DeckView view,
	        KartenAuswahl kartenAuswahl) {
		this.controller = controller;
		this.deckView = view;
		this.kartenAuswahl = kartenAuswahl;

		controller.registriereKarteMouseAdapter(this);
		aktiv = false;
	}

	public void mouseClicked(java.awt.event.MouseEvent evt) {
		if (aktiv) {
			controller.karteGewaehlt(((KarteView) evt.getComponent()).karte);
			
			kartenAuswahl.setPosition(((KarteView) evt.getComponent()).getKoordinaten());
			deckView.add(kartenAuswahl);
			deckView.updateUI();
		}
	}

	public void aktiv(Boolean aktiv) {
		this.aktiv = aktiv;
	}
}
