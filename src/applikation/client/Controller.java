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

/**
 * Der Controller dient zum Kanalisieren der Zugriffe zwischen der UI- und
 * Applikationsschicht. Er bietet dem UI Methoden Ereignisse an den Automaten
 * weiterzuleiten und dem Automaten die Möglichkeit UI-Ereignisse unabhängig von
 * der Implementation des UIs auszulösen.
 */
public abstract class Controller {
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
	 * Die Verbindungsdaten zu erfragen.
	 */
	public abstract void zeigeVerbinden();

	/**
	 * Die Lobby anzeigen.
	 */
	public abstract void zeigeLobby();

	/**
	 * Das Spielbrett anzeigen.
	 */
	public abstract void zeigeSpiel();

	/**
	 * Fehlermeldungen, die vom Automaten an den Controller gereicht werden
	 * darstellen.
	 * 
	 * @param fehlermeldung
	 *            auszugebene Fehlermeldung
	 */
	public abstract void zeigeFehlermeldung(String fehlermeldung);

	/**
	 * Mit dem Erfassen eines neuen Zuges beginnen. Startet einen eigenen
	 * Automaten für die Zugerfassung.
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
	 * Dem Automaten auftragen eine Verbindung zum Server aufzubauen.
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
	 * Dem Automaten mitteilen welche Karte der Benutzer gewählt hat.
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
	 * Dem Automaten mitteilen welches Feld der Benutzer gewählt hat.
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
}
