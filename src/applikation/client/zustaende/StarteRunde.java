package applikation.client.zustaende;

import applikation.server.zustaende.KartenTauschen;
import dienste.automat.zustaende.Zustand;

public class StarteRunde extends PassiverClientZustand {
	public Zustand handle() {
		return automat.getZustand(KartenTauschen.class);
	}
}
