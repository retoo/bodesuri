package applikation.server.zustaende;

import applikation.server.BodesuriServer;
import applikation.server.Spieler;
import applikation.server.Spielerschaft;
import applikation.server.nachrichten.BeitrittsBestaetigung;
import applikation.server.nachrichten.SpielBeitreten;
import dienste.automat.zustaende.Zustand;
import dienste.netzwerk.EndPunkt;

public class EmpfangeSpieler extends AktiverServerZustand {
	Zustand spielBeitreten(EndPunkt absender, SpielBeitreten beitreten) {

		Spieler spieler = new Spieler(absender, beitreten.spielerName);

		Spielerschaft spielers = automat.spielerschaft;

		spielers.add(spieler);

		spieler.endpunkt.sende(new BeitrittsBestaetigung(spielers.getStringArray()));

		String msg = "Neuer Spieler "
		             + spieler.spielerName
		             + ". Noch "
		             + (BodesuriServer.MAXSPIELER - spielers.getAnZahlSpieler())
		             + " Spieler nötig.";

		spielers.broadcast(msg);

		System.out.println("Neuer Spieler: " + spieler);

		if (spielers.isKomplett()) {
			return automat.getZustand(StarteSpiel.class);
		} else {
			return this;
		}
	}
}
