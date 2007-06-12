package applikation.server.zustaende;

import applikation.nachrichten.BeitrittsBestaetigung;
import applikation.nachrichten.SpielBeitreten;
import applikation.server.pd.Spieler;
import applikation.server.pd.Spielerschaft;
import dienste.automat.zustaende.Zustand;
import dienste.netzwerk.EndPunkt;

/**
 * Zustand in welchem neue Spieler empfangen werden. Sobald das Spiel komplett
 * ist wird der Zustand {@link SpielStart} aufgerufen.
 */
public class EmpfangeSpieler extends ServerZustand {
	Class<? extends Zustand> spielBeitreten(EndPunkt absender,
	                                        SpielBeitreten beitreten) {
		Spielerschaft spielers = spielDaten.spielerschaft;


		/* PD Spieler erstelle n*/
		pd.spieler.Spieler neuerSpieler = spielDaten.spiel.fuegeHinzu(beitreten.spielerName);

		Spieler spieler = new Spieler(absender, neuerSpieler);
		spielers.add(spieler);

		spielers.broadcast(new BeitrittsBestaetigung(spielers.getSpielInfo()));

		System.out.println("Neuer Spieler: " + spieler);

		if (spielers.isKomplett()) {
			spielers.partnerschaftenBilden();

			return SpielStart.class;
		}

		return EmpfangeSpieler.class;
	}
}
