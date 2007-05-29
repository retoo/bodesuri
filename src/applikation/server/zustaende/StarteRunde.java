package applikation.server.zustaende;

import dienste.automat.zustaende.Zustand;

public class StarteRunde extends PassiverServerZustand {

	@Override
	public Zustand handle() {
		/* TODO: Karten austeilen */

		return automat.getZustand(KartenTauschen.class);
	}
}
