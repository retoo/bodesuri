package spielplatz;

import initialisierung.BodesuriServer;

import java.util.Vector;

import dienste.eventqueue.EventQueue;

import ui.GUIController;

import applikation.bot.Botsuri;
import applikation.bot.Stupidbot;
import applikation.client.konfiguration.Konfiguration;

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
			Konfiguration konfig = new Konfiguration();
			konfig.defaultName = nicks.get(i);

			EventQueue queue = new EventQueue();
			GUIController guiController = new GUIController(queue, konfig);
			Thread t = new Botsuri(konfig, queue, guiController, Stupidbot.class, false);

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
