package applikation.client.zustaende;

import dienste.automat.Zustand;

/**
 * Bis auf weiteres ein passiver Zustand, da hier nichts getan werden muss.
 */
public class AmZug extends PassiverClientZustand {
	@Override
	protected Zustand execute() {
		return automat.getZustand(KarteWaehlen.class);
	}

}
