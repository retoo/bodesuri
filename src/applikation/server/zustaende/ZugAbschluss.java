package applikation.server.zustaende;

import dienste.automat.zustaende.PassiverZustand;
import dienste.automat.zustaende.Zustand;

public class ZugAbschluss extends ServerZustand implements PassiverZustand {
	public Class<? extends Zustand> handle() {

		if (1 == 2 /* SpielFertig */) {
			return ServerEnde.class;
		} else if (spiel.runde.isFertig()) {
			/* wie erkennen wir das ne runde fertig ist?
			 * ich würd sagen der client meldet seine 'Aufgabe'
			 * sobald er die Aufgabe bekommt. Der Server trackt
			 * wer noch alles in der Runde ist und tut, sobald alle draus
			 * sind die Runde abbrechen.
			 */
			return StartRunde.class;
		} else { /* ganz noraml weiter zum nächsten zug */
			return StarteZug.class;
		}
	}
}
