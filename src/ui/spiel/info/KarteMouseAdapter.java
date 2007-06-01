package ui.spiel.info;

import java.awt.event.MouseAdapter;

import pd.karten.Karte;
import applikation.client.ClientController;
import applikation.client.zugautomat.zustaende.EndeWaehlen;
import applikation.client.zugautomat.zustaende.KarteWaehlen;
import applikation.client.zugautomat.zustaende.StartWaehlen;
import applikation.events.KarteGewaehltEvent;

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
		if (controller.zugAutomat != null) {
			if (controller.zugAutomat.isZustand(KarteWaehlen.class)
					|| controller.zugAutomat.isZustand(StartWaehlen.class)
					|| controller.zugAutomat.isZustand(EndeWaehlen.class)) {
				Karte karte = ((KarteView) evt.getComponent()).karte;
				KarteGewaehltEvent kge = new KarteGewaehltEvent(karte);
				controller.zugAutomat.queue.enqueue(kge);

				kartenAuswahl.setPosition(((KarteView) evt.getComponent()).getKoordinaten());
				
				deckView.add(kartenAuswahl);
				deckView.updateUI();
			}
		}
	}
}
