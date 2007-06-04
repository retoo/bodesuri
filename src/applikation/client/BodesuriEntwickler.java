package applikation.client;

import ui.BodesuriClientController;
import ui.ClientController;
import dienste.automat.Automat;
import dienste.automat.EventQueue;
import applikation.server.BodesuriServer;

public class BodesuriEntwickler {

	public static void main(String[] args) {
		Thread server = new Thread(new Runnable() {
			public void run() {
				BodesuriServer server = new BodesuriServer();

				try {
					server.run();
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Server: Exception in run()");
					System.exit(99);
				}
            }
		});

		server.start();

		for (int i = 0; i < 4; i++) {
			Thread client = new Thread(new Runnable() {
				public void run() {
					EventQueue eventQueue = new EventQueue();
					ClientController controller = new BodesuriClientController(eventQueue, Thread.currentThread().getName());
					Automat client = new BodesuriClient(eventQueue, controller);

					try {
						client.run();
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("Client: Exception in run()");
						System.exit(99);
					}
	            }
			}, "Spieler " + i); /* evil.. :P */

			client.start();
		}
	}
}
