package applikation.client;

import java.util.HashMap;
import java.util.Map;

import pd.Spiel;
import pd.brett.Feld;
import pd.karten.Karte;
import pd.spieler.Spieler;
import applikation.events.FeldGewaehltEvent;
import applikation.events.KarteGewaehltEvent;
import applikation.events.VerbindeEvent;
import dienste.eventqueue.EventQueue;

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
	protected Map<Spieler, applikation.client.Spieler> spielers = new HashMap<Spieler, applikation.client.Spieler>();
	protected applikation.client.Spieler aktuellerSpieler;

	public Controller(String spielerName) {
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

	/**
	 * Einen Spieler dem {@link Spiel} hinzufügen. Ausserdem wird der Spieler
	 * noch mit einem clientspezifischen
	 * {@link applikation.client.Spieler Spieler} assoziiert um zu speichern wer
	 * am Zug ist.
	 *
	 * @param name
	 *            Spielername
	 */
	public void fuegeSpielerHinzu(String name) {
		Spieler neuerSpieler = spiel.fuegeHinzu(name);
		if (name.equals(spielerName)) {
			spielerIch = neuerSpieler;
		}

		spielers.put(neuerSpieler, new applikation.client.Spieler());
	}

	/**
	 * Den Spieler der am Zug ist ändern.
	 *
	 * @param spieler
	 *            Neuer Spieler der am Zug ist.
	 */
	public void amZug(Spieler spieler) {
		applikation.client.Spieler neuerSpieler = spielers.get(spieler);
		aktuellerSpieler.setAmZug(false);
		neuerSpieler.setAmZug(true);
		aktuellerSpieler = neuerSpieler;
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

	public Spieler getSpielerIch() {
		return spielerIch;
	}

	public void setEventQueue(EventQueue queue) {
		this.eventQueue = queue;
    }

	public applikation.client.Spieler getSpieler(Spieler spieler) {
		return spielers.get(spieler);
	}
}
