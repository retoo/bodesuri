package applikation.client.zustaende;


import pd.regelsystem.ZugEingabe;
import pd.regelsystem.verstoesse.RegelVerstoss;
import applikation.client.pd.Karte;
import applikation.client.pd.Spieler;
import applikation.nachrichten.AktuellerSpielerInformation;
import applikation.nachrichten.AufgabeInformation;
import applikation.nachrichten.RundenStart;
import applikation.nachrichten.SpielFertigNachricht;
import applikation.nachrichten.ZugAufforderung;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand wenn der Spieler nicht am Zug ist.
 * <ul>
 * <li>Wenn eine {@link ZugAufforderung} eintrifft, wird der Zustand
 * {@link AmZug} aufgerufen.</li>
 * <li>Wenn eine {@link AktuellerSpielerInformation} eintrifft, wird der
 * aktuelle Spieler dargestellt, der Zustand aber nicht gewechselt.</li>
 * <li>Wenn eine {@link ZugEingabe} eintrifft, wird Zug aus der Eingabe
 * visualisiert. Der Zustand wird nicht gewechselt.</li>
 * <li>Wenn eine {@link RundenStart}-Nachricht eintrifft, werden die
 * erhaltenen Karten gespeichert und der Zustand {@link StarteRunde} aufgerufen.</li>
 * <li>Wenn eine {@link AufgabeInformation} eintrifft, wird der Spieler welcher
 * aufgegeben hat im UI angepasst. Der Zustand wird nicht gewechselt.</li>
 * <li>Wenn eine {@link SpielFertigNachricht} eintrifft, wird der Gewinner
 * angezeigt und {@link SpielEnde} aufgerufen.</li>
 * </ul>
 */
public class NichtAmZug extends ClientZustand {
	Class<? extends Zustand> zugAufforderung(ZugAufforderung zugAufforderung) {
		controller.zugAufforderung();
		return AmZug.class;
	}

	Class<? extends Zustand> aktuellerSpielerInformation(AktuellerSpielerInformation information) {
		Spieler neuerSpieler = spiel.findeSpieler(information.spieler);

		if (spiel.aktuellerSpieler != null) {
			spiel.aktuellerSpieler.setAmZug(false);
		}

		neuerSpieler.setAmZug(true);
		spiel.aktuellerSpieler = neuerSpieler;

		spiel.setHinweis(neuerSpieler + " ist am Zug.");
		return this.getClass();
	}

	Class<? extends Zustand> zugWurdeGemacht(ZugEingabe zug) {
		spiel.chat.meldeZug(zug);

		try {
			zug.validiere().ausfuehren();
		} catch (RegelVerstoss e) {
			controller.zeigeFehlermeldung("Ungültigen Zug (" + e
			                              + ") vom Server erhalten!");
			return SchwererFehler.class;
		}

		controller.zugWurdeGemacht(zug);
		return this.getClass();
	}

	Class<? extends Zustand> rundenStart(RundenStart rundenStart) {
		spiel.chat.meldeRundenStart();
		spiel.spielerIch.getKarten().clear();
		for (pd.regelsystem.karten.Karte karte : rundenStart.neueKarten) {
			spiel.spielerIch.getKarten().add(new Karte(karte));
		}

		return StarteRunde.class;
	}

	Class<? extends Zustand> aufgabe(AufgabeInformation aufgabe) {
		Spieler spieler = spiel.findeSpieler(aufgabe.spieler);
		spiel.chat.meldeAufgabe(aufgabe);
		spieler.setHatAufgebeben(true);
		return this.getClass();
	}

	Class<? extends Zustand> spielFertig(SpielFertigNachricht nachricht) {
		controller.zeigeMeldung("Das Spiel ist fertig, gewonnen haben "  + nachricht.gewinner);

		return SpielEnde.class;
	}
}
