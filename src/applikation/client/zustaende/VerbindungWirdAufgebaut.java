package applikation.client.zustaende;

import applikation.server.nachrichten.BeitrittsBestaetigung;
import dienste.automat.State;

public class VerbindungWirdAufgebaut extends ActiveClientState {
	State beitrittsBestaetitigung(BeitrittsBestaetigung bestaetitigung) {
		
		System.out.println("Folgende Spieler sind dabei: ");
		
		for (String name : bestaetitigung.spielerNamen) {
			System.out.println(" - " + name);
		}
		
		return automat.getState(LobbyStart.class);
	}
}
