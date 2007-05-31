package ui.spiel.info;

import java.awt.event.MouseAdapter;

import applikation.client.ClientController;

class KarteMouseAdapter extends MouseAdapter {
	private ClientController controller;

	public KarteMouseAdapter(ClientController controller) {
		this.controller = controller;
	}

	public void mouseClicked(java.awt.event.MouseEvent evt) {
		if (controller.isZugAutomatControllerVorhanden()) {
			controller.klickKarte( ((KarteView) evt.getComponent()).karte );
		}
	}
}
