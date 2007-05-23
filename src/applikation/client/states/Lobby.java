package applikation.client.states;

import applikation.server.nachrichten.ChatNachricht;
import applikation.server.nachrichten.SpielStartNachricht;
import dienste.netzwerk.EndPunkt;
import dienste.statemachine.State;

public class Lobby extends ActiveClientState {
	State chatNachricht(EndPunkt absender, ChatNachricht nachricht) {
		System.out.println("Nachricht von " + absender + ":");
		System.out.println(nachricht);
		return this;
	}

	State spielStarten(SpielStartNachricht startNachricht) {
		automat.spiel = new pd.Spiel();

		for (int i = 0; i < startNachricht.spieler.length; i++) {
			String name = startNachricht.spieler[i];

			automat.spiel.fuegeHinzu(name);
			if (name.equals(automat.spielerName)) {
				automat.spielerIch = automat.spiel.getSpieler().get(i);
			}
		}

		return automat.getState(SpielStart.class);
	}
}
