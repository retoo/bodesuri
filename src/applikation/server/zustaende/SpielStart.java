package applikation.server.zustaende;

import applikation.nachrichten.SpielStartNachricht;
import applikation.server.pd.Spiel;
import dienste.automat.zustaende.PassiverZustand;
import dienste.automat.zustaende.Zustand;

/**
 * Den Spielern wird der Spielstart mit der Nachricht {@link SpielStartNachricht}
 * angekündet. Die Nachricht {@link SpielStartNachricht} enthält die Namen aller
 * Spieler und die gebildeten Partnerschaften.
 *
 * Geht direkt in den Zustand {@link StartRunde} über.
 */
public class SpielStart extends ServerZustand implements PassiverZustand {
	public Class<? extends Zustand> handle() {
		Spiel spielerschaft = spiel;

		SpielStartNachricht ssn = new SpielStartNachricht(spielerschaft.getSpielInfo(), spielerschaft.getPartnerschaften());
		spielerschaft.broadcast(ssn);

		return StartRunde.class;
	}
}
