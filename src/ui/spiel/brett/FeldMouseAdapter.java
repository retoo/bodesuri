package ui.spiel.brett;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import pd.brett.Feld;
import applikation.client.BodesuriClient;
import applikation.client.zugautomat.zustaende.EndeWaehlen;
import applikation.client.zugautomat.zustaende.StartWaehlen;
import applikation.events.FeldGewaehltEvent;

/**
 * MouseEventListener, der auf die Klicks der Felder achtet.
 */
class FeldMouseAdapter extends MouseAdapter {
	BodesuriClient automat;

	public FeldMouseAdapter(BodesuriClient automat) {
		this.automat = automat;
	}

	/**
	 * Anhand der Instanzvariablen wird entschieden, ob gerade der Anfangspunkt
	 * oder der Endpunkt angeklickt wurde.
	 * 
	 * @param e
	 *            MouseEvent enthält die angeklickte Komponente
	 */
	public void mouseClicked(MouseEvent e) {
		if (automat.zugAutomat.isZustand(StartWaehlen.class)
		    || automat.zugAutomat.isZustand(EndeWaehlen.class)) {
			Feld feld = ((Feld2d) e.getComponent()).feld;
			automat.zugAutomat.queue.enqueue(new FeldGewaehltEvent(feld));
		}
	}
}
