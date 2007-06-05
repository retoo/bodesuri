import applikation.server.BodesuriServer;


public class ServerStart {

	/**
	 * Applikationsstart des Servers. Benötigte Instanzen werden erstellt,
	 * Server wird initialisiert und gestartet.
	 * @param args	Wird nicht genutzt
	 */
	public static void main(String[] args) {
		BodesuriServer server = new BodesuriServer();

		try {
				server.run();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Server: Exception in run()");
			System.exit(99);
		}

		System.exit(0);
	}

}
