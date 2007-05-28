package applikation.server.zustaende;

import java.io.IOException;

import applikation.server.BodesuriServer;
import applikation.server.Spielerschaft;

import dienste.automat.zustaende.Zustand;
import dienste.netzwerk.server.Server;

/**
 * Passiver Zustand der den Server initialisiert.
 */
public class ServerStart extends PassiverServerZustand {
	public Zustand handle() {
		System.out.println("Initialiere Server");

		try {
			automat.server = new Server(automat.queue);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		automat.spielerschaft = new Spielerschaft(BodesuriServer.MAXSPIELER);

		return automat.getZustand(EmpfangeSpieler.class);
	}
}
