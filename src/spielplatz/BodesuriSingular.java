package spielplatz;

import ui.GUIController;
import applikation.client.ClientAutomat;
import applikation.client.controller.Controller;
import applikation.server.ServerAutomat;
import dienste.automat.Automat;

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


		//EventQueue eventQueue = new EventQueue();
		Controller controller = new GUIController();
		Automat client = new ClientAutomat(controller, "Singular");

		controller. verbinde("localhost", 7788, "Singular");


		try {
			client.run();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Client: Exception in run()");
			System.exit(99);
		}
	}
}
