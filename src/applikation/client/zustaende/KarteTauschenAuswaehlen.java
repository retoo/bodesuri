package applikation.client.zustaende;

import dienste.automat.zustaende.Zustand;

public class KarteTauschenAuswaehlen extends PassiverClientZustand {
    public Zustand handle() {
	    return automat.getZustand(KarteTauschenBekommen.class);
    }
}
