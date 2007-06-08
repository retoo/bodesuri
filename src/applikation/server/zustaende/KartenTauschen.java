package applikation.server.zustaende;

import applikation.nachrichten.KartenTausch;
import applikation.server.Spieler;
import applikation.server.Spielerschaft;
import dienste.automat.zustaende.Zustand;
import dienste.netzwerk.EndPunkt;

/* Wird bald aktiv */
public class KartenTauschen extends ServerZustand {
	Class<? extends Zustand> kartenTausch(EndPunkt absender, KartenTausch tausch) {
		Spielerschaft spielers = spielDaten.spielerschaft;

		Spieler spieler = spielers.getSpieler(absender);

		if (spieler.hatGetauscht)
			throw new RuntimeException("Spieler " + spieler + "versucht ein zweites mal zu tauschen");

		/* Transferiere Karte zum Partner */
		spieler.partner.sendeKartenTausch(tausch);

		spieler.hatGetauscht = true;


		if (spielers.runde.isFertigGetauscht()) {
			return StarteZug.class;
		}

		return KartenTauschen.class;
	}
}
