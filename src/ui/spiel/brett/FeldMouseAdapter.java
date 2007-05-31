package ui.spiel.brett;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import applikation.client.BodesuriClient;
import applikation.client.zugAutomatAlt.zustaende.Ziehen;

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
		if (automat.zugAutomat.isZustand(Ziehen.class)) {
			automat.zugAutomat.zugentgegennahme.ziehen(((Feld2d) e.getComponent()).feld);
		}
	}
}
