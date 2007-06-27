package applikation.bot;

import applikation.client.ClientAutomat;
import applikation.client.controller.Controller;
import applikation.client.konfiguration.Konfiguration;
import dienste.automat.Automat;
import dienste.eventqueue.EventQueue;
import dienste.threads.BodesuriThread;

public class Botsuri extends BodesuriThread {
	private BotController controller;
	private EventQueue queue;
	private Konfiguration konfiguration;

	public Botsuri(Konfiguration konfig, EventQueue queue, Controller guiController, Class<? extends Bot> typ) {
		super("Bot " + konfig.defaultName);
		this.konfiguration = konfig;
		this.queue = queue;

		Bot bot = createBot(typ);

		controller = new BotController(konfig, queue, guiController, bot);
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
