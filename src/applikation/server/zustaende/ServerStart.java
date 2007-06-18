package applikation.server.zustaende;

import java.io.IOException;

import dienste.automat.zustaende.PassiverZustand;
import dienste.automat.zustaende.Zustand;
import dienste.netzwerk.server.Server;
import dienste.serialisierung.SerialisierungsKontext;

/**
 * Passiver Zustand der den Server initialisiert.
 */
public class ServerStart extends ServerZustand implements PassiverZustand {
	public Class<? extends Zustand> handle() {
		SerialisierungsKontext kontext = spiel;

		try {
			spiel.server = new Server(spiel.queue, kontext);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return EmpfangeSpieler.class;
	}
}