package applikation.server.zustaende;

import applikation.nachrichten.KartenTausch;
import applikation.server.pd.Spieler;
import applikation.server.pd.Spiel;
import dienste.automat.zustaende.Zustand;
import dienste.netzwerk.EndPunktInterface;

/* Wird bald aktiv */
public class KartenTauschen extends ServerZustand {
	Class<? extends Zustand> kartenTausch(EndPunktInterface absender, KartenTausch tausch) {
		Spiel spielers = spiel;

		Spieler spieler = spielers.getSpieler(absender);

		if (spieler.hatGetauscht)
			throw new RuntimeException("Spieler " + spieler + "versucht ein zweites mal zu tauschen");

		/* Transferiere Karte zum Partner */
		spieler.partner.getauschteKarte(tausch);

		spieler.hatGetauscht = true;

		if (spielers.runde.isFertigGetauscht()) {
			for (Spieler s : spielers.runde.spielers) {
				s.sendeTauschKarte();
			}

			return StarteZug.class;
		}

		return KartenTauschen.class;
	}
}
