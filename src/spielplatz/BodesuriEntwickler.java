package spielplatz;

import applikation.client.konfiguration.Konfiguration;
import initialisierung.Bodesuri;
import initialisierung.BodesuriServer;

public class BodesuriEntwickler {

	public static void main(String[] args) throws InterruptedException {
		BodesuriServer server = new BodesuriServer(2);
		server.start();
		server.warteAufBereitschaft();

		Konfiguration konfigA = new Konfiguration();
		konfigA.defaultName = "Christoph";
		konfigA.debugAutoLogin = true;
		konfigA.debugKeineLobbyVerzoegerung = true;

		Konfiguration konfigB = new Konfiguration();
		konfigB.defaultName = "Micheline";
		konfigB.debugAutoLogin = true;
		konfigB.debugKeineLobbyVerzoegerung = true;

		Bodesuri a = new Bodesuri(konfigA);
		Bodesuri b = new Bodesuri(konfigB);

		a.start();
		b.start();

		server.join();

		a.join();
		b.join();
	}
}
