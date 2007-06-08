package applikation.client.zustaende;

import dienste.automat.PassiverZustand;
import dienste.automat.zustaende.Zustand;

/**
 * Wenn ein schwerer Fehler auftritt.
 */
public class SchwererFehler extends ClientZustand implements PassiverZustand {
    public Class<? extends Zustand> handle() {
		throw new RuntimeException("Schwerer Fehler");
    }
}
