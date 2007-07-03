package applikation.server.zustaende;

import applikation.nachrichten.RundenStart;
import applikation.server.pd.Runde;
import applikation.server.pd.RundenTeilnahme;
import applikation.server.pd.Spieler;
import dienste.automat.zustaende.PassiverZustand;
import dienste.automat.zustaende.Zustand;

/**
 * Eine neue {@link Runde} wird gestartet und mit der Nachricht {@link RundenStart} den
 * Spielern mitgeteilt. Es werden allen Spielern Karten ausgeteilt.
 */
public class StartRunde extends ServerZustand implements PassiverZustand {
	public Class<? extends Zustand> handle() {
		Runde runde = spiel.starteRunde();

		for (RundenTeilnahme teilnahme: runde.getTeilnamhmen()) {
			Spieler spieler = teilnahme.getSpieler();
			spieler.sende( new RundenStart(teilnahme.getKarten()) );
		}

		return KartenTauschen.class;
	}
}
