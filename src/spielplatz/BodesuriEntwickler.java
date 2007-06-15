package spielplatz;

import ui.GUIController;
import applikation.client.ClientAutomat;
import applikation.client.controller.Controller;
import applikation.events.VerbindeEvent;
import applikation.server.ServerAutomat;
import dienste.automat.Automat;
import dienste.eventqueue.EventQueue;

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
					String name = Thread.currentThread().getName();
					EventQueue queue = new EventQueue();
					Controller controller = new GUIController(queue);
					Automat client = new ClientAutomat(controller, name, queue);

					String host = "localhost";
					int port = 7788;
					String spieler = name;

					queue.enqueue(new VerbindeEvent(host, port, spieler));

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
