import initialisierung.BodesuriBot;
import initialisierung.BodesuriServer;

import java.util.Vector;

import junit.framework.TestCase;
import applikation.bot.Stupidbot;
import applikation.client.konfiguration.Konfiguration;

/**
 * Bietet Tests zur Prüfung der Funktionalität des Servers.
 */


public class ServerTest extends TestCase {
	/**
	 * Testet den Server auf korrektes Funktionieren, indem ein Server
	 * und vier Endpunkte erstellt werden und ein Beitritt der Spieler
	 * simuliert wird.
	 * @throws InterruptedException
	 */
	public void testServer() throws InterruptedException {
		Vector<String> nicks = new Vector<String>();

		nicks.add("Anna Navarre ");
		nicks.add("JC Denton");
		nicks.add("Joseph Manderley");
		nicks.add("Walton Simons");

		Thread server = new BodesuriServer();
		server.start();

		Vector<BodesuriBot> clients = new Vector<BodesuriBot>();

		for (String nick : nicks) {
			Konfiguration konfig = new Konfiguration();
			konfig.defaultName = nick;
			konfig.debugKeineLobbyVerzoegerung = true;
			konfig.debugBotsZoegernNicht = true;

			BodesuriBot t = new BodesuriBot(konfig, Stupidbot.class, false);

			t.start();
			clients.add(t);
		}

		server.join();
		System.out.println("Server fertig");


		for (BodesuriBot t : clients) {
			t.join();
			System.out.println("Client fertig " + t.getAnzahlZuege());
		}
	}
}
