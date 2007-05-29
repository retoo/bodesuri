package applikation.server.zustaende;

import applikation.nachrichten.KartenTausch;
import dienste.automat.zustaende.Zustand;
import dienste.netzwerk.EndPunkt;

/* Wird bald aktiv */
public class KartenTauschen extends PassiverServerZustand {
	Zustand kartenTausch(EndPunkt absender, KartenTausch tausch) {
		boolean alleKartenGetauscht = true;

		if (alleKartenGetauscht) {
			return automat.getZustand(StarteZug.class);
		}

		return this;
	}

	@Override
    public Zustand handle() {
	    // TODO wird verschwinden
	    return automat.getZustand(StarteZug.class);
    }
}
