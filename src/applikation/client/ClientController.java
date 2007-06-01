package applikation.client;

import java.util.Observable;

import javax.swing.JOptionPane;

import pd.Spiel;
import pd.brett.Feld;
import pd.karten.Karte;
import pd.spieler.Spieler;
import ui.lobby.LobbyView;
import ui.spiel.BodesuriView;
import ui.verbinden.VerbindenView;
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
 * die logischen Schritte, die in diesem Zustand ausgeführt werden.
 * Für die grafische Darstellung ist die <code>ui</code>-Schicht zuständig. Dort
 * sind ebenfalls Observer implementiert.
 *
 */
public class ClientController extends Observable {
	// Logik
	private EventQueue eventQueue;
	private ZugAutomatController zugAutomatController;
	public ZugAutomat zugAutomat;
	
	// Views
	private BodesuriView spielView;			
	private VerbindenView verbindenView;
	private LobbyView lobbyView;
	
	// Spiel
	private Spiel spiel;
	private String defaultName;				
	private String spielerName;
	private Spieler spielerIch;
	
	
	public ClientController(EventQueue eventQueue, String defaultName) {
		this.eventQueue = eventQueue;
		this.defaultName = defaultName;
	}
	
	public static void main(String[] args) {
		EventQueue queue = new EventQueue();
		ClientController controller = new ClientController(queue, "Spieler");
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
	
	/**
	 * Programm wurde gestartet.
	 */
	public void zeigeProgrammStart() {
		verbindenView = new VerbindenView(this, defaultName);
		verbindenView.setVisible(true);
	}
	
	/**
	 * Definition wer am Zug ist.
	 */
	public void zeigeAktuellenZug() {
		// noch nichts
	}

	/**
	 * Der Spieler kann eine Karte auswählen.
	 */
	public void zeigeKarteWahelen() {
		// aktiver Zustand
	}

	/**
	 * Der Spieler befindet sich in der Lobby.
	 */
	public void zeigeLobby() {
		// aktiver Zustand
	}

	/**
	 * Zeigt die Lobby an.
	 */
	public void zeigeLobbyStart() {
		verbindenView.setVisible(false);
		lobbyView = new LobbyView();
		lobbyView.setVisible(true);
	}

	/**
	 * Der Spieler ist nicht am Zug.
	 */
	public void zeigeNichtAmZug() {
		// aktiver Zustand
	}

	/**
	 * Ein schwerer Fehler ist aufgetreten. Eine entsprechende Darstellung des
	 * Ausnahmezustandes kann hier durchgeführt werden.
	 */
	public void zeigeSchwerenFehler() {
		// noch nichts
	}

	/**
	 * Das spiel wurde gestartet.
	 */
	public void zeigeSpielStart() {
		lobbyView.setVisible(false);
		spielView = new BodesuriView(this, defaultName);	// TODO: richtiger Spielername übergeben
		spielView.setVisible(true);
	}

	/**
	 * Das Spiel befindet sich im Zustand, in dem die Verbindungsdaten erfasst werden.
	 */
	public void zeigeVerbindungErfassen() {
		// noch nichts (ausser Fehlermeldungen)
	}

	/**
	 * Eine Verbindung wurde hergestellt. Der Client ist mit dem Server verbunden.
	 */
	public void zeigeVerbindungSteht() {
		// noch nichts
	}

	/**
	 * Es wird ein Zug erfasst und ausgeführt.
	 */
	public void zeigeStartWaehlen() {
		// aktiver Zustand
	}
	
	/**
	 * Reaktion auf Fehlermeldungen, die vom Automaten an den Controller gereicht werden.
	 */
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
	
	public void setSpiel(Spiel spiel) {
		this.spiel = spiel;
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
