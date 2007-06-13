package applikation.server.zustaende;

import applikation.nachrichten.BeitrittsBestaetigung;
import applikation.nachrichten.SpielBeitreten;
import applikation.server.pd.Spieler;
import dienste.automat.zustaende.Zustand;
import dienste.netzwerk.EndPunktInterface;

/**
 * Zustand in welchem neue Spieler empfangen werden. Sobald das Spiel komplett
 * ist wird der Zustand {@link SpielStart} aufgerufen.
 */
public class EmpfangeSpieler extends ServerZustand {
	Class<? extends Zustand> spielBeitreten(EndPunktInterface absender,
	                                        SpielBeitreten beitreten) {


		Spieler spieler = spiel.neuerSpieler(beitreten.spielerName, absender);


		spiel.broadcast(new BeitrittsBestaetigung(spiel.getSpielInfo()));

		System.out.println("Neuer Spieler: " + spieler);

		if (spiel.isKomplett()) {
			spiel.partnerschaftenBilden();

			return SpielStart.class;
		}

		return EmpfangeSpieler.class;
	}
}
