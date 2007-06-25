package spielplatz;

import initialisierung.Bodesuri;
import initialisierung.BodesuriServer;

import java.util.List;
import java.util.Vector;

import applikation.client.konfiguration.Konfiguration;

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

		for (int i = 0; i < 2; i++) {
			Konfiguration konfig = new Konfiguration();
			konfig.defaultName = namen.get(i);

			Botsuri b = new Botsuri(konfig, "localhost", 7788, spielplatz.Stupidbot.class, false);

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
