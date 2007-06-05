import ui.GUIController;
import applikation.client.BodesuriClient;
import applikation.client.Controller;
import dienste.automat.Automat;
import dienste.automat.EventQueue;


public class ClientStart {

	/**
	 * Applikationsstart des Clients. Benötigte Instanzen werden erstellt, 
	 * Client wird initialisiert und gestartet.
	 * @param args	Wird nicht genutzt
	 */
	public static void main(String[] args) {
		EventQueue queue = new EventQueue();
		Controller controller = new GUIController(queue, "Spieler");
		Automat automat = new BodesuriClient(queue, controller);

		try {
			automat.run();
		} catch (Exception e) {
			/* Applikation stoppen wenn ein Fehler auftritt */
			e.printStackTrace();
			System.out.println("Client: Exception in run()");
			System.exit(99);
		}
	}

}
