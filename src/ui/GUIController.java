package ui;

import java.util.List;

import javax.swing.JOptionPane;

import ui.lobby.LobbyView;
import ui.spiel.BodesuriView;
import ui.verbinden.VerbindenView;
import applikation.client.controller.Controller;
import applikation.client.pd.Spiel;
import applikation.client.pd.Spieler;
import dienste.eventqueue.EventQueue;

public class GUIController extends Controller {
	private VerbindenView verbindenView;
	private LobbyView lobbyView;
	private BodesuriView spielView;

	public GUIController(EventQueue eventQueue) {
		this.eventQueue = eventQueue;
	}

	public void zeigeVerbinden(String spielerName) {
		verbindenView = new VerbindenView(this, spielerName);
		verbindenView.setVisible(true);
	}

	public void zeigeLobby(List<Spieler> spieler) {
		verbindenView.setVisible(false);
		lobbyView = new LobbyView(spieler);
		lobbyView.setVisible(true);
	}

	public void zeigeSpiel(Spiel spiel) {
		lobbyView.setVisible(false);
		spielView = new BodesuriView(this, spiel);
		spielView.setVisible(true);
	}

	public void zeigeFehlermeldung(String fehlermeldung) {
		JOptionPane.showMessageDialog(null, fehlermeldung);
	}
}
