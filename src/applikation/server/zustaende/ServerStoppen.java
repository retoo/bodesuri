package applikation.server.zustaende;

import applikation.server.ServerAutomat;
import applikation.server.pd.Spieler;

import dienste.automat.zustaende.EndZustand;
import dienste.automat.zustaende.PassiverZustand;
import dienste.automat.zustaende.Zustand;
import dienste.netzwerk.server.Server;

/**
 * Es wird geprüft ob noch irgendwelche {@link Spieler} mit dem {@link Server} verbunden sind.
 * Ist dies der Fall wird in den Zustand {@link WarteBisAlleVerbindungenWeg} gewechselt.
 * Ansonsten wird der {@link ServerAutomat} in den {@link EndZustand} überführt und beendet sich
 * selber.
 */
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