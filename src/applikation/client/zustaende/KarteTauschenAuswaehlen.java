package applikation.client.zustaende;

import applikation.events.KarteGewaehltEvent;
import applikation.nachrichten.KartenTausch;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand in dem wir warten bis der Spieler die Karte die er tauschen möchte
 * ausgewählt hat. Tritt {@link KarteGewaehltEvent} ein gehen wir in den Zustand
 * {@link KarteTauschenBekommen} über.
 */
public class KarteTauschenAuswaehlen extends AktiverClientZustand {
	Zustand karteGewaehlt(KarteGewaehltEvent event) {
		//TODO: Karte aus Stapel des Spielers entfernen
		automat.endpunkt.sende(new KartenTausch(event.karte));
		return automat.getZustand(KarteTauschenBekommen.class);
	}
}
