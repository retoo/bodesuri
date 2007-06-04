package applikation.client;

import pd.Spiel;
import pd.brett.Feld;
import pd.karten.Karte;
import pd.spieler.Spieler;
import applikation.client.zugautomat.ZugAutomat;
import applikation.client.zugautomat.zustaende.EndeWaehlen;
import applikation.client.zugautomat.zustaende.KarteWaehlen;
import applikation.client.zugautomat.zustaende.StartWaehlen;
import applikation.events.FeldGewaehltEvent;
import applikation.events.KarteGewaehltEvent;
import applikation.events.VerbindeEvent;
import dienste.automat.EventQueue;

public abstract class Controller {
	// Logik
	protected EventQueue eventQueue;
	protected ZugAutomatController zugAutomatController;
	protected ZugAutomat zugAutomat;

	// Spiel
	protected Spiel spiel;
	protected String spielerName;
	protected Spieler spielerIch;

	public Controller(EventQueue eventQueue, String spielerName) {
		this.eventQueue = eventQueue;
		this.spielerName = spielerName;
		this.spiel = new Spiel();
	}

	/**
	 * Programm wurde gestartet. Verbindungsdaten erfragen.
	 */
	public abstract void zeigeVerbinden();

	/**
	 * Die Verbinung wurde erstellt. Die Lobby anzeigen.
	 */
	public abstract void zeigeLobby();

	/**
	 * Das Spiel wurde gestartet. Das Spielbrett anzeigen.
	 */
	public abstract void zeigeSpiel();

	/**
	 * Reaktion auf Fehlermeldungen, die vom Automaten an den Controller
	 * gereicht werden.
	 * 
	 * @param fehlermeldung
	 *            auszugebene Fehlermeldung
	 */
	public abstract void zeigeFehlermeldung(String fehlermeldung);

	/**
	 * Mit dem erfassen eines neuen Zuges beginnen. Startet einen eigenen
	 * Automaten für die Zugerfassung.
	 * 
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

	/**
	 * Verdindung zum Server aufbauen.
	 * 
	 * @param host
	 *            Hostname des Servers
	 * @param port_raw
	 *            Port des Servers
	 * @param spieler
	 *            Name des Spielers
	 */
	public void verbinde(String host, int port_raw, String spieler) {
		VerbindeEvent e = new VerbindeEvent(host, port_raw, spieler);
		eventQueue.enqueue(e);
	}

	/**
	 * Der Benutzer hat eine Karte ausgewählt.
	 * 
	 * @param gewaehlteKarte
	 */
	public void karteGewaehlt(Karte gewaehlteKarte) {
		if (zugAutomat.isZustand(KarteWaehlen.class)
		    || zugAutomat.isZustand(StartWaehlen.class)
		    || zugAutomat.isZustand(EndeWaehlen.class)) {
			KarteGewaehltEvent kge = new KarteGewaehltEvent(gewaehlteKarte);
			zugAutomat.queue.enqueue(kge);
		}
	}

	/**
	 * Der Benutzer hat ein Feld ausgewählt.
	 * 
	 * @param gewaehltesFeld
	 */
	public void feldGewaehlt(Feld gewaehltesFeld) {
		if (zugAutomat.isZustand(StartWaehlen.class)
		    || zugAutomat.isZustand(EndeWaehlen.class)) {
			zugAutomat.queue.enqueue(new FeldGewaehltEvent(gewaehltesFeld));
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
