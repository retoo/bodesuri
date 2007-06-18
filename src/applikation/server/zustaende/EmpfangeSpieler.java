package applikation.server.zustaende;

import applikation.nachrichten.BeitrittsInformation;
import applikation.nachrichten.SpielBeitreten;
import dienste.automat.zustaende.Zustand;
import dienste.netzwerk.EndPunktInterface;

/**
 * Zustand in welchem neue Spieler empfangen werden. Sobald das Spiel komplett
 * ist wird der Zustand {@link SpielStart} aufgerufen.
 */
public class EmpfangeSpieler extends ServerZustand {
	Class<? extends Zustand> spielBeitreten(EndPunktInterface absender,
	                                        SpielBeitreten beitreten) {

		spiel.broadcast(new BeitrittsInformation(spiel.getSpielInfo()));

		if (spiel.isKomplett()) {
			spiel.partnerschaftenBilden();

			return SpielStart.class;
		}

		return EmpfangeSpieler.class;
	}
}
