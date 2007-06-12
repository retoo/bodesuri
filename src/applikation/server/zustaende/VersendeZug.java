package applikation.server.zustaende;

import dienste.automat.zustaende.PassiverZustand;
import dienste.automat.zustaende.Zustand;

public class VersendeZug extends ServerZustand implements PassiverZustand {
	public Class<? extends Zustand> handle() {
		/* Weiss nicht ob der zustand sinn macht.. evtl. halt doch im vorherigen Zustand
		 * tun
		 */

		/* Versende Zug */

		return ZugAbschluss.class;
	}
}
