package applikation.client.zustaende;

import dienste.automat.zustaende.Zustand;

public class KarteTauschenBekommen extends PassiverClientZustand {
	public Zustand handle() {
	    return automat.getZustand(NichtAmZug.class);
    }
}
