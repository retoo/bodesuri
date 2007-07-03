package applikation.client.controller;

import applikation.client.pd.Feld;
import applikation.client.pd.Karte;

public interface Steuerung {

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
	public abstract void verbinde(String host, int port_raw, String spieler);

	/**
	 * Dem Automaten mitteilen welche Karte der Benutzer gewählt hat.
	 *
	 * @param gewaehlteKarte
	 */
	public abstract void karteAuswaehlen(Karte gewaehlteKarte);

	/**
	 * Dem Automaten mitteilen, dass die vom Benutzer ausgewählte Karte
	 * getauscht werden soll.
	 */
	public abstract void kartenTauschBestaetigen();

	/**
	 * Dem Automaten mitteilen welches Feld der Benutzer gewählt hat.
	 *
	 * @param gewaehltesFeld
	 */
	public abstract void feldAuswaehlen(Feld gewaehltesFeld);

	/**
	 * Dem Automaten mitteilen, dass das ausgewählte Feld wieder abgewählt
	 * werden soll.
	 *
	 */
	public abstract void feldAbwaehlen();

	/**
	 * Dem Automaten mitteilen, dass man aufgeben möchte.
	 */
	public abstract void aufgeben();

	/**
	 * Dem Automaten mitteilen, dass er das Spiel beenden soll.
	 */
	public abstract void beenden();

	/**
	 * Teilt dem Automat mit, dass er dass das übergebene Feld gerade gehovered wird.
	 *
	 * @param feld betroffenes Feld
	 */
	public abstract void hoverStart(Feld feld);

	/**
	 * Teilt dem Automat mit, dass er dass das übergebene Feld nicht mehr gehoveredwird.
	 *
	 * @param feld betroffenes Feld
	 */
	public abstract void hoverEnde(Feld feld);

	/**
	 * Meldet dem Automaten die Eingabe des übergebenen Textes.
	 *
	 * @param text zu versendender Text
	 */
	public abstract void chatNachricht(String text);

}
