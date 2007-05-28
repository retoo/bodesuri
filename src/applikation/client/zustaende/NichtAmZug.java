package applikation.client.zustaende;

import pd.regelsystem.RegelVerstoss;
import applikation.nachrichten.ChatNachricht;
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
public class NichtAmZug extends AktiverClientZustand {
	Zustand chatNachricht(EndPunkt absender, ChatNachricht nachricht) {
		System.out.println("Nachricht von " + absender + ": " + nachricht);
		return this;
	}

	Zustand zugAufforderung() {
		return automat.getZustand(AmZug.class);
	}

	Zustand zugWurdeGemacht(ZugInformation information) {
		try {
			information.zug.validiere().ausfuehren();
		} catch (RegelVerstoss e) {
			System.out.println("Ungültigen Zug (" + e
			                   + ") vom Server erhalten!");
			return automat.getZustand(SchwererFehler.class);
		}
		return this;
	}
}
