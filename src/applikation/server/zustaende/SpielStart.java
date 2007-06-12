package applikation.server.zustaende;

import applikation.nachrichten.SpielStartNachricht;
import applikation.server.pd.Spielerschaft;
import dienste.automat.zustaende.PassiverZustand;
import dienste.automat.zustaende.Zustand;

/**
 * Passiver Zustand der das Spiel initialisiert.
 */
public class SpielStart extends ServerZustand implements PassiverZustand {
	public Class<? extends Zustand> handle() {
		Spielerschaft spielerschaft = spielDaten.spielerschaft;

		SpielStartNachricht ssn = new SpielStartNachricht(spielerschaft.getSpielInfo());

		spielerschaft.broadcast(ssn);

		return StartRunde.class;
	}
}
