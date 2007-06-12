package applikation.client.zustaende;

import pd.spieler.Spieler;
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
			String name = si.name;
			Spieler neuerSpieler = spielDaten.spiel.fuegeHinzu(name);
            if (name.equals(spielDaten.spielerName)) {
            	spielDaten.spielerIch = neuerSpieler;
            }
            
            spielDaten.spielers.put(neuerSpieler, new applikation.client.Spieler(neuerSpieler));
		}

		return SpielStart.class;
	}
}
