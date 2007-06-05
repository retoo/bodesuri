package applikation.client.zustaende;

import applikation.client.Controller;
import ui.spiel.BodesuriView;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand in welchem das Spiel ({@link BodesuriView}) gestartet wird. Geht
 * direkt in {@link NichtAmZug} über.
 */
public class SpielStart extends PassiverClientZustand {
	public SpielStart(Controller controller) {
		this.controller = controller;
	}
	
	public Zustand handle() {
		controller.zeigeSpiel();
		return automat.getZustand(NichtAmZug.class);
	}
}
