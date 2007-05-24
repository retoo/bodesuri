package ui.brett;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import applikation.client.BodesuriClient;

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
		if (automat.zugentgegennahme != null) {
			automat.zugentgegennahme.ziehen(((Feld2d) e.getComponent()).feld);
		}
	}
}
