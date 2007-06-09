package spielplatz;

import ui.GUIController;
import applikation.client.ClientAutomat;
import applikation.client.controller.Controller;
import applikation.server.ServerAutomat;
import dienste.automat.Automat;

public class BodesuriEntwickler {

	public static void main(String[] args) {
		Thread server = new Thread(new Runnable() {
			public void run() {
				ServerAutomat server = new ServerAutomat();

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
					Automat client = new ClientAutomat(controller);

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
