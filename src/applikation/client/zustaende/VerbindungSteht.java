package applikation.client.zustaende;

import applikation.nachrichten.BeitrittsBestaetigung;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand wenn der Client auf die {@link BeitrittsBestaetigung} vom Server
 * wartet. Wenn sie eintrifft wir der Zustand {@link LobbyStart} aufgerufen.
 */
public class VerbindungSteht extends AktiverClientZustand {
	Zustand beitrittsBestaetitigung(BeitrittsBestaetigung bestaetitigung) {

		System.out.println("Folgende Spieler sind dabei: ");

		for (String name : bestaetitigung.spielerNamen) {
			System.out.println(" - " + name);
		}

		return automat.getZustand(LobbyStart.class);
	}
}
