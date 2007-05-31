package applikation.client.zugautomat.zustaende;

import applikation.events.KarteGewaehltEvent;
import applikation.nachrichten.ChatNachricht;
import dienste.automat.zustaende.Zustand;
import dienste.netzwerk.EndPunkt;

/**
 * Zustand wenn der Spieler eine Karte auswählen muss. Wenn der
 * {@link KarteGewaehltEvent} eintritt, wird der Zustand {@link StartWaehlen}
 * aufgerufen.
 */
public class KarteWaehlen extends AktiverZugZustand {
	Zustand chatNachricht(EndPunkt absender, ChatNachricht nachricht) {
		System.out.println("Nachricht von " + absender + ": " + nachricht);
		return this;
	}

	Zustand karteGewaehlt(KarteGewaehltEvent event) {
		automat.karte = event.karte;
		return automat.getZustand(StartWaehlen.class);
	}
}
