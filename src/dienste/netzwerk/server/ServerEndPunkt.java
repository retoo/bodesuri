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


package dienste.netzwerk.server;

import java.io.IOException;
import java.net.Socket;

import dienste.netzwerk.BriefKastenInterface;
import dienste.netzwerk.EndPunkt;
import dienste.serialisierung.SerialisierungsKontext;

/**
 * Erstellt eine ClientVerbindung.
 */
public class ServerEndPunkt extends EndPunkt {

	/**
	 * Startet die Kommunkation mit dem übergebenen Socket. Dieser Konstrutkur
	 * wird vom Server verwendet.
	 *
	 * @param socket
	 *            zu verwendendender Socket
	 * @param briefkasten
	 *            Briefkasten in welchem die Nachrichten abgelegt werden können
	 * @param sk Serialisierungskontext
	 * @throws IOException
	 */
	public ServerEndPunkt(Socket socket, BriefKastenInterface briefkasten,
	                SerialisierungsKontext sk)
	        throws IOException {
		this.socket = socket;
		this.serialisierungsKontext = sk;

		startVerhandlung(briefkasten);
	}
}
