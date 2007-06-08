package applikation.client.zustaende;

import ui.verbinden.VerbindenView;
import dienste.automat.PassiverZustand;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand in welcher dem Automat beim Start ist. Öffnet den
 * {@link VerbindenView} und geht direkt in den Zustand {@link Lobby} über.
 */
public class ProgrammStart extends ClientZustand implements PassiverZustand {
	public Class<? extends Zustand> handle() {
		controller.zeigeVerbinden();
		return VerbindungErfassen.class;
	}
}
