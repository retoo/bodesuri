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


package ch.bodesuri.dienste.netzwerk;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import ch.bodesuri.dienste.eventqueue.EventQueue;
import ch.bodesuri.dienste.serialisierung.SerialisierungsKontext;
import ch.bodesuri.dienste.threads.BodesuriThread;



/**
 * Der Empfänger ist für das Empfangen von {@link Nachricht} über einen
 * {@link EndPunkt} zuständig. Die eingehenden Nachrichten werden der
 * Applikation über das {@link BriefKastenInterface} gemeldet. Hinter einem
 * solchen Interface steht in den meisten Fällen eine {@link EventQueue}.
 */
public class Empfaenger extends BodesuriThread {
	private BriefKastenInterface briefkasten;
	private ObjectInputStream inputStream;
	private EndPunktInterface endpunkt;
	private SerialisierungsKontext serialisierungsKontext;

	protected Empfaenger(EndPunktInterface endpunkt, Socket socket,
	        BriefKastenInterface briefkasten, SerialisierungsKontext serialisierungsKontext) throws IOException {
		super("Empfänger " + endpunkt.toString());

		inputStream = new ObjectInputStream(socket.getInputStream());
		this.endpunkt = endpunkt;
		this.briefkasten = briefkasten;
		this.serialisierungsKontext = serialisierungsKontext;
	}

	/**
	 * Startet den Empfänger-Thread
	 */
	public void run() {
		try {
			serialisierungsKontext.registriere(Thread.currentThread());

			while (true) {
				Object obj = inputStream.readObject();

				Nachricht nachricht = (Nachricht) obj;
				Brief brief = new Brief(endpunkt, nachricht);

				briefkasten.einwerfen(brief);
			}
		} catch (IOException e) {
			Brief brief = new Brief(endpunkt, new VerbindungGeschlossen());
			briefkasten.einwerfen(brief);
			return;
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
