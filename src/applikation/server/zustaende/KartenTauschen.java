package applikation.server.zustaende;

import pd.regelsystem.karten.Karte;
import applikation.nachrichten.KartenTausch;
import applikation.server.pd.Runde;
import applikation.server.pd.Spieler;
import dienste.automat.zustaende.Zustand;
import dienste.netzwerk.EndPunktInterface;

/**
 * Alle {@link Spieler} müssen mit ihrem Partner eine {@link Karte} tauschen. Der Server wartet
 * bis alle Spieler die zu tauschende Karte mit der Nachricht {@link KartenTausch}
 * gemeldet haben. Anschliessend wird die getauschte Karte mit derselben
 * Nachricht dem Partner gemeldet und es wird in den Zustand {@link StarteZug}
 * gewechselt.
 *
 */
public class KartenTauschen extends ServerZustand {
	Class<? extends Zustand> kartenTausch(EndPunktInterface absender, KartenTausch tausch) {
		Spieler spieler = spiel.getSpieler(absender);

		if (spieler == null) {
			throw new RuntimeException("Unbekannter Spieler, kann Spieler nicht anhand des Endpunktes "
			                                   + absender + " auflösen");
		}

		Runde runde = spiel.runde;

		runde.tausche(spieler, tausch);

		if (runde.isFertigGetauscht()) {
			runde.tauscheAbschluss();
			return StarteZug.class;
		}

		return KartenTauschen.class;
	}
}
