package applikation.client.states;

import applikation.server.nachrichten.BeitrittsBestaetigung;
import dienste.statemachine.State;

public class VerbindungWirdAufgebaut extends ActiveClientState {
	State beitrittsBestaetitigung(BeitrittsBestaetigung bestaetitigung) {
		
		System.out.println("Folgende Spieler sind dabei: ");
		
		for (String name : bestaetitigung.spielerNamen) {
			System.out.println(" - " + name);
		}
		
		return machine.getState(LobbyStart.class);
	}
}
