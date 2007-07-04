/*
 * Copyright (C) 2007  Danilo Couto, Philippe Eberli,
 *                     Pascal Hobus, Reto Schüttel, Robin Stocker
 *
 * This file is part of Bodesuri.
 *
 * Bodesuri is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.
 *
 * Bodesuri is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Bodesuri; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


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
