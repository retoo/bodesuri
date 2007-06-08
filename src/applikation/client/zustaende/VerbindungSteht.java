package applikation.client.zustaende;

import applikation.nachrichten.BeitrittsBestaetigung;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand wenn der Client auf die {@link BeitrittsBestaetigung} vom Server
 * wartet. Wenn sie eintrifft wir der Zustand {@link LobbyStart} aufgerufen.
 */
public class VerbindungSteht extends ClientZustand {
	Class<? extends Zustand> beitrittsBestaetitigung(BeitrittsBestaetigung bestaetitigung) {

		return LobbyStart.class;
	}
}
