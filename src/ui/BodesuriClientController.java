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
public class BodesuriClientController extends Observable implements ClientController {
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

	public BodesuriClientController(EventQueue eventQueue, String defaultName) {
		this.eventQueue = eventQueue;
		this.defaultName = defaultName;
		this.spiel = new Spiel();
	}

	public static void main(String[] args) {
		EventQueue queue = new EventQueue();
		ClientController controller = new BodesuriClientController(queue, "Spieler");
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

	/* (non-Javadoc)
     * @see ui.ClientController#starteZugerfassung()
     */
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

	/* (non-Javadoc)
     * @see ui.ClientController#verbinde(java.lang.String, int, java.lang.String)
     */
	public void verbinde(String host, int port_raw, String spieler) {
		VerbindeEvent e = new VerbindeEvent(host, port_raw, spieler);
		eventQueue.enqueue(e);
	}

	/* (non-Javadoc)
     * @see ui.ClientController#zeigeProgrammStart()
     */
	public void zeigeProgrammStart() {
		verbindenView = new VerbindenView(this, defaultName);
		verbindenView.setVisible(true);
	}

	/* (non-Javadoc)
     * @see ui.ClientController#zeigeAktuellenZug()
     */
	public void zeigeAktuellenZug() {
		// noch nichts
	}

	/* (non-Javadoc)
     * @see ui.ClientController#zeigeKarteWahelen()
     */
	public void zeigeKarteWahelen() {
		// aktiver Zustand
	}

	/* (non-Javadoc)
     * @see ui.ClientController#zeigeLobby()
     */
	public void zeigeLobby() {
		// aktiver Zustand
	}

	/* (non-Javadoc)
     * @see ui.ClientController#zeigeLobbyStart()
     */
	public void zeigeLobbyStart() {
		verbindenView.setVisible(false);
		lobbyView = new LobbyView();
		lobbyView.setVisible(true);
	}

	/* (non-Javadoc)
     * @see ui.ClientController#zeigeNichtAmZug()
     */
	public void zeigeNichtAmZug() {
		// aktiver Zustand
	}

	/* (non-Javadoc)
     * @see ui.ClientController#zeigeSchwerenFehler()
     */
	public void zeigeSchwerenFehler() {
		// noch nichts
	}

	/* (non-Javadoc)
     * @see ui.ClientController#zeigeSpielStart()
     */
	public void zeigeSpielStart() {
		lobbyView.setVisible(false);
		spielView = new BodesuriView(this, defaultName); // TODO: richtiger
															// Spielername
															// übergeben
		spielView.setVisible(true);
	}

	/* (non-Javadoc)
     * @see ui.ClientController#zeigeVerbindungErfassen()
     */
	public void zeigeVerbindungErfassen() {
		// noch nichts (ausser Fehlermeldungen)
	}

	/* (non-Javadoc)
     * @see ui.ClientController#zeigeVerbindungSteht()
     */
	public void zeigeVerbindungSteht() {
		// noch nichts
	}

	/* (non-Javadoc)
     * @see ui.ClientController#zeigeStartWaehlen()
     */
	public void zeigeStartWaehlen() {
		// aktiver Zustand
	}

	/* (non-Javadoc)
     * @see ui.ClientController#zeigeFehlermeldung(java.lang.String)
     */
	public void zeigeFehlermeldung(String fehlermeldung) {
		JOptionPane.showMessageDialog(verbindenView, fehlermeldung);
	}

	/* (non-Javadoc)
     * @see ui.ClientController#klickKarte(pd.karten.Karte)
     */
	public void klickKarte(Karte geklickteKarte) {
		if (zugAutomat.isZustand(KarteWaehlen.class)
		    || zugAutomat.isZustand(StartWaehlen.class)
		    || zugAutomat.isZustand(EndeWaehlen.class)) {
			KarteGewaehltEvent kge = new KarteGewaehltEvent(geklickteKarte);
			zugAutomat.queue.enqueue(kge);
		}
	}

	/* (non-Javadoc)
     * @see ui.ClientController#klickFeld(pd.brett.Feld)
     */
	public void klickFeld(Feld geklicktesFeld) {
		if (zugAutomat.isZustand(StartWaehlen.class)
		    || zugAutomat.isZustand(EndeWaehlen.class)) {
			zugAutomat.queue.enqueue(new FeldGewaehltEvent(geklicktesFeld));
		}
	}

	/* (non-Javadoc)
     * @see ui.ClientController#getSpiel()
     */
	public Spiel getSpiel() {
		return spiel;
	}

	/* (non-Javadoc)
     * @see ui.ClientController#getSpielerName()
     */
	public String getSpielerName() {
		return spielerName;
	}

	/* (non-Javadoc)
     * @see ui.ClientController#setSpielerName(java.lang.String)
     */
	public void setSpielerName(String spielerName) {
		this.spielerName = spielerName;
	}

	/* (non-Javadoc)
     * @see ui.ClientController#setSpielerIch(pd.spieler.Spieler)
     */
	public void setSpielerIch(Spieler spielerIch) {
		this.spielerIch = spielerIch;
	}

	/* (non-Javadoc)
     * @see ui.ClientController#isZugAutomatControllerVorhanden()
     */
	public boolean isZugAutomatControllerVorhanden() {
		return (zugAutomatController != null) ? true : false;
	}
}
