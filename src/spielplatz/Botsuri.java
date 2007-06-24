package spielplatz;

import applikation.client.ClientAutomat;
import dienste.automat.Automat;
import dienste.eventqueue.EventQueue;
import dienste.threads.BodesuriThread;

public class Botsuri extends BodesuriThread {
	private BotController controller;
	private EventQueue queue;

	public Botsuri(String name, String host, int port, Class<? extends Bot> typ, boolean gui) {
		super("Bot " + name);
		Bot bot = createBot(typ);

		queue = new EventQueue();
		controller = new BotController(queue, name, host, port, bot, gui);
    }

	private Bot createBot(Class<? extends Bot> typ) {
		try {
			return typ.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void run() {
		Automat client = new ClientAutomat(controller, queue);
		client.init();
	    client.run();
	}
}
