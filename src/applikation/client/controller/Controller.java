package applikation.client.controller;

import java.util.List;

import ui.Steuerung;

import applikation.client.pd.Feld;
import applikation.client.pd.Karte;
import applikation.client.pd.Spiel;
import applikation.client.pd.Spieler;
import applikation.events.AufgegebenEvent;
import applikation.events.FeldAbgewaehltEvent;
import applikation.events.FeldGewaehltEvent;
import applikation.events.HoverStartEvent;
import applikation.events.KarteGewaehltEvent;
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
	 * Die Auswahl von Karten (de-)aktivieren.
	 *
	 * @param aktiv
	 */
	public abstract void aktiviereKarte(Boolean aktiv);


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

	public abstract Steuerung getSteuerung();
}