package applikation.client.zustaende;

import pd.karten.Karte;
import pd.regelsystem.RegelVerstoss;
import pd.zugsystem.ZugEingabe;
import applikation.client.zugautomat.ZugAutomat;
import applikation.nachrichten.RundenStart;
import applikation.nachrichten.ZugAufforderung;
import applikation.nachrichten.ZugInformation;
import dienste.automat.zustaende.Zustand;
import dienste.netzwerk.EndPunkt;

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
	Class<? extends Zustand> chatNachricht(EndPunkt absender, String nachricht) {
		System.out.println("Nachricht von " + absender + ": " + nachricht);
		return this.getClass();
	}

	Class<? extends Zustand> zugAufforderung(ZugAufforderung zugAufforderung) {
		applikation.client.Spieler neuerSpieler = spielDaten.spielers.get(zugAufforderung.spieler);
		if (spielDaten.aktuellerSpieler != null) {
			spielDaten.aktuellerSpieler.setAmZug(false);
		}
		neuerSpieler.setAmZug(true);
		spielDaten.aktuellerSpieler = neuerSpieler;

		if (zugAufforderung.spieler == spielDaten.spielerIch) {
			spielDaten.zugAutomat = new ZugAutomat(controller,
			                                       spielDaten.queue,
			                                       spielDaten.spielerIch);
			spielDaten.zugAutomat.init();
			return AmZug.class;
		} else {
			controller.zeigeHinweis("Spieler "
			                        + zugAufforderung.spieler.getName()
			                        + " ist am Zug.");
			return this.getClass();
		}
	}

	Class<? extends Zustand> zugWurdeGemacht(ZugEingabe zug) {
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
		spielDaten.spielerIch.getKarten().clear();
		for (Karte karte : rundenStart.neueKarten) {
			spielDaten.spielerIch.getKarten().add(karte);
		}
		return StarteRunde.class;
	}
}
