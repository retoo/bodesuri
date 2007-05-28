package applikation.client.zustaende;

import dienste.automat.zustaende.Zustand;

/**
 * Bis auf weiteres ein passiver Zustand, da hier nichts getan werden muss.
 */
public class AmZug extends PassiverClientZustand {
    public Zustand handle() {
		return automat.getZustand(KarteWaehlen.class);
	}

}
