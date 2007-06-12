package applikation.client.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import pd.Spiel;
import pd.brett.Feld;
import pd.karten.Karte;
import pd.spieler.Spieler;
import applikation.events.AufgegebenEvent;
import applikation.events.FeldGewaehltEvent;
import applikation.events.HoverStartEvent;
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
	private Spiel spiel;
	private String spielerName;
	private Spieler spielerIch;
	//FIXME: HashMap ist nicht gut. Reihenfolge ist nicht garantiert.
	private Map<Spieler, applikation.client.controller.Spieler> spielers =
		new HashMap<Spieler, applikation.client.controller.Spieler>();
	private applikation.client.controller.Spieler aktuellerSpieler;

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
	 * Darstellen des abgewählten Feldes.
	 * @param abgewaehltesFeld
	 */
	public abstract void feldAbwaehlen(Feld abgewaehltesFeld);

	/**
	 * Darstellung des gewählten Feldes.
	 * @param gewaehltesFeld
	 */
	public abstract void feldWaehlen(Feld gewaehltesFeld);
	
	/**
	 * Einen Spieler dem {@link Spiel} hinzufügen. Ausserdem wird der Spieler
	 * noch mit einem clientspezifischen
	 * {@link applikation.client.controller.Spieler Spieler} assoziiert um zu speichern wer
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

		//TODO: Bestimmen der Farbe des Spielers (wird vom Server kommen).
		spielers.put(neuerSpieler, new applikation.client.controller.Spieler(neuerSpieler, SpielerFarbe.rot));
	}

	/**
	 * Den Spieler der am Zug ist ändern.
	 *
	 * @param spieler
	 *            Neuer Spieler der am Zug ist.
	 */
	public void amZug(Spieler spieler) {
		applikation.client.controller.Spieler neuerSpieler = spielers.get(spieler);
		if (aktuellerSpieler != null) {
			aktuellerSpieler.setAmZug(false);	
		}
		neuerSpieler.setAmZug(true);
		aktuellerSpieler = neuerSpieler;
	}

	/**
	 * Wenn man keine Karten mehr spielen kann. Noch nicht sicher ob dies auch
	 * im definitven Spiel drin ist...
	 */
	public void aufgeben() {
		if (spielerIch.kannZiehen()) {
			zeigeFehlermeldung("Es kann noch nicht aufgegeben werden, " +
                               "da es noch möglich ist zu ziehen.");
			return;
		}
		kartenAuswahl(false);
		AufgegebenEvent age = new AufgegebenEvent();
		eventQueue.enqueue(age);
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

	public void zielHover(Feld feld) {
		HoverStartEvent hve = new HoverStartEvent(feld);
		eventQueue.enqueue(hve);
    }

	public Collection<applikation.client.controller.Spieler> getSpielers() {
    	return spielers.values();
    }
}