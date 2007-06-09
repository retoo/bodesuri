package spielplatz;

import ui.GUIController;
import applikation.client.BodesuriClient;
import applikation.client.controller.Controller;
import applikation.server.BodesuriServer;
import dienste.automat.Automat;

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
					//EventQueue eventQueue = new EventQueue();
					Controller controller = new GUIController(Thread.currentThread().getName());
					Automat client = new BodesuriClient(controller);

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
