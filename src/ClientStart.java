import ui.GUIController;
import applikation.client.ClientAutomat;
import applikation.client.controller.Controller;


public class ClientStart {

	/**
	 * Applikationsstart des Clients. Benötigte Instanzen werden erstellt,
	 * Client wird initialisiert und gestartet.
	 * @param args	Wird nicht genutzt
	 */
	public static void main(String[] args) {
		Controller controller = new GUIController("Spieler");
		ClientAutomat automat = new ClientAutomat(controller);

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
