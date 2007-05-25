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
		Automat sync = new BodesuriClient(queue);

		sync.run();
	}
}
