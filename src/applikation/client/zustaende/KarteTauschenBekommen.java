package applikation.client.zustaende;

import applikation.client.pd.Karte;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand wenn wir auf die Karte die wir von unserem Mitspieler erhalten
 * warten. Wenn sie eintrifft wird der Zustand {@link NichtAmZug} aufgerufen.
 */
public class KarteTauschenBekommen extends ClientZustand {
	public void onEntry() {
		spiel.setHinweis("Warte auf die Karte deines Partners.");
	}

	Class<? extends Zustand> kartenTausch(pd.karten.Karte karte) {
		/*
		 * Wir erstellen hier die app.karte neu, diese wird dann nur für die
		 * jeweilige Runde verwendet (siehe auch rundenstart)
		 */
		Karte k = new Karte(karte);

		spiel.spielerIch.getKarten().add(k);
		return NichtAmZug.class;
	}

	Class<? extends Zustand> aufgegeben() {
		//TODO: Philippe: Aufgeben im GUI deaktivieren.
		controller.zeigeFehlermeldung("Bitte schau zuerst was du für eine"
		                              + " Karte bekommst bevor du aufgibst!");
		return this.getClass();
	}
}
