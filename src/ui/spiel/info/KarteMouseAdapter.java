package ui.spiel.info;

import java.awt.event.MouseAdapter;

import pd.karten.Karte;
import applikation.client.BodesuriClient;
import applikation.client.zugautomat.zustaende.EndeWaehlen;
import applikation.client.zugautomat.zustaende.KarteWaehlen;
import applikation.client.zugautomat.zustaende.StartWaehlen;
import applikation.events.KarteGewaehltEvent;

class KarteMouseAdapter extends MouseAdapter {
	private BodesuriClient automat;

	public KarteMouseAdapter(BodesuriClient automat) {
		this.automat = automat;
	}

	public void mouseClicked(java.awt.event.MouseEvent evt) {
		if (automat.zugAutomat != null) {
			if (automat.zugAutomat.isZustand(KarteWaehlen.class)
			    || automat.zugAutomat.isZustand(StartWaehlen.class)
			    || automat.zugAutomat.isZustand(EndeWaehlen.class)) {
				Karte karte = ((KarteView) evt.getComponent()).karte;
				KarteGewaehltEvent kge = new KarteGewaehltEvent(karte);
				automat.zugAutomat.queue.enqueue(kge);
			}
		}
	}
}
