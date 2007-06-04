package applikation.client.zustaende;

import ui.ClientController;
import ui.spiel.BodesuriView;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand in welchem das Spiel ({@link BodesuriView}) gestartet wird. Geht
 * direkt in {@link NichtAmZug} über.
 */
public class SpielStart extends PassiverClientZustand {
	public SpielStart(ClientController controller) {
		/* TODO: 
		 * super(automat, controller);
		 */
		this.controller = controller;
	}
	
	public Zustand handle() {
		controller.zeigeSpielStart();
		return automat.getZustand(NichtAmZug.class);
	}
}
