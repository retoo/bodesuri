package spielplatz;

import java.util.List;
import java.util.Vector;

import ui.GUIController;
import applikation.client.ClientAutomat;
import applikation.client.controller.Controller;
import applikation.server.ServerAutomat;
import dienste.automat.Automat;
import dienste.eventqueue.EventQueue;

public class BodesuriDemo {

	public static void main(String[] args) {
		Thread server = new Thread(new Runnable() {
			public void run() {
				ServerAutomat server = new ServerAutomat(4);

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


		List<String> namen = new Vector<String>();
		namen.add("Donald");
		namen.add("Daisy");
		namen.add("Mickey");

		for (int i = 0; i < 3; i++) {
			Botsuri b = new Botsuri(namen.get(i), "localhost", 7788, spielplatz.Stupidbot.class, false);

			Thread t = new Thread(b);
			t.start();
		}


		EventQueue queue = new EventQueue();
		Controller controller = new GUIController(queue, "Dog");
		Automat client = new ClientAutomat(controller,  queue);

		try {
			client.run();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Client: Exception in run()");
			System.exit(99);
		}
	}
}
