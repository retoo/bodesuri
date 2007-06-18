package applikation.client.zustaende;

import pd.regelsystem.RegelVerstoss;
import applikation.client.pd.Karte;
import applikation.client.pd.Spieler;
import applikation.client.zugautomat.ZugAutomat;
import applikation.nachrichten.AktuellerSpielerInformation;
import applikation.nachrichten.RundenStart;
import applikation.nachrichten.ZugAufforderung;
import applikation.nachrichten.ZugInformation;
import dienste.automat.zustaende.Zustand;
import dienste.netzwerk.EndPunktInterface;

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
	Class<? extends Zustand> chatNachricht(EndPunktInterface absender, String nachricht) {
		System.out.println("Nachricht von " + absender + ": " + nachricht);
		return this.getClass();
	}

	Class<? extends Zustand> zugAufforderung(ZugAufforderung zugAufforderung) {

		spielDaten.zugAutomat = new ZugAutomat(controller, spielDaten.queue,
		                                       spielDaten.spiel,
		                                       spielDaten.spielerIch);
		spielDaten.zugAutomat.init();
		return AmZug.class;
	}

    Class<? extends Zustand> aktuellerSpielerInformation(AktuellerSpielerInformation information) {
		Spieler neuerSpieler = spielDaten.spiel.findeSpieler(information.spieler);

		if (spielDaten.aktuellerSpieler != null) {
			spielDaten.aktuellerSpieler.setAmZug(false);
		}

		neuerSpieler.setAmZug(true);
		spielDaten.aktuellerSpieler = neuerSpieler;

		spielDaten.spiel.setHinweis(neuerSpieler.getName()
			                        + " ist am Zug.");
		return this.getClass();
    }


	Class<? extends Zustand> zugWurdeGemacht(pd.zugsystem.ZugEingabe zug) {
		spielDaten.spiel.setLetzterZug(zug);
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
		spielDaten.spiel.setLetzterZug(null);
		for (pd.karten.Karte karte : rundenStart.neueKarten) {
			spielDaten.spielerIch.getKarten().add(new Karte(karte));
		}
		return StarteRunde.class;
	}
}
