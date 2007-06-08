package applikation.server.zustaende;

import java.io.IOException;

import pd.Spiel;
import applikation.server.Spielerschaft;
import dienste.automat.PassiverZustand;
import dienste.automat.zustaende.Zustand;
import dienste.netzwerk.server.Server;

/**
 * Passiver Zustand der den Server initialisiert.
 */
public class ServerStart extends ServerZustand implements PassiverZustand {
	public Class<? extends Zustand> handle() {
		System.out.println("Initialisiere Server");

		try {
			spielDaten.server = new Server(spielDaten.queue);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		spielDaten.spielerschaft = new Spielerschaft();

		spielDaten.spiel = new Spiel();

		return EmpfangeSpieler.class;
	}
}
