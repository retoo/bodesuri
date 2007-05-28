package applikation.client.zustaende;

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
	Zustand chatNachricht(EndPunkt absender, ChatNachricht nachricht) {
		System.out.println("Nachricht von " + absender + ": " + nachricht);
		return this;
	}

	Zustand spielStarten(SpielStartNachricht startNachricht) {
		automat.spiel = new pd.Spiel();

		for (int i = 0; i < startNachricht.spieler.length; i++) {
			String name = startNachricht.spieler[i];

			automat.spiel.fuegeHinzu(name);
			if (name.equals(automat.spielerName)) {
				automat.spielerIch = automat.spiel.getSpieler().get(i);
			}
		}

		return automat.getZustand(SpielStart.class);
	}
}
