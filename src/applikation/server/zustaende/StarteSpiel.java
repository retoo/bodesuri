package applikation.server.zustaende;

import applikation.nachrichten.SpielStartNachricht;
import applikation.server.Spielerschaft;
import dienste.automat.zustaende.PassiverZustand;
import dienste.automat.zustaende.Zustand;

/**
 * Passiver Zustand der das Spiel initialisiert.
 */
public class StarteSpiel extends ServerZustand implements PassiverZustand {
	public Class<? extends Zustand> handle() {
		Spielerschaft spielerschaft = spielDaten.spielerschaft;

		SpielStartNachricht ssn = new SpielStartNachricht(spielerschaft.getStringArray());

		spielerschaft.broadcast(ssn);

		return StartRunde.class;
	}
}
