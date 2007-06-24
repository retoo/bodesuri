package ui;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import ui.lobby.LobbyView;
import ui.spiel.SpielView;
import ui.spiel.brett.JokerView;
import ui.verbinden.VerbindenView;
import applikation.client.controller.Controller;
import applikation.client.konfiguration.Konfiguration;
import applikation.client.pd.Chat;
import applikation.client.pd.Spiel;
import applikation.client.pd.Spieler;
import dienste.eventqueue.EventQueue;

public class GUIController extends Controller {
	private VerbindenView verbindenView;
	private LobbyView lobbyView;
	private SpielView spielView;
	private Konfiguration konfiguration;
	private JokerView jokerView;

	public GUIController(EventQueue eventQueue, Konfiguration konfiguration) {
		this.konfiguration = konfiguration;
		this.eventQueue = eventQueue;
		setNativeLookAndFeel();
	}

	public void zeigeVerbinden() {
		verbindenView = new VerbindenView(this, konfiguration);
		verbindenView.setVisible(true);
	}

	public void zeigeLobby(List<Spieler> spieler, Chat chat) {
		verbindenView.setVisible(false);
		lobbyView = new LobbyView(spieler, this, chat);
		lobbyView.setVisible(true);
	}

	public void zeigeSpiel(Spiel spiel) {
		// Warte 3 Sekunden, damit alle auch den zuletzt beigetretenen Spieler
		// noch in der Lobby sehen.
		try {
			if (!konfiguration.debugKeineLobbyVerzoegerung)
				Thread.sleep(3000);
		} catch (InterruptedException e) {
			throw new RuntimeException("Das Warten in der Lobby wurde durch " +
					"einen anderen Thread unterbrochen.");
		}
		lobbyView.setVisible(false);
		lobbyView.dispose();
		spielView = new SpielView(this, spiel);
		spielView.setVisible(true);
		jokerView = new JokerView(this);
		spielView.setGlassPane(jokerView);
	}

	public void zeigeJokerauswahl(boolean aktiv) {
		jokerView.setVisible(aktiv);
	}

	public void zeigeFehlermeldung(String fehlermeldung) {
		JOptionPane.showMessageDialog(null, fehlermeldung);
	}

    public void zeigeMeldung(String meldung) {
    	JOptionPane.showMessageDialog(null, meldung);
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
	/**
	 * Setzt den nativen Look & Feel für Windows. Auf allen anderen Plattformen
	 * wird eine Exception geworfen, die ignoriert wird.
	 */
	private static void setNativeLookAndFeel() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
		}
	}
}
