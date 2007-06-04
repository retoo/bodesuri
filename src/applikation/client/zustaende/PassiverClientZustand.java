package applikation.client.zustaende;

import ui.ClientController;
import applikation.client.BodesuriClient;
import dienste.automat.Automat;
import dienste.automat.zustaende.PassiverZustand;

/**
 * Spezifischer passiver Client-Zustand
 */
public abstract class PassiverClientZustand extends PassiverZustand {
	protected BodesuriClient automat;
	protected ClientController controller;

	/* TODO:
	 * public PassiverClientZustand(Automat automat, ClientController controller) {
		this.automat = (BodesuriClient) automat;
		this.controller = ClientController;
	}*/
	
	public void setAutomat(Automat automat) {
		this.automat = (BodesuriClient) automat;
	}
}
