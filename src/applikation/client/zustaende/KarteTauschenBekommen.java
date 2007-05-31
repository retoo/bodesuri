package applikation.client.zustaende;

import dienste.automat.zustaende.Zustand;

/**
 * Zustand wenn wir auf die Karte die wir von unserem Mitspieler erhalten
 * warten. Wenn sie eintrifft wird der Zustand {@link NichtAmZug} aufgerufen.
 */
public class KarteTauschenBekommen extends PassiverClientZustand {
	public Zustand handle() {
		return automat.getZustand(NichtAmZug.class);
	}
}
