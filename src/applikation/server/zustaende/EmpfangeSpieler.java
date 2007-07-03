package applikation.server.zustaende;

import applikation.nachrichten.BeitrittVerweigert;
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

		/* Verweigere den Zutritt wenn es bereits einen Spieler mit diesem Namen
		 * gibt.
		 */
		if (spiel.hatBereitsSpieler(beitreten.spielerName)) {
			BeitrittVerweigert bv = new BeitrittVerweigert(
					"Es hat bereits ein Spieler mit dem Namen "
							+ beitreten.spielerName
							+ ". Bitte wähle einen anderen Namen und versuche es erneut.");

			absender.sende(bv);

			absender.ausschalten();
			return this.getClass();
		}

		spiel.fuegeHinzu(beitreten.spielerName, absender);
		spiel.broadcast(new BeitrittsInformation(spiel.getSpielInfo()));

		if (spiel.isKomplett()) {
			spiel.partnerschaftenBilden();

			return SpielStart.class;
		}

		return EmpfangeSpieler.class;
	}
}
