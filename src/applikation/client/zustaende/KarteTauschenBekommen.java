package applikation.client.zustaende;

import pd.karten.Karte;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand wenn wir auf die Karte die wir von unserem Mitspieler erhalten
 * warten. Wenn sie eintrifft wird der Zustand {@link NichtAmZug} aufgerufen.
 */
public class KarteTauschenBekommen extends ClientZustand {
	Class<? extends Zustand> kartenTausch(Karte karte) {
		spielDaten.spielerIch.getKarten().add(karte);
		return NichtAmZug.class;
	}
	
	Class<? extends Zustand> aufgegeben() {
		controller.zeigeFehlermeldung("Bitte schau zuerst was du für eine Karte bekommst bevor du aufgibst!");
		return this.getClass();
	}
}
