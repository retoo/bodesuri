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
				ServerAutomat server = new ServerAutomat(2);

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

		for (int i = 0; i < 2; i++) {
			Thread client = new Thread(new Runnable() {
				public void run() {
					Controller controller = new GUIController();
					Automat client = new ClientAutomat(controller, Thread.currentThread().getName());
					controller.getSteuerung().verbinde("localhost", 7788, Thread.currentThread().getName());

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
