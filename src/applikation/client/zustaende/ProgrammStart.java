package applikation.client.zustaende;

import applikation.client.Controller;
import ui.verbinden.VerbindenView;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand in welcher dem Automat beim Start ist. Öffnet den
 * {@link VerbindenView} und geht direkt in den Zustand {@link Lobby} über.
 */
public class ProgrammStart extends PassiverClientZustand {
	public ProgrammStart(Controller controller) {
		/* TODO: 
		 * super(automat, controller);
		 */
		this.controller = controller;
	}
	
	public Zustand handle() {
		controller.zeigeProgrammStart();
		return automat.getZustand(VerbindungErfassen.class);
	}
}
