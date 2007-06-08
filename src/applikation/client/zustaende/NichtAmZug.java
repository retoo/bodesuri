package applikation.client.zustaende;

import pd.regelsystem.RegelVerstoss;
import applikation.client.zugautomat.ZugAutomat;
import applikation.nachrichten.ChatNachricht;
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
	Class<? extends Zustand> chatNachricht(EndPunkt absender, ChatNachricht nachricht) {
		System.out.println("Nachricht von " + absender + ": " + nachricht);
		return this.getClass();
	}

	Class<? extends Zustand> zugAufforderung() {
		spielDaten.zugAutomat = new ZugAutomat(controller, spielDaten.queue);
		//Nur vorübergehend -> siehe KarteWaehlen.
		controller.kartenAuswahl(true);
		return AmZug.class;
	}

	Class<? extends Zustand> zugWurdeGemacht(ZugInformation information) {
		try {
			information.zug.validiere().ausfuehren();
		} catch (RegelVerstoss e) {
			controller.zeigeFehlermeldung("Ungültigen Zug (" + e
			                              + ") vom Server erhalten!");
			return SchwererFehler.class;
		}
		return this.getClass();
	}

	Class<? extends Zustand> rundenStart(RundenStart rundenStart) {
		// TODO: rundenStart.neueKarten in Spieler speichern.
		// Oder muss das in StarteRunde gemacht werden? (Der hätte dann aber
		// keinen rundenStart.) Robin
		// Nein, das kommt schon hierhin. --Philippe

		return StarteRunde.class;
	}
}
