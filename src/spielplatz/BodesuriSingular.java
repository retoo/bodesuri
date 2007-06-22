package spielplatz;

import ui.GUIController;
import applikation.client.ClientAutomat;
import applikation.client.controller.Controller;
import applikation.client.events.VerbindeEvent;
import applikation.server.ServerAutomat;
import dienste.automat.Automat;
import dienste.eventqueue.EventQueue;

public class BodesuriSingular {

	public static void main(String[] args) {
		Thread server = new Thread(new Runnable() {
			public void run() {
				ServerAutomat server = new ServerAutomat(1);

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


		EventQueue eventQueue = new EventQueue();
		Controller controller = new GUIController(eventQueue, "Singular");
		Automat client = new ClientAutomat(controller, eventQueue);

		String host = "localhost";
		int port = 7788;
		String spieler = "Mr. Singular";

		eventQueue.enqueue(new VerbindeEvent(host, port, spieler));

		try {
			client.run();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Client: Exception in run()");
			System.exit(99);
		}
	}
}
