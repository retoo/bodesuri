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
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import dienste.serialisierung.SerialisierungsKontext;

/**
 * Dient dem Server und dem Client zur Kommunikation mit dem jeweiligen
 * Kommunikationspartner.
 */
public abstract class EndPunkt implements EndPunktInterface {
	protected Socket socket;
	private Thread empfaengerThread;
	private ObjectOutputStream outputStream;
	protected SerialisierungsKontext serialisierungsKontext;

	protected void startVerhandlung(BriefKastenInterface briefkasten) throws IOException {
		outputStream = new ObjectOutputStream(socket.getOutputStream());

		/*
		 * Der Empfanger der Nachrichten in einem dedizierten Thread.
		 */
		empfaengerThread = new Empfaenger(this, socket, briefkasten, serialisierungsKontext);
		empfaengerThread.start();
	}

	/* (non-Javadoc)
     * @see dienste.netzwerk.EndPunktInterface#sende(dienste.netzwerk.Nachricht)
     */
	public void sende(Nachricht nachricht) {
		try {
			outputStream.writeObject(nachricht);
		} catch (IOException e) {
			throw new VerbindungWegException(e);
		}
	}

	/* (non-Javadoc)
     * @see dienste.netzwerk.EndPunktInterface#ausschalten()
     */
	public void ausschalten() {
		try {
			socket.shutdownInput();
			socket.shutdownOutput();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		InetAddress addr = socket.getInetAddress();
		return addr.getHostAddress() + ":" + socket.getPort();
	}
}
