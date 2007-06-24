package initialisierung;
import applikation.server.ServerAutomat;


public class BodesuriServer implements Runnable {
	private ServerAutomat server;
	private static final int VERZOEGERUNG = 100;
	private static final int VERSUCHE = 20;

	public void run() {
		server = new ServerAutomat();

		try {
			server.run();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Server: Exception in run()");
			System.exit(99);
		}
	}

	public void warteAufBereitschaft() {
		boolean serverBereit = false;
		for (int i = 0; i < VERSUCHE; i++) {

			try {
	            Thread.sleep(VERZOEGERUNG);
            } catch (InterruptedException e) {
            	throw new RuntimeException(e);
            }

			if (server.istBereitFuerSpieler()) {
				serverBereit = true;
				break;
			}
		}

		if (!serverBereit) {
			int pruefdauer = (VERSUCHE * VERZOEGERUNG) / 100;
			throw new RuntimeException("Server wurde nicht innert " + pruefdauer + " Sekunden bereit");
		}
    }

	/**
	 * Applikationsstart des Servers. Benötigte Instanzen werden erstellt,
	 * Server wird initialisiert und gestartet.
	 * @param args	Wird nicht genutzt
	 */
	public static void main(String[] args) {
		BodesuriServer server = new BodesuriServer();

		server.run();
	}
}
