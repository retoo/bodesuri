package applikation.client.zugautomat.zustaende;

import applikation.client.Controller;
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
	public KarteWaehlen(Controller controller) {
		this.controller = controller;
	}

	public void entry() {
		// TODO: Wird erst ausgeführt wenn wir das das nächste Ereignis erhalten
		// und den Automaten mit Step brauchen. -> Bis auf weiteres verschoben nach NichtAmZug
		// controller.kartenAuswahl(true);
	}

	Zustand chatNachricht(EndPunkt absender, ChatNachricht nachricht) {
		System.out.println("Nachricht von " + absender + ": " + nachricht);
		return this;
	}

	Zustand karteGewaehlt(KarteGewaehltEvent event) {
		automat.karte = event.karte;
		return automat.getZustand(StartWaehlen.class);
	}
}
