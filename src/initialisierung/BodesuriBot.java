package initialisierung;


import ui.GUIController;
import applikation.bot.Bot;
import applikation.bot.BotController;
import applikation.bot.Stupidbot;
import applikation.client.ClientAutomat;
import applikation.client.konfiguration.Konfiguration;
import dienste.automat.Automat;
import dienste.eventqueue.EventQueue;

public class BodesuriBot extends Thread {
	private BotController controller;
	private EventQueue queue;
	private Konfiguration konfiguration;

	public BodesuriBot(Konfiguration konfig, Class<? extends Bot> typ, boolean gui) {
		super("Bot " + konfig.defaultName);
		this.konfiguration = konfig;
		this.queue = new EventQueue();

		GUIController guiController = null;

		if (gui) {
			guiController = new GUIController(queue, konfig);
		}

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

	public static void main(String[] args) throws InterruptedException {
		Konfiguration konfig = new Konfiguration();
		konfig.defaultName = "Roboter";


		BodesuriBot bot = new BodesuriBot(konfig, Stupidbot.class, true);

		bot.start();
		bot.join();
	}
}
