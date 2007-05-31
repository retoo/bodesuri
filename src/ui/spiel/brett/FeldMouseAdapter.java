package ui.spiel.brett;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import applikation.client.ClientController;

/**
 * MouseEventListener, der auf die Klicks der Felder achtet.
 */
class FeldMouseAdapter extends MouseAdapter {
	ClientController controller;

	public FeldMouseAdapter(ClientController controller) {
		this.controller = controller;
	}

	/**
	 * Anhand der Instanzvariablen wird entschieden, ob gerade der Anfangspunkt
	 * oder der Endpunkt angeklickt wurde.
	 * 
	 * @param e
	 *            MouseEvent enthält die angeklickte Komponente
	 */
	public void mouseClicked(MouseEvent e) {
		if (controller.isZugAutomatControllerVorhanden()) {
			controller.klickFeld( ((Feld2d) e.getComponent()).feld );
		}
	}
}
