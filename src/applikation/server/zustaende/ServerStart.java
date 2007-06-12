package applikation.server.zustaende;

import java.io.IOException;

import pd.Spiel;
import applikation.server.pd.Spielerschaft;
import dienste.automat.zustaende.PassiverZustand;
import dienste.automat.zustaende.Zustand;
import dienste.netzwerk.server.Server;
import dienste.serialisierung.SerialisierungsKontext;

/**
 * Passiver Zustand der den Server initialisiert.
 */
public class ServerStart extends ServerZustand implements PassiverZustand {
	public Class<? extends Zustand> handle() {
		System.out.println("Initialisiere Server");

		SerialisierungsKontext kontext = spielDaten;


		spielDaten.spiel = new Spiel();

		try {
			spielDaten.server = new Server(spielDaten.queue, kontext);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		spielDaten.spielerschaft = new Spielerschaft(spielDaten.anzSpieler);

		return EmpfangeSpieler.class;
	}
}