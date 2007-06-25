package applikation.client.zustaende;

import pd.spieler.Partnerschaft;
import applikation.client.controller.Controller;
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
		controller.zeigeLobby(spiel.getSpieler(), spiel.chat);
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
		for (Partnerschaft ps : startNachricht.partnerschaften) {
			ps.getSpielerA().setPartner(ps.getSpielerB());
			ps.getSpielerB().setPartner(ps.getSpielerA());
		}

		return SpielStart.class;
	}
}
