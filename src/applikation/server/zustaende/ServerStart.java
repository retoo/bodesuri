package applikation.server.zustaende;

import java.io.IOException;

import applikation.server.BodesuriServer;
import applikation.server.Spielerschaft;

import dienste.automat.Zustand;
import dienste.netzwerk.server.Server;

public class ServerStart extends PassiveServerZustand {
	protected Zustand execute() {
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
