package applikation.client.zustaende;

import applikation.client.Controller;
import applikation.nachrichten.ChatNachricht;
import applikation.nachrichten.SpielStartNachricht;
import dienste.automat.zustaende.Zustand;
import dienste.netzwerk.EndPunkt;

/**
 * Zustand wennn der Spieler in der Lobby ist. Wenn eine
 * {@link SpielStartNachricht} eintrifft wird der Zustand {@link SpielStart}
 * aufgerufen.
 */
public class Lobby extends AktiverClientZustand {
	public Lobby(Controller controller) {
		this.controller = controller;
	}
	
	Zustand chatNachricht(EndPunkt absender, ChatNachricht nachricht) {
		System.out.println("Nachricht von " + absender + ": " + nachricht);
		return this;
	}

	Zustand spielStarten(SpielStartNachricht startNachricht) {
		for (int i = 0; i < startNachricht.spieler.length; i++) {
			String name = startNachricht.spieler[i];
			controller.fuegeSpielerHinzu(name);
		}

		return automat.getZustand(SpielStart.class);
	}
}
