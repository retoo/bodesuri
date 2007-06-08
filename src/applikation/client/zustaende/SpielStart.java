package applikation.client.zustaende;

import ui.spiel.BodesuriView;
import dienste.automat.PassiverZustand;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand in welchem das Spiel ({@link BodesuriView}) gestartet wird. Geht
 * direkt in {@link NichtAmZug} über.
 */
public class SpielStart extends ClientZustand implements PassiverZustand {
	public Class<? extends Zustand> handle() {
		controller.zeigeSpiel();
		return NichtAmZug.class;
	}
}
