package applikation.server.zustaende;

import applikation.nachrichten.BeitrittsBestaetigung;
import applikation.nachrichten.SpielBeitreten;
import applikation.server.Spieler;
import applikation.server.Spielerschaft;
import dienste.automat.zustaende.Zustand;
import dienste.netzwerk.EndPunkt;

/**
 * Zustand in welchem neue Spieler empfangen werden. Sobald das Spiel komplett
 * ist wird der Zustand {@link StarteSpiel} aufgerufen.
 */
public class EmpfangeSpieler extends ServerZustand {
	Class<? extends Zustand> spielBeitreten(EndPunkt absender,
	                                        SpielBeitreten beitreten) {
		Spielerschaft spielers = spielDaten.spielerschaft;


		pd.spieler.Spieler neuerSpieler = spielDaten.spiel.fuegeHinzu(beitreten.spielerName);

		Spieler spieler = new Spieler(absender, beitreten.spielerName, neuerSpieler);
		spielers.add(spieler);

		spielers.broadcast(new BeitrittsBestaetigung(spielers.getStringArray()));

		System.out.println("Neuer Spieler: " + spieler);

		if (spielers.isKomplett()) {
			spielers.partnerschaftenBilden();

			return StarteSpiel.class;
		}

		return EmpfangeSpieler.class;
	}
}
