package applikation.client.controller;

import java.util.List;
import java.util.Map;

import pd.Spiel;
import pd.brett.Feld;
import pd.karten.Karte;
import pd.spieler.Spieler;
import applikation.events.AufgegebenEvent;
import applikation.events.FeldAbgewaehltEvent;
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

	/**
	 * Die Verbindungsdaten erfragen.
	 * @param spielerName 
	 */
	public abstract void zeigeVerbinden(String spielerName);

	/**
	 * Die Lobby anzeigen.
	 * 
	 * @param spieler
	 *            Liste der Spieler
	 */
	public abstract void zeigeLobby(List<Spieler> spieler);

	/**
	 * Das Spielbrett anzeigen.
	 * @param spiel 
	 * @param spielerIch 
	 * @param spielers 
	 */
	public abstract void zeigeSpiel(Spiel spiel, Spieler spielerIch, Map<Spieler, applikation.client.pd.Spieler> spielers);

	/**
	 * Hervorhebung des Spielers, der an der Reihe ist.
	 * 
	 * @param spielerName zu markierender Spieler
	 */
	public abstract void zeigeSpielerAmZug(String spielerName);
	
	/**
	 * Fehlermeldungen, die vom Automaten an den Controller gereicht werden,
	 * darstellen.
	 * 
	 * @param fehlermeldung
	 *            anzuzeigende Fehlermeldung
	 */
	public abstract void zeigeFehlermeldung(String fehlermeldung);
	
	/**
	 * Hinweise, die vom Automaten an den Controller gereicht werden, darstellen
	 * um dem Spieler zu führen.
	 * 
	 * @param hinweis
	 *            anzuzeigender Hinweis
	 */
	public abstract void zeigeHinweis(String hinweis);
	
	/**
	 * Die gespielte Karte, die vom Automaten an den Controller gereicht wird,
	 * darstellen.
	 * 
	 * @param karte
	 *            gespielte Karte
	 */
	public abstract void zeigeGespielteKarte(String karte);
	
	/**
	 * Darstellen des gewählten bzw. abgewählten Feldes.
	 * @param abgewaehltesFeld
	 * @param status
	 */
	public abstract void zeigeFeldauswahl(Feld abgewaehltesFeld, boolean status);

	/**
	 * Die Auswahl von Feldern (de-)aktivieren.
	 *
	 * @param aktiv
	 */
	public abstract void aktiviereFeld(Boolean aktiv);

	/**
	 * Die Auswahl von Karten (de-)aktivieren.
	 *
	 * @param aktiv
	 */
	public abstract void aktiviereKarte(Boolean aktiv);

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
		VerbindeEvent ve = new VerbindeEvent(host, port_raw, spieler);
		eventQueue.enqueue(ve);
	}

	/**
	 * Dem Automaten mitteilen welche Karte der Benutzer gewählt hat.
	 *
	 * @param gewaehlteKarte
	 */
	public void karteAuswaehlen(Karte gewaehlteKarte) {
		KarteGewaehltEvent kge = new KarteGewaehltEvent(gewaehlteKarte);
		eventQueue.enqueue(kge);
	}

	/**
	 * Dem Automaten mitteilen welches Feld der Benutzer gewählt hat.
	 *
	 * @param gewaehltesFeld
	 */
	public void feldAuswaehlen(Feld gewaehltesFeld) {
		FeldGewaehltEvent fge = new FeldGewaehltEvent(gewaehltesFeld);
		eventQueue.enqueue(fge);
	}

	/**
	 * Dem Automaten mitteilen, dass das ausgewählte Feld wieder abgewählt
	 * werden soll.
	 *
	 */
	public void feldAbwaehlen() {
		FeldAbgewaehltEvent fage = new FeldAbgewaehltEvent();
		eventQueue.enqueue(fage);
	}

	/**
	 * Wenn man keine Karten mehr spielen kann. Noch nicht sicher ob dies auch
	 * im definitven Spiel drin ist...
	 */
	public void aufgeben() {
		AufgegebenEvent age = new AufgegebenEvent();
		eventQueue.enqueue(age);
	}
	
	public void zielHover(Feld feld) {
		HoverStartEvent hse = new HoverStartEvent(feld);
		eventQueue.enqueue(hse);
	}

	public void setEventQueue(EventQueue queue) {
		this.eventQueue = queue;
    }

}