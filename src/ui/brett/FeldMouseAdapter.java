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
		// TODO: Es wäre eigentlich besser zu überprüfen ob der Automat im
		// Zustand Ziehen ist. Der Automat will seinen Zustand aber nicht
		// verraten.
		if (automat != null) {
			automat.zugentgegennahme.ziehen(((Feld2d) e.getComponent()).feld);
		}
	}
}
