package applikation.server.zustaende;

import dienste.automat.zustaende.Zustand;

public class ZugAbschluss extends PassiverServerZustand {

	@Override
	public Zustand handle() {

		if (1 == 2 /* SpielFertig */) {
			return automat.getZustand(ServerEnde.class);
		} else if (2 == 3 /* RundeFertig */) {
			/* wie erkennen wir das ne runde fertig ist?
			 * ich würd sagen der client meldet seine 'Aufgabe'
			 * sobald er die ZugAuffoderung bekommt. Der Server trackt
			 * wer noch alles in der Runde ist und tut, sobald alle draus
			 * sind die Runde abbrechen.
			 */
			return automat.getZustand(StarteRunde.class);
		} else { /* ganz noraml weiter zum nächsten zug */
			return automat.getZustand(StarteZug.class);
		}
	}
}
