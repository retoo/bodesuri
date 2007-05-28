package applikation.server.zustaende;

import applikation.nachrichten.BeitrittsBestaetigung;
import applikation.nachrichten.SpielBeitreten;
import applikation.server.Spieler;
import applikation.server.Spielerschaft;
import dienste.automat.zustaende.Zustand;
import dienste.netzwerk.EndPunkt;

/**
 * Zustand in welchem neue Spieler empfangen werden. Sobald das Spiel komplett
 * ist wird der Zustand {@link StarteSpiel} aufgerufen.s
 */
public class EmpfangeSpieler extends AktiverServerZustand {
	Zustand spielBeitreten(EndPunkt absender, SpielBeitreten beitreten) {
		Spielerschaft spielers = automat.spielerschaft;

		Spieler spieler = new Spieler(absender, beitreten.spielerName);
		spielers.add(spieler);

		String[] spielers_raw = spielers.getStringArray();
		spieler.endpunkt.sende(new BeitrittsBestaetigung(spielers_raw));

		String msg = "Neuer Spieler " + spieler.name + ". Noch "
		             + spielers.getAnzahlOffen() + " Spieler nötig.";

		spielers.broadcast(msg);

		System.out.println("Neuer Spieler: " + spieler);

		if (spielers.isKomplett()) {
			return automat.getZustand(StarteSpiel.class);
		}

		return this;
	}
}
