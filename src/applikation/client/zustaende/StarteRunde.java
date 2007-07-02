package applikation.client.zustaende;

import applikation.client.pd.Spieler;
import dienste.automat.zustaende.PassiverZustand;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand wenn eine neue Runde gestartet wird. Alle Eigenschaften der Spieler
 * werden zurückgesetzt (am Zug, hat Aufgegeben). Der Automat geht nach
 * {@link KarteTauschenAuswaehlen} über.
 */
public class StarteRunde extends ClientZustand implements PassiverZustand {
	public Class<? extends Zustand> handle() {
		for (Spieler spieler : spiel.getSpieler()) {
			spieler.setAmZug(false);
			spieler.setHatAufgebeben(false);
		}

		return KarteTauschenAuswaehlen.class;
	}
}
