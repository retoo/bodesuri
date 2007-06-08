package applikation.server.zustaende;

import pd.Spiel;
import applikation.nachrichten.SpielStartNachricht;
import applikation.server.Spieler;
import applikation.server.Spielerschaft;
import dienste.automat.PassiverZustand;
import dienste.automat.zustaende.Zustand;

/**
 * Passiver Zustand der das Spiel initialisiert.
 */
public class StarteSpiel extends ServerZustand implements PassiverZustand {
	public Class<? extends Zustand> handle() {
		Spielerschaft spielerschaft = spielDaten.spielerschaft;

		Spiel spiel = new Spiel();

		for (Spieler spieler : spielerschaft) {
			spiel.fuegeHinzu(spieler.name);
		}

		SpielStartNachricht ssn = new SpielStartNachricht(spielerschaft.getStringArray());

		spielerschaft.broadcast(ssn);

		return StartRunde.class;
	}
}
