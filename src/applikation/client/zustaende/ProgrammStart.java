package applikation.client.zustaende;

import ui.verbinden.VerbindenView;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand in welcher dem Automat beim Start ist. Öffnet den
 * {@link VerbindenView} und geht direkt in {@link Lobby} über.
 */
public class ProgrammStart extends PassiverClientZustand {
	public Zustand handle() {
		automat.verbindenView = new VerbindenView(automat.queue);
		automat.verbindenView.setVisible(true);
		return automat.getZustand(VerbindungErfassen.class);
	}
}
