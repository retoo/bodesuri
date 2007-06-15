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

	/**
	 * Hinweise, die vom Automaten an den Controller gereicht werden, darstellen
	 * um dem Spieler zu führen.
	 *
	 * @param hinweis
	 *            anzuzeigender Hinweis
	 */

	/**
	 * Die gespielte Karte, die vom Automaten an den Controller gereicht wird,
	 * darstellen.
	 *
	 * @param karte
	 *            gespielte Karte
	 */
//	 FIXME !!  public abstract void zeigeGespielteKarte(String karte);

	/**
	 * Die Auswahl von Karten (de-)aktivieren.
	 *
	 * param aktiv
	 */
	// FIXME RETO: public abstract void aktiviereKarte(Boolean aktiv);



	/* (non-Javadoc)
     * @see ui.SteuerungInf#verbinde(java.lang.String, int, java.lang.String)
     */
	public void verbinde(String host, int port_raw, String spieler) {
		VerbindeEvent ve = new VerbindeEvent(host, port_raw, spieler);
		eventQueue.enqueue(ve);
	}

	/* (non-Javadoc)
     * @see ui.SteuerungInf#karteAuswaehlen(applikation.client.pd.Karte)
     */
	public void karteAuswaehlen(Karte gewaehlteKarte) {
		KarteGewaehltEvent kge = new KarteGewaehltEvent(gewaehlteKarte);
		eventQueue.enqueue(kge);
	}

	/* (non-Javadoc)
     * @see ui.SteuerungInf#feldAuswaehlen(applikation.client.pd.Feld)
     */
	public void feldAuswaehlen(Feld gewaehltesFeld) {
		FeldGewaehltEvent fge = new FeldGewaehltEvent(gewaehltesFeld);
		eventQueue.enqueue(fge);
	}

	/* (non-Javadoc)
     * @see ui.SteuerungInf#feldAbwaehlen()
     */
	public void feldAbwaehlen() {
		FeldAbgewaehltEvent fage = new FeldAbgewaehltEvent();
		eventQueue.enqueue(fage);
	}

	/* (non-Javadoc)
     * @see ui.SteuerungInf#aufgeben()
     */
	public void aufgeben() {
		AufgegebenEvent age = new AufgegebenEvent();
		eventQueue.enqueue(age);
	}

	/* (non-Javadoc)
     * @see ui.SteuerungInf#zielHover(applikation.client.pd.Feld)
     */
	public void zielHover(Feld feld) {
		HoverStartEvent hse = new HoverStartEvent(feld);
		eventQueue.enqueue(hse);
	}

}