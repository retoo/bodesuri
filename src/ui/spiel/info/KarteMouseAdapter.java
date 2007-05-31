package ui.spiel.info;

import java.awt.event.MouseAdapter;

import applikation.client.BodesuriClient;
import applikation.client.zugAutomatAlt.zustaende.KarteWaehlen;
import applikation.events.KarteGewaehltEvent;

class KarteMouseAdapter extends MouseAdapter {
	private BodesuriClient automat;
	
	public KarteMouseAdapter(BodesuriClient automat) {
	    this.automat = automat;
    }

	public void mouseClicked(java.awt.event.MouseEvent evt) {
		if (automat.zugAutomat != null
		    && automat.zugAutomat.isZustand(KarteWaehlen.class)) {
			automat.zugAutomat.queue.enqueue(
			        new KarteGewaehltEvent(((KarteView) evt.getComponent()).karte));
		}
	}
}
