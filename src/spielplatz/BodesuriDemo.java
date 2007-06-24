package spielplatz;

import initialisierung.Bodesuri;
import initialisierung.BodesuriServer;

import java.util.List;
import java.util.Vector;

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
			Botsuri b = new Botsuri(namen.get(i), "localhost", 7788, spielplatz.Stupidbot.class, false);

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
