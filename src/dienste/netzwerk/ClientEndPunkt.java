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


package dienste.netzwerk;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import dienste.serialisierung.SerialisierungsKontext;

/**
 * Erstellt eine TCP-Verbindung zu einem Server.
 */
public class ClientEndPunkt extends EndPunkt{
	/**
	 * Startet die Kommunikation mit dem übergebenen System (Hostname & Port).
	 * Dieser Konstruktor wird vom Client verwendet.
	 *
	 *
	 * @param hostname
	 *            Hostname des zu verbindenen Systems
	 * @param port
	 *            Port des zu verbinden Systems
	 * @param briefkasten Briefkasten in welchem die Nachrichten abgelegt werden können
	 * @param sk Serialisierungskontext
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public ClientEndPunkt(String hostname, int port, BriefKastenInterface briefkasten,
	                SerialisierungsKontext sk)
	        throws UnknownHostException, IOException {
		serialisierungsKontext = sk;
		socket = new Socket(hostname, port);

		startVerhandlung(briefkasten);
	}
}
