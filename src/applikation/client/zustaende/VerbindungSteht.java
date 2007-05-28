package applikation.client.zustaende;

import applikation.nachrichten.BeitrittsBestaetigung;
import dienste.automat.zustaende.Zustand;

public class VerbindungSteht extends AktiverClientZustand {
	Zustand beitrittsBestaetitigung(BeitrittsBestaetigung bestaetitigung) {
		
		System.out.println("Folgende Spieler sind dabei: ");
		
		for (String name : bestaetitigung.spielerNamen) {
			System.out.println(" - " + name);
		}
		
		return automat.getZustand(LobbyStart.class);
	}
}
