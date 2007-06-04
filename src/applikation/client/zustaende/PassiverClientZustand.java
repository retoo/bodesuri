package applikation.client.zustaende;

import applikation.client.BodesuriClient;
import applikation.client.Controller;
import dienste.automat.Automat;
import dienste.automat.zustaende.PassiverZustand;

/**
 * Spezifischer passiver Client-Zustand
 */
public abstract class PassiverClientZustand extends PassiverZustand {
	protected BodesuriClient automat;
	protected Controller controller;

	/* TODO:
	 * public PassiverClientZustand(Automat automat, ClientController controller) {
		this.automat = (BodesuriClient) automat;
		this.controller = ClientController;
	}*/
	
	public void setAutomat(Automat automat) {
		this.automat = (BodesuriClient) automat;
	}
}
