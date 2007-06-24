package spielplatz;

import applikation.client.ClientAutomat;
import applikation.client.konfiguration.Konfiguration;
import dienste.automat.Automat;
import dienste.eventqueue.EventQueue;
import dienste.threads.BodesuriThread;

public class Botsuri extends BodesuriThread {
	private BotController controller;
	private EventQueue queue;
	private Konfiguration konfiguration;

	public Botsuri(Konfiguration konfig, String host, int port, Class<? extends Bot> typ, boolean gui) {
		super("Bot " + konfig.defaultName);
		this.konfiguration = konfig;

		Bot bot = createBot(typ);

		queue = new EventQueue();
		controller = new BotController(queue, konfig.defaultName, host, port, bot, gui);
    }

	private Bot createBot(Class<? extends Bot> typ) {
		try {
			return typ.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void run() {
		Automat client = new ClientAutomat(controller, queue, konfiguration);
		client.init();
	    client.run();
	}
}
