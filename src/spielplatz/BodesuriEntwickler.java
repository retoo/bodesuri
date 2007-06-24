package spielplatz;

import applikation.client.konfiguration.DefaultKonfiguration;
import initialisierung.Bodesuri;
import initialisierung.BodesuriServer;

public class BodesuriEntwickler {

	public static void main(String[] args) throws InterruptedException {
		BodesuriServer server = new BodesuriServer(2);
		server.start();
		server.warteAufBereitschaft();

		DefaultKonfiguration konfigA = new DefaultKonfiguration();
		konfigA.defaultName = "Christoph";
		konfigA.debugAutoLogin = true;

		DefaultKonfiguration konfigB = new DefaultKonfiguration();
		konfigB.defaultName = "Micheline";
		konfigB.debugAutoLogin = true;

		Bodesuri a = new Bodesuri(konfigA);
		Bodesuri b = new Bodesuri(konfigB);

		a.start();
		b.start();

		server.join();

		a.join();
		b.join();
	}
}
