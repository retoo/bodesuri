package applikation.client.zustaende;

import applikation.nachrichten.BeitrittsInformation;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand wenn der Client auf die {@link BeitrittsInformation} vom Server
 * wartet. Wenn sie eintrifft wir der Zustand {@link LobbyStart} aufgerufen.
 */
public class VerbindungSteht extends ClientZustand {
	Class<? extends Zustand> beitrittsBestaetitigung(BeitrittsInformation bestaetitigung) {

		return LobbyStart.class;
	}
}
