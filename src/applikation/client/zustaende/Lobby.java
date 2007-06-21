package applikation.client.zustaende;

import java.util.List;

import pd.spieler.Partnerschaft;
import applikation.client.controller.Controller;
import applikation.client.pd.Spieler;
import applikation.nachrichten.BeitrittsInformation;
import applikation.nachrichten.SpielStartNachricht;
import dienste.automat.zustaende.Zustand;
import dienste.netzwerk.EndPunktInterface;

/**
 * Zustand wennn der Spieler in der Lobby ist. Wenn eine
 * {@link SpielStartNachricht} eintrifft wird der Zustand {@link SpielStart}
 * aufgerufen.
 */
public class Lobby extends ClientZustand {
	public Lobby(Controller controller) {
		this.controller = controller;
	}
	
	public void onEntry() {
		controller.zeigeLobby(spiel.getSpiel().getSpieler(), spiel.chat);
	}

	Class<? extends Zustand> chatNachricht(EndPunktInterface absender, String nachricht) {
		System.out.println("Nachricht von " + absender + ": " + nachricht);
		return this.getClass();
	}

    Class<? extends Zustand> beitrittsBestaetitigung(BeitrittsInformation bestaetigung) {
    	for (int i = 0; i < bestaetigung.spielInfo.spielers.size(); i++) {
    		spiel.getSpiel().getSpieler().get(i).setName(bestaetigung.spielInfo.spielers.get(i).name);
    		
    		if (bestaetigung.spielInfo.spielers.get(i).name.equals(spiel.spielerName)) {
            	spiel.spielerIch = spiel.getSpieler().get(i);
            }
    	}
    	
	    return this.getClass();
    }

	Class<? extends Zustand> spielStarten(SpielStartNachricht startNachricht) {
		// Partnerschaften auf PD abbilden, damit validierung auf Clients funktioniert
		List<Spieler> spielers = spiel.getSpieler();
		for (Spieler s : spielers) {
			for (Partnerschaft ps : startNachricht.partnerschaften) {
				s.setPartner( ps.getPartner(s.getSpieler()) );
			}
		}

		return SpielStart.class;
	}
}
