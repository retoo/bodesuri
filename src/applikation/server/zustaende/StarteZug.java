package applikation.server.zustaende;

import dienste.automat.zustaende.Zustand;

public class StarteZug extends PassiverServerZustand {

	@Override
	public Zustand handle() {
		/* Aktuellen Spieler bestimmen
		 * und diesem mitteilen dass er einen Zug fahren soll
		 */

		return automat.getZustand(WarteAufZug.class);
	}
}
