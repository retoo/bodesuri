package applikation.client.zustaende;

import applikation.client.ClientController;
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
	public Lobby(ClientController controller) {
		this.controller = controller;
	}
	
	Zustand chatNachricht(EndPunkt absender, ChatNachricht nachricht) {
		System.out.println("Nachricht von " + absender + ": " + nachricht);
		return this;
	}

	Zustand spielStarten(SpielStartNachricht startNachricht) {
		controller.setSpiel(new pd.Spiel());

		for (int i = 0; i < startNachricht.spieler.length; i++) {
			String name = startNachricht.spieler[i];

			controller.getSpiel().fuegeHinzu(name);
			if (name.equals(controller.getSpielerName())) {
				controller.setSpielerIch(controller.getSpiel().getSpieler().get(i));	// TODO: Ich glaube das geht auch einfacher ;-)
			}
		}

		return automat.getZustand(SpielStart.class);
	}
}
