package applikation.client.zustaende;

import applikation.client.controller.Controller;
import applikation.nachrichten.BeitrittsBestaetigung;
import applikation.nachrichten.SpielStartNachricht;
import applikation.server.pd.SpielerInfo;
import dienste.automat.zustaende.Zustand;
import dienste.netzwerk.EndPunkt;

/**
 * Zustand wennn der Spieler in der Lobby ist. Wenn eine
 * {@link SpielStartNachricht} eintrifft wird der Zustand {@link SpielStart}
 * aufgerufen.
 */
public class Lobby extends ClientZustand {
	public Lobby(Controller controller) {
		this.controller = controller;
	}

	Class<? extends Zustand> chatNachricht(EndPunkt absender, String nachricht) {
		System.out.println("Nachricht von " + absender + ": " + nachricht);
		return this.getClass();
	}

    Class<? extends Zustand> beitrittsBestaetitigung(BeitrittsBestaetigung bestaetitigung) {
    	//TODO: Controller(Lobby) über neuen Spieler benachrichtigen.
	    return this.getClass();
    }

	Class<? extends Zustand> spielStarten(SpielStartNachricht startNachricht) {

		for (SpielerInfo si : startNachricht.spielInfo.spielers) {
			/* TODO: inline me */
			controller.fuegeSpielerHinzu(si.name);
		}

		return SpielStart.class;
	}
}
