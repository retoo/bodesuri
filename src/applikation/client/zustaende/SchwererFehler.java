package applikation.client.zustaende;

import dienste.automat.zustaende.Zustand;

public class SchwererFehler extends PassiverClientZustand {
	@Override
    public Zustand handle() {
		throw new RuntimeException("Schwerer Fehler");
    }
}
