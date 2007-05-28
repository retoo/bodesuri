package applikation.client.zustaende;

import dienste.automat.zustaende.Zustand;

/**
 * Zustand in welchem der Spieler dran kommt. Bis auf weiteres ein passiver
 * Zustand, da hier nichts getan werden muss. Geht direkt in {@link KarteWaehlen} über.
 */
public class AmZug extends PassiverClientZustand {
	public Zustand handle() {
		return automat.getZustand(KarteWaehlen.class);
	}

}
