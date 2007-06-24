package spielplatz;

import initialisierung.Bodesuri;
import initialisierung.BodesuriServer;

public class BodesuriSingular {

	public static void main(String[] args) throws InterruptedException {
		BodesuriServer server = new BodesuriServer(1);
		server.start();
		server.warteAufBereitschaft();

		Bodesuri a = new Bodesuri("Huere Michi");
		a.start();

		a.join();
	}
}
