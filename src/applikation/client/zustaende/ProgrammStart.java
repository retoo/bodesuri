package applikation.client.zustaende;

import dienste.automat.zustaende.PassiverZustand;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand in welcher dem Automat beim Start ist. Geht direkt in den Zustand
 * {@link VerbindungErfassen} über.
 */
// TODO: Philippe: Diesen könnten wir nun auch löschen. Der Automat funktioniert
// aber nicht wenn der 1. Zustand aktiv ist.
public class ProgrammStart extends ClientZustand implements PassiverZustand {
	public Class<? extends Zustand> handle() {
		return VerbindungErfassen.class;
	}
}
