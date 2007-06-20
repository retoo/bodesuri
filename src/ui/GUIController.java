package ui;

import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;

import ui.lobby.LobbyView;
import ui.spiel.SpielView;
import ui.verbinden.VerbindenView;
import applikation.client.controller.Controller;
import applikation.client.pd.Chat;
import applikation.client.pd.Spiel;
import applikation.client.pd.Spieler;
import applikation.geteiltes.SpielerInfo;
import dienste.eventqueue.EventQueue;

public class GUIController extends Controller {
	private VerbindenView verbindenView;
	private LobbyView lobbyView;
	private SpielView spielView;

	public GUIController(EventQueue eventQueue) {
		this.eventQueue = eventQueue;
	}

	public void zeigeVerbinden(String spielerName) {
		verbindenView = new VerbindenView(this, spielerName);
		verbindenView.setVisible(true);
	}

	public void zeigeLobby(List<Spieler> spieler, Chat chat) {
		verbindenView.setVisible(false);
		lobbyView = new LobbyView(spieler, this, chat);
		lobbyView.setVisible(true);
	}

	public void zeigeSpiel(Spiel spiel) {
		lobbyView.setVisible(false);
		lobbyView.dispose();
		spielView = new SpielView(this, spiel);
		spielView.setVisible(true);
	}

	public void zeigeFehlermeldung(String fehlermeldung) {
		JOptionPane.showMessageDialog(null, fehlermeldung);
	}
	
	public void beenden() {
		if (verbindenView != null) {
			verbindenView.setVisible(false);
			verbindenView.dispose();
		}

		if (lobbyView != null) {
			lobbyView.setVisible(false);
			lobbyView.dispose();
		}

		if (spielView != null) {
			spielView.setVisible(false);
			spielView.dispose();
		}

		super.beenden();
	}

	public void zeigeSpielerInLobby(Vector<SpielerInfo> spielers) {
		lobbyView.zeigeSpieler(spielers);
	}
}
