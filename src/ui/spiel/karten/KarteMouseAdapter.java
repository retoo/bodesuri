package ui.spiel.karten;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import ui.GUIController;
import applikation.client.controller.Controller;

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

	public void mouseClicked(MouseEvent evt) {
		if (aktiv) {
			KarteView karteView = (KarteView) evt.getComponent();
			kartenAuswahl.setPosition(karteView.getPosition());
			deckView.add(kartenAuswahl);
			deckView.updateUI();
			controller.karteGewaehlt(karteView.getKarte());
		}
	}

	public void aktiv(Boolean aktiv) {
		this.aktiv = aktiv;
		if (!aktiv) {
			deckView.remove(kartenAuswahl);
			deckView.updateUI();
		}
	}
}
