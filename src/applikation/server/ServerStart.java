package applikation.server;

import java.io.IOException;

import dienste.automat.Zustand;
import dienste.netzwerk.server.Server;

public class ServerStart extends PassiveServerState {
	protected Zustand execute() {
		System.out.println("Initialiere Server");

		try {
			machine.server = new Server(machine.queue);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return machine.getZustand(EmpfangeSpieler.class);
	}
}