package applikation.client.zustaende;

import applikation.nachrichten.Aufgabe;
import pd.karten.Karte;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand wenn wir auf die Karte die wir von unserem Mitspieler erhalten
 * warten. Wenn sie eintrifft wird der Zustand {@link NichtAmZug} aufgerufen.
 */
public class KarteTauschenBekommen extends ClientZustand {
	Class<? extends Zustand> kartenTausch(Karte karte) {
		controller.getSpielerIch().getKarten().add(karte);
		return NichtAmZug.class;
	}
	
	Class<? extends Zustand> aufgegeben() {
		spielDaten.endpunkt.sende(new Aufgabe());
		return NichtAmZug.class;
	}
}
