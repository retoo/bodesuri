package applikation.client.zustaende;

import dienste.automat.zustaende.Zustand;

public class StarteRunde extends PassiverClientZustand {
	public Zustand handle() {
		return automat.getZustand(KarteTauschenAuswaehlen.class);
	}
}
