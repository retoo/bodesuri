package spielplatz;

import applikation.client.ClientAutomat;
import applikation.client.controller.Controller;
import dienste.automat.Automat;
import dienste.eventqueue.EventQueue;

public class Botsuri implements Runnable {
	private Automat client;

	public Botsuri(String name, String host, int port, Class<? extends Bot> typ, boolean gui) {
		Bot bot = createBot(typ);

		EventQueue queue = new EventQueue();
		Controller controller = new BotController(queue, name, host, port, bot, gui);
		client = new ClientAutomat(controller, name, queue);
		client.init();
    }

	private Bot createBot(Class<? extends Bot> typ) {
		try {
			return typ.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void run() {
	    client.run();
	}
}
