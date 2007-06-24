package spielplatz;

import initialisierung.Bodesuri;
import initialisierung.BodesuriServer;

public class BodesuriEntwickler {

	public static void main(String[] args) throws InterruptedException {
		BodesuriServer server = new BodesuriServer(2);
		server.start();
		server.warteAufBereitschaft();

		Bodesuri a = new Bodesuri("Christoph");
		a.start();
		Bodesuri b = new Bodesuri("Micheline");
		b.start();

		server.join();

		a.join();
		b.join();
	}
}
