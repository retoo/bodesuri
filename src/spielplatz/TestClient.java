package spielplatz;

import applikation.client.ClientAutomat;
import applikation.client.controller.Controller;
import dienste.automat.Automat;
import dienste.eventqueue.EventQueue;

public class TestClient implements Runnable {
	private String name;

	public TestClient(String name) {
	    this.name = name;
    }

	public void run() {
		EventQueue queue = new EventQueue();
		Controller controller = new SurriController(queue, name);
		Automat client = new ClientAutomat(controller, name, queue);

		try {
			client.run();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Client: Exception in run()");
			System.exit(99);
		}

    }
}
