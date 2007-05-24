package applikation.client.zustaende;

import dienste.automat.Zustand;

public class SchwererFehler extends PassiverClientZustand {
	@Override
    protected Zustand execute() {
		throw new RuntimeException("Schwerer Fehler");
    }
}
