package applikation.server.zustaende;

import pd.spiel.spieler.Partnerschaft;
import pd.zugsystem.Zug;
import applikation.nachrichten.SpielFertigNachricht;
import applikation.server.pd.Runde;
import applikation.server.pd.Spiel;
import dienste.automat.zustaende.PassiverZustand;
import dienste.automat.zustaende.Zustand;


/**
 * Server schliesst den {@link Zug} formell ab. Es wird geprüft, ob das {@link Spiel}
 * beziehungsweise die {@link Runde} abgeschlossen ist und in den Zustand {@link ServerStoppen}
 * respektive {@link StartRunde} gewechselt. Ist weder das {@link Spiel} noch die {@link Runde} fertig
 * wird in den Zustand {@link StarteZug} gewechselt.
 *
 * Bei Spielende wird der Gewinner mittels der Nachricht
 * {@link SpielFertigNachricht} allen Spielern mitgeteilt.
 */
public class ZugAbschluss extends ServerZustand implements PassiverZustand {
	public Class<? extends Zustand> handle() {
		if (spiel.istFertig()) {
			/* Gewinner den restlichen Spielern melden */
			Partnerschaft gewinner = spiel.getGewinner();
			spiel.broadcast(new SpielFertigNachricht(gewinner));

			return ServerStoppen.class;
		} else if (spiel.runde.isFertig()) {
			return StartRunde.class;
		} else { /* ganz normal weiter zum nächsten zug */
			return StarteZug.class;
		}
	}
}
