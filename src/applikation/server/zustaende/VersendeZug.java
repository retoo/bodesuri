package applikation.server.zustaende;

import dienste.automat.zustaende.Zustand;

public class VersendeZug extends PassiverServerZustand {

	@Override
	public Zustand handle() {
		/* Weiss nicht ob der zustand sinn macht.. evtl. halt doch im vorherigen Zustand
		 * tun
		 */

		/* Versende Zug */

		return automat.getZustand(ZugAbschluss.class);
	}
}
