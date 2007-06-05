package applikation.client.zustaende;

import pd.karten.Karte;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand wenn wir auf die Karte die wir von unserem Mitspieler erhalten
 * warten. Wenn sie eintrifft wird der Zustand {@link NichtAmZug} aufgerufen.
 */
public class KarteTauschenBekommen extends AktiverClientZustand {
	Zustand kartenTausch(Karte karte) {
		//Vorübergehend:
		System.out.println(karte);
		// TODO: Erhaltene Karte dem Stapel des Spielers hinzufügen.
		return automat.getZustand(NichtAmZug.class);
	}
}
