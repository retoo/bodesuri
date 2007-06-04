package ui;

import java.util.Observable;

import javax.swing.JOptionPane;

import pd.Spiel;
import pd.brett.Feld;
import pd.karten.Karte;
import pd.spieler.Spieler;
import ui.lobby.LobbyView;
import ui.spiel.BodesuriView;
import ui.verbinden.VerbindenView;
import applikation.client.BodesuriClient;
import applikation.client.Controller;
import applikation.client.ZugAutomatController;
import applikation.client.zugautomat.ZugAutomat;
import applikation.client.zugautomat.zustaende.EndeWaehlen;
import applikation.client.zugautomat.zustaende.KarteWaehlen;
import applikation.client.zugautomat.zustaende.StartWaehlen;
import applikation.events.FeldGewaehltEvent;
import applikation.events.KarteGewaehltEvent;
import applikation.events.VerbindeEvent;
import dienste.automat.Automat;
import dienste.automat.EventQueue;

/**
 * Der ClientController dient zur Zustandsverwaltung des Clients. Er handhabt
 * die logischen Schritte, die in diesem Zustand ausgeführt werden. Für die
 * grafische Darstellung ist die <code>ui</code>-Schicht zuständig. Dort sind
 * ebenfalls Observer implementiert.
 *
 */
public class GUIController extends Observable implements Controller {
	// Logik
	private EventQueue eventQueue;
	private ZugAutomatController zugAutomatController;
	private ZugAutomat zugAutomat;

	// Views
	private BodesuriView spielView;
	private VerbindenView verbindenView;
	private LobbyView lobbyView;

	// Spiel
	private Spiel spiel;
	private String defaultName;
	private String spielerName;
	private Spieler spielerIch;

	public GUIController(EventQueue eventQueue, String defaultName) {
		this.eventQueue = eventQueue;
		this.defaultName = defaultName;
		this.spiel = new Spiel();
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

	public void starteZugerfassung() {
		zugAutomatController = new ZugAutomatController();

		Thread zugErfassungThread = new Thread(new Runnable() {
			public void run() {
				zugAutomat = new ZugAutomat(spielerIch, eventQueue);
				zugAutomat.run();
			}
		});

		zugErfassungThread.start();
	}

	public void verbinde(String host, int port_raw, String spieler) {
		VerbindeEvent e = new VerbindeEvent(host, port_raw, spieler);
		eventQueue.enqueue(e);
	}

	public void zeigeProgrammStart() {
		verbindenView = new VerbindenView(this, defaultName);
		verbindenView.setVisible(true);
	}

	public void zeigeAktuellenZug() {
		// noch nichts
	}

	public void zeigeKarteWahelen() {
		// aktiver Zustand
	}

	public void zeigeLobby() {
		// aktiver Zustand
	}

	public void zeigeLobbyStart() {
		verbindenView.setVisible(false);
		lobbyView = new LobbyView();
		lobbyView.setVisible(true);
	}

	public void zeigeNichtAmZug() {
		// aktiver Zustand
	}

	public void zeigeSchwerenFehler() {
		// noch nichts
	}

	public void zeigeSpielStart() {
		lobbyView.setVisible(false);
		spielView = new BodesuriView(this, defaultName); // TODO: richtiger
															// Spielername
															// übergeben
		spielView.setVisible(true);
	}

	public void zeigeVerbindungErfassen() {
		// noch nichts (ausser Fehlermeldungen)
	}

	public void zeigeVerbindungSteht() {
		// noch nichts
	}

	public void zeigeStartWaehlen() {
		// aktiver Zustand
	}

	public void zeigeFehlermeldung(String fehlermeldung) {
		JOptionPane.showMessageDialog(verbindenView, fehlermeldung);
	}

	public void klickKarte(Karte geklickteKarte) {
		if (zugAutomat.isZustand(KarteWaehlen.class)
		    || zugAutomat.isZustand(StartWaehlen.class)
		    || zugAutomat.isZustand(EndeWaehlen.class)) {
			KarteGewaehltEvent kge = new KarteGewaehltEvent(geklickteKarte);
			zugAutomat.queue.enqueue(kge);
		}
	}

	public void klickFeld(Feld geklicktesFeld) {
		if (zugAutomat.isZustand(StartWaehlen.class)
		    || zugAutomat.isZustand(EndeWaehlen.class)) {
			zugAutomat.queue.enqueue(new FeldGewaehltEvent(geklicktesFeld));
		}
	}

	public Spiel getSpiel() {
		return spiel;
	}

	public String getSpielerName() {
		return spielerName;
	}

	public void setSpielerName(String spielerName) {
		this.spielerName = spielerName;
	}

	public void setSpielerIch(Spieler spielerIch) {
		this.spielerIch = spielerIch;
	}

	public boolean isZugAutomatControllerVorhanden() {
		return (zugAutomatController != null) ? true : false;
	}
}
