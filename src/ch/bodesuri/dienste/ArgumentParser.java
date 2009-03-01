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

package ch.bodesuri.dienste;

/**
 * Wandelt Argumente von der Kommandozeile in den richtigen Typ um und überprüft
 * den Wertebereich.
 */
public class ArgumentParser {
	/**
	 * Wandelt einen String in eine gültige Portnummer (int zwischen 1 und
	 * 65535) um.
	 * 
	 * @param arg Der String mit der Portnummer
	 * @return Eine gültige Portnummer
	 */
	public static int parsePort(String arg) {
		try {
			int port = Integer.parseInt(arg);
			if (port < 1 || port > 65535)
				throw new NumberFormatException();
			return port;
		} catch (NumberFormatException ex) {
			throw new RuntimeException(arg + " ist kein gültiger Port."
			                           + " Der Port muss zwischen 1"
			                           + " und 65535 liegen.");
		}
	}

	/**
	 * Wandelt einen String in einen gültigen Hostnamen um (string, nicht leer).
	 * 
	 * @param hostname Der String mit dem Hostnamen
	 * @return Ein gültiger Hostname
	 */
	public static String parseHostname(String hostname) {
		if (hostname.equals("")) {
			throw new RuntimeException("Der Servername darf nicht leer sein.");
		} else {
			return hostname;
		}
	}
}
