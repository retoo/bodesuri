package applikation.client.zustaende;

import dienste.automat.zustaende.Zustand;

/**
 * Zustand in dem wir warten bis der Spieler die Karte die er tauschen möchte
 * ausgeählt hat. Hat er gewählt gehen wir in den Zustand
 * {@link KarteTauschenBekommen} über.
 */
public class KarteTauschenAuswaehlen extends PassiverClientZustand {
	public Zustand handle() {
		return automat.getZustand(KarteTauschenBekommen.class);
	}
}
