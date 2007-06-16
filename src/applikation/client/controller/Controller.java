package applikation.client.controller;

import java.util.List;

import dienste.eventqueue.EventQueue;


import applikation.client.pd.Feld;
import applikation.client.pd.Karte;
import applikation.client.pd.Spiel;
import applikation.client.pd.Spieler;
import applikation.events.AufgegebenEvent;
import applikation.events.FeldAbgewaehltEvent;
import applikation.events.FeldGewaehltEvent;
import applikation.events.HoverEndeEvent;
import applikation.events.HoverStartEvent;
import applikation.events.KarteGewaehltEvent;
import applikation.events.VerbindeEvent;

/**
 * Der Controller dient zum Kanalisieren der Zugriffe zwischen der UI- und
 * Applikationsschicht. Er bietet dem UI Methoden Ereignisse an den Automaten
 * weiterzuleiten und dem Automaten die Möglichkeit UI-Ereignisse unabhängig von
 * der Implementation des UIs auszulösen.
 */
public abstract class Controller implements Steuerung {
	protected EventQueue eventQueue;
	/**
	 * Die Verbindungsdaten erfragen.
	 *
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
	 *
	 * @param spiel
	 * @param spielerIch
	 */
	public abstract void zeigeSpiel(Spiel spiel, Spieler spielerIch);

	/**
	 * Fehlermeldungen, die vom Automaten an den Controller gereicht werden,
	 * darstellen.
	 *
	 * @param fehlermeldung
	 *            anzuzeigende Fehlermeldung
	 */
	public abstract void zeigeFehlermeldung(String fehlermeldung);

	public void verbinde(String host, int port_raw, String spieler) {
		VerbindeEvent ve = new VerbindeEvent(host, port_raw, spieler);
		eventQueue.enqueue(ve);
	}

	public void karteAuswaehlen(Karte gewaehlteKarte) {
		KarteGewaehltEvent kge = new KarteGewaehltEvent(gewaehlteKarte);
		eventQueue.enqueue(kge);
	}

	public void feldAuswaehlen(Feld gewaehltesFeld) {
		FeldGewaehltEvent fge = new FeldGewaehltEvent(gewaehltesFeld);
		eventQueue.enqueue(fge);
	}

	public void feldAbwaehlen() {
		FeldAbgewaehltEvent fage = new FeldAbgewaehltEvent();
		eventQueue.enqueue(fage);
	}

	public void aufgeben() {
		AufgegebenEvent age = new AufgegebenEvent();
		eventQueue.enqueue(age);
	}

	public void hoverStart(Feld feld) {
		HoverStartEvent hse = new HoverStartEvent(feld);
		eventQueue.enqueue(hse);
	}

	public void hoverEnde(Feld feld) {
		HoverEndeEvent hee = new HoverEndeEvent(feld);
		eventQueue.enqueue(hee);
	}

}