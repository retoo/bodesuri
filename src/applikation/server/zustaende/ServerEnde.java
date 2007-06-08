package applikation.server.zustaende;

import dienste.automat.PassiverZustand;
import dienste.automat.zustaende.EndZustand;
import dienste.automat.zustaende.Zustand;

public class ServerEnde extends ServerZustand implements PassiverZustand {
	public Class<? extends Zustand> handle() {
		/* Alles stoppen */
		return EndZustand.class;
	}
}
