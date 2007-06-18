package applikation.client.zustaende;

import dienste.automat.zustaende.PassiverZustand;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand in welcher dem Automat beim Start ist. Öffnet den
 * VerbindenView und geht direkt in den Zustand {@link Lobby} über.
 */
public class ProgrammStart extends ClientZustand implements PassiverZustand {
	public Class<? extends Zustand> handle() {
		return VerbindungErfassen.class;
	}
}
