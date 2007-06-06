package applikation.client;

import pd.Spiel;
import pd.brett.Feld;
import pd.karten.Karte;
import pd.spieler.Spieler;
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
	 * Die Verbindungsdaten erfragen.
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
	 * Die Auswahl von Feldern (de-)aktivieren.
	 * 
	 * @param aktiv
	 */
	public abstract void feldAuswahl(Boolean aktiv);

	/**
	 * Die Auswahl von Karten (de-)aktivieren.
	 * 
	 * @param aktiv
	 */
	public abstract void kartenAuswahl(Boolean aktiv);

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
		KarteGewaehltEvent kge = new KarteGewaehltEvent(gewaehlteKarte);
		eventQueue.enqueue(kge);
	}

	/**
	 * Dem Automaten mitteilen welches Feld der Benutzer gewählt hat.
	 * 
	 * @param gewaehltesFeld
	 */
	public void feldGewaehlt(Feld gewaehltesFeld) {
		FeldGewaehltEvent fge = new FeldGewaehltEvent(gewaehltesFeld);
		eventQueue.enqueue(fge);
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

	public Spieler getSpielerIch() {
		return spielerIch;
	}
}
