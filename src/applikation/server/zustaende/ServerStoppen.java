package applikation.server.zustaende;

import dienste.automat.zustaende.EndZustand;
import dienste.automat.zustaende.PassiverZustand;
import dienste.automat.zustaende.Zustand;

public class ServerStoppen extends ServerZustand implements PassiverZustand {
	public Class<? extends Zustand> handle() {
		spiel.server.ausschalten();

		if (spiel.getAnzahlSpieler() == 0) {
			return EndZustand.class;
		} else {
			return WarteBisAlleVerbindungenWeg.class;
		}
	}
}
