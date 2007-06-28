package applikation.client.zustaende;

import dienste.automat.zustaende.PassiverZustand;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand in welchem das Spiel gestartet wird. Geht direkt in
 * {@link NichtAmZug} über.
 */
public class SpielStart extends ClientZustand implements PassiverZustand {
	public Class<? extends Zustand> handle() {
		controller.zeigeSpiel(spiel);
		return NichtAmZug.class;
	}
}
