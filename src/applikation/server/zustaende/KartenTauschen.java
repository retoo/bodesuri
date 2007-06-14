package applikation.server.zustaende;

import applikation.nachrichten.KartenTausch;
import applikation.server.pd.Runde;
import applikation.server.pd.Spieler;
import dienste.automat.zustaende.Zustand;
import dienste.netzwerk.EndPunktInterface;

/* Wird bald aktiv */
public class KartenTauschen extends ServerZustand {
	Class<? extends Zustand> kartenTausch(EndPunktInterface absender, KartenTausch tausch) {
		Spieler spieler = spiel.getSpieler(absender);
		Runde runde = spiel.runde;

		runde.tausche(spieler, tausch);

		if (runde.isFertigGetauscht()) {
			runde.tauscheKarten();
			return StarteZug.class;
		}

		return KartenTauschen.class;
	}
}
