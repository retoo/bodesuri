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


package applikation.client.konfiguration;

/**
 * Standardwerte für die Anwendungskonfiguration.
 */
public class Konfiguration {
	/**
	 * Deubg-Option: Sollen Debug-Meldungen auf der Konsole ausgegben werden.
	 */
	public boolean debugMeldungen = false;

	/**
	 * Debug-Option: Ob man, obwohl noch Züge möglich sind, aufgeben darf.
	 */
	public boolean debugAufgabeImmerMoeglich = false;

	/**
	 * Debug-Option: Die VerbindenView überspringen und die Standardwerte
	 * verwenden.
	 */
	public boolean debugAutoLogin = false;

	/**
	 * Debug-Option: Keine Verzögerung beim Schliessen der Lobby.
	 */
	public boolean debugKeineLobbyVerzoegerung = false;

	/**
	 * Debug-Option: Keine Verzögerung beim Bot-Denken
	 */
	public boolean debugBotsZoegernNicht = false;

	/**
	 * Default-Hostname
	 */
	public String defaultHost = "localhost";

	/**
	 * Default-Port
	 */
	public int defaultPort = 7788;

	/**
	 * Default-Name
	 */
	public String defaultName = "Dog Spieler";
}
