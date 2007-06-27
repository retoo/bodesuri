package initialisierung;


import java.util.List;
import java.util.Vector;

import applikation.bot.Botsuri;
import applikation.bot.Stupidbot;
import applikation.client.konfiguration.Konfiguration;
import dienste.eventqueue.EventQueue;

public class BodesuriDemo {

	public static void main(String[] args) throws InterruptedException {
		BodesuriServer server = new BodesuriServer();
		server.start();
		server.warteAufBereitschaft();

		Vector<Thread> clients = new Vector<Thread>();

		List<String> namen = new Vector<String>();
		namen.add("Donald");
		namen.add("Daisy");
		namen.add("Mickey");

		for (int i = 0; i < 3; i++) {

			Konfiguration konfig = new Konfiguration();
			konfig.defaultName = namen.get(i);

			EventQueue queue = new EventQueue();
			Botsuri b = new Botsuri(konfig, queue, null, Stupidbot.class);

			b.start();

			clients.add(b);
		}

		Bodesuri b = new Bodesuri();
		b.start();

		server.join();
		b.join();

		for (Thread t : clients) {
			t.join();
		}
	}
}
