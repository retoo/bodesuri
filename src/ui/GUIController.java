package ui;

import javax.swing.JOptionPane;

import ui.lobby.LobbyView;
import ui.spiel.BodesuriView;
import ui.verbinden.VerbindenView;
import applikation.client.BodesuriClient;
import applikation.client.Controller;
import dienste.automat.Automat;
import dienste.automat.EventQueue;

public class GUIController extends Controller {
	private VerbindenView verbindenView;
	private LobbyView lobbyView;
	private BodesuriView spielView;

	public GUIController(EventQueue eventQueue, String spielerName) {
		super(eventQueue, spielerName);
	}

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

	public void zeigeVerbinden() {
		verbindenView = new VerbindenView(this, spielerName);
		verbindenView.setVisible(true);
	}

	public void zeigeLobby() {
		verbindenView.setVisible(false);
		lobbyView = new LobbyView();
		lobbyView.setVisible(true);
	}

	public void zeigeSpiel() {
		lobbyView.setVisible(false);
		spielView = new BodesuriView(this, spielerName);
		spielView.setVisible(true);
	}

	public void zeigeFehlermeldung(String fehlermeldung) {
		JOptionPane.showMessageDialog(null, fehlermeldung);
	}
}