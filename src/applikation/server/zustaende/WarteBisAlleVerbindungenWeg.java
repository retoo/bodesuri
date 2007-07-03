package applikation.server.zustaende;

import applikation.server.ServerAutomat;
import dienste.automat.zustaende.EndZustand;
import dienste.automat.zustaende.Zustand;
import dienste.netzwerk.EndPunktInterface;

/**
 * Der Server wartet bis alle verbleibenden Verbindungen beendet sind. Sobald
 * dies der Fall ist wird der {@link ServerAutomat} in den {@link EndZustand}
 * überführt und beendet sich selber.
 */
public class WarteBisAlleVerbindungenWeg extends ServerZustand {
	Class<? extends Zustand> verbindungGeschlossen(EndPunktInterface absender) {
		spiel.entferne(absender);

		if (spiel.getAnzahlSpieler() <= 0)
			return EndZustand.class;
		else
			return this.getClass();
	}
}