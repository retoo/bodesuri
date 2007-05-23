package applikation.client.zustaende;

import applikation.server.nachrichten.BeitrittsBestaetigung;
import dienste.automat.Zustand;

public class VerbindungWirdAufgebaut extends AktiverClientZustand {
	Zustand beitrittsBestaetitigung(BeitrittsBestaetigung bestaetitigung) {
		
		System.out.println("Folgende Spieler sind dabei: ");
		
		for (String name : bestaetitigung.spielerNamen) {
			System.out.println(" - " + name);
		}
		
		return automat.getZustand(LobbyStart.class);
	}
}
