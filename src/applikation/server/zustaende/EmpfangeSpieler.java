package applikation.server.zustaende;

import applikation.nachrichten.BeitrittVerweigert;
import applikation.nachrichten.BeitrittsInformation;
import applikation.nachrichten.SpielBeitreten;
import applikation.server.pd.Spiel;
import applikation.server.pd.Spieler;
import dienste.automat.zustaende.Zustand;
import dienste.netzwerk.EndPunktInterface;

/**
 * Der Server empfängt solange neue {@link Spieler} bis das {@link Spiel} vollzählig ist.
 *
 * Erhält der Server die Nachricht {@link SpielBeitreten} prüft er ob bereits ein
 * Spieler dem angegebenen Namen im Spiel vorhanden ist. Falls nicht, bestätigt
 * er den Beitritt mit der Nachricht {@link SpielBeitreten} und informiert die
 * restlichen Spieler über den Neuling mit der Nachricht {@link BeitrittsInformation}.
 *
 * Sobald alle Spieler komplett sind, wird in den Zustand {@link SpielStart}
 * gewechselt.
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
