package initialisierung;

import ui.GUIController;
import applikation.client.ClientAutomat;
import applikation.client.controller.Controller;
import applikation.client.konfiguration.DefaultKonfiguration;
import dienste.eventqueue.EventQueue;
import dienste.threads.BodesuriThread;


public class Bodesuri extends BodesuriThread {
	private DefaultKonfiguration konfiguration;

	public Bodesuri() {
		this(new DefaultKonfiguration());
    }

	public Bodesuri(DefaultKonfiguration konfiguration) {
		super("Bodesuri Client " + konfiguration.defaultName);
		this.konfiguration = konfiguration;
    }

	public void run() {
		EventQueue queue = new EventQueue();
		Controller controller = new GUIController(queue, konfiguration);
		ClientAutomat automat = new ClientAutomat(controller, queue);
		automat.run();
	}

	/**
	 * Applikationsstart des Clients. Benötigte Instanzen werden erstellt,
	 * Client wird initialisiert und gestartet.
	 * @param args	Wird nicht genutzt
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		Bodesuri bodesuri = new Bodesuri();
		bodesuri.start();
		bodesuri.join();
	}
}
