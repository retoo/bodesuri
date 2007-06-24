package spielplatz;

import initialisierung.BodesuriServer;

import java.util.Vector;

public class BodesuriArena {
	public static void main(String[] args) throws InterruptedException {
		BodesuriServer server = new BodesuriServer();
		server.start();
		server.warteAufBereitschaft();

		Vector<Thread> clients = new Vector<Thread>();

		Vector<String> nicks = new Vector<String>();
		nicks.add("Anna Navarre ");
		nicks.add("JC Denton");
		nicks.add("Joseph Manderley");
		nicks.add("Walton Simons");

		for (int i = 0; i < 4; i++) {
			Thread t = new Botsuri(nicks.get(i), "localhost", 7788, Stupidbot.class, false);

			t.start();
			clients.add(t);
		}

		server.join();

		for (Thread t : clients) {
			t.join();
		}

		System.out.println("Alles fertig");
	}
}
