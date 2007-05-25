package ui;

import java.io.IOException;
import java.net.UnknownHostException;

import applikation.client.BodesuriClient;
import dienste.automat.Automat;
import dienste.automat.EventQueue;
import dienste.netzwerk.VerbindungWegException;

public class ClientController {
	public static void main(String[] args) throws UnknownHostException,
	                                      IOException, VerbindungWegException {
		EventQueue queue = new EventQueue();
		Automat automat = new BodesuriClient(queue);

		try {
			automat.run();
		} catch (Exception e) {
			/* Applikation stoppen wenn ein Fehler auftritt */
			e.printStackTrace();
			System.out.println("Client: Exception in run()");
			System.exit(99);
		}
	}
}
