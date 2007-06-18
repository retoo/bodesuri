package applikation.client.zustaende;

import pd.regelsystem.RegelVerstoss;
import applikation.client.pd.Karte;
import applikation.client.pd.Spieler;
import applikation.client.zugautomat.ZugAutomat;
import applikation.nachrichten.AktuellerSpielerInformation;
import applikation.nachrichten.AufgabeInformation;
import applikation.nachrichten.RundenStart;
import applikation.nachrichten.ZugAufforderung;
import applikation.nachrichten.ZugInformation;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand wenn der Spieler nicht am Zug ist.
 * <ul>
 * <li>Wenn eine {@link ZugAufforderung} eintrifft wird der Zustand
 * {@link AmZug} aufgerufen.</li>
 * <li>Wenn eine {@link ZugInformation} eintrifft wird der Zug ausgeführt, der
 * Zustand aber nicht gewechselt.</li>
 * </ul>
 */
public class NichtAmZug extends ClientZustand {
	Class<? extends Zustand> zugAufforderung(ZugAufforderung zugAufforderung) {

		spiel.zugAutomat = new ZugAutomat(controller, spiel.queue, spiel);
		spiel.zugAutomat.init();
		return AmZug.class;
	}

    Class<? extends Zustand> aktuellerSpielerInformation(AktuellerSpielerInformation information) {
		Spieler neuerSpieler = spiel.findeSpieler(information.spieler);

		if (spiel.aktuellerSpieler != null) {
			spiel.aktuellerSpieler.setAmZug(false);
		}

		neuerSpieler.setAmZug(true);
		spiel.aktuellerSpieler = neuerSpieler;

		spiel.setHinweis(neuerSpieler.getName()
			                        + " ist am Zug.");
		return this.getClass();
    }


	Class<? extends Zustand> zugWurdeGemacht(pd.zugsystem.ZugEingabe zug) {
		spiel.setLetzterZug(zug);
		try {
			zug.validiere().ausfuehren();
		} catch (RegelVerstoss e) {
			controller.zeigeFehlermeldung("Ungültigen Zug (" + e
			                              + ") vom Server erhalten!");
			return SchwererFehler.class;
		}
		return this.getClass();
	}

	Class<? extends Zustand> rundenStart(RundenStart rundenStart) {
		spiel.setLetzterZug(null);
		for (Spieler spieler : spiel.getSpieler()) {
			spieler.setHatAufgebeben(false);
		}

		spiel.spielerIch.getKarten().clear();
		for (pd.karten.Karte karte : rundenStart.neueKarten) {
			spiel.spielerIch.getKarten().add(new Karte(karte));
		}

		return StarteRunde.class;
	}

	Class<? extends Zustand> aufgabe(AufgabeInformation aufgabe) {
		Spieler spieler = spiel.findeSpieler(aufgabe.spieler);
		spieler.setHatAufgebeben(true);
		return this.getClass();
	}
}
