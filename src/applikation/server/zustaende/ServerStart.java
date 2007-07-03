package applikation.server.zustaende;

import dienste.automat.zustaende.PassiverZustand;
import dienste.automat.zustaende.Zustand;
import dienste.netzwerk.server.Server;
import dienste.serialisierung.SerialisierungsKontext;

/**
 * {@link Server} wird gestartet. Der TCP-Daemon wird initialisiert und es werden die
 * notwendigen Vorbereitungen getroffen um Spieler zu akzeptieren. Geht direkt in
 * den Zustand {@link EmpfangeSpieler} über.
 */
public class ServerStart extends ServerZustand implements PassiverZustand {
	public Class<? extends Zustand> handle() {
		SerialisierungsKontext kontext = spiel;

		spiel.server = new Server(spiel.queue, kontext);

		return EmpfangeSpieler.class;
	}
}