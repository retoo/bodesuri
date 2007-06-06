package ui;

import javax.swing.JOptionPane;

import ui.lobby.LobbyView;
import ui.spiel.BodesuriView;
import ui.verbinden.VerbindenView;
import applikation.client.Controller;
import dienste.automat.EventQueue;

public class GUIController extends Controller {
	private VerbindenView verbindenView;
	private LobbyView lobbyView;
	private BodesuriView spielView;

	public GUIController(EventQueue eventQueue, String spielerName) {
		super(eventQueue, spielerName);
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

    public void feldAuswahl(Boolean aktiv) {
	    // TODO Die Auwahl von Feldern (MouseListener) an-/ausschalten.
    }

    public void kartenAuswahl(Boolean aktiv) {
	    // TODO Die Auwahl von Karten (MouseListener) an-/ausschalten.
    }
}
