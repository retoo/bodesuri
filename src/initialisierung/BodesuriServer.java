package initialisierung;

import applikation.server.ServerAutomat;
import dienste.threads.BodesuriThread;

/**
 * Applikationsstart des Servers. Benötigte Instanzen werden erstellt, Server
 * wird initialisiert und gestartet.
 */
public class BodesuriServer extends BodesuriThread {
	private ServerAutomat server;
	private int anzahlSpieler = 4;
	private static final int VERZOEGERUNG = 100;
	private static final int VERSUCHE = 55;

	public BodesuriServer() {
		super("Bodesuri Server");
	}

	public BodesuriServer(int anzahlSpieler) {
		this();

		this.anzahlSpieler = anzahlSpieler;
	}

	public void run() {
		server = new ServerAutomat(anzahlSpieler, false);
		server.run();
	}

	/**
	 * Blockiert bis der Server bereit ist.
	 */
	public void warteAufBereitschaft() {
		boolean serverBereit = false;
		for (int i = 0; i < VERSUCHE; i++) {

			try {
				Thread.sleep(VERZOEGERUNG);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}

			if (server != null && server.istBereitFuerSpieler()) {
				serverBereit = true;
				break;
			}
		}

		if (!serverBereit) {
			int pruefdauer = (VERSUCHE * VERZOEGERUNG) / 1000;
			System.out.println("ERROR: Server startete nicht innert "
			                   + pruefdauer + " Sekunden.");
			System.exit(99);
		}
	}

	/**
	 * Den Server starten.
	 *
	 * @param args
	 *            Wird nicht genutzt
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		BodesuriServer server = new BodesuriServer();

		server.start();
		server.join();
	}
}
