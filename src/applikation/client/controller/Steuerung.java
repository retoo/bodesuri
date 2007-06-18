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
	 * Wenn man keine Karten mehr spielen kann. Noch nicht sicher ob dies auch
	 * im definitven Spiel drin ist...
	 */
	public abstract void aufgeben();

	public abstract void hoverStart(Feld feld);
	public abstract void hoverEnde(Feld feld);

	public abstract void chatNachricht(String text);

}
