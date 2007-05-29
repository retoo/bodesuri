package applikation.server.zustaende;

import dienste.automat.zustaende.EndZustand;
import dienste.automat.zustaende.Zustand;

public class ServerEnde extends PassiverServerZustand {

	@Override
	public Zustand handle() {
		/* Alles stoppen */
		return automat.getZustand(EndZustand.class);
	}

}
