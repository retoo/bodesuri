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

import dienste.eventqueue.EventQueue;
import dienste.netzwerk.BriefKastenInterface;
import dienste.serialisierung.SerialisierungsKontext;

/**
 * Server welcher die Kommunikation mit mehreren Clients ermöglicht. Beim Start
 * wird ein Socket an den TCP Port 7788 gebunden welcher eingehende Verbindungen
 * akzeptiert. Eingehende Nachrichten könnnen über eine zu übergebende Queue
 * empfangne werden.
 *
 * Neue Verbindungen werden über
 *
 */
public class Server {
	private static final int PORT = 7788;
	private BriefKastenInterface serverBriefkasten;
	private Daemon daemon;

	/**
	 * Queue für eingehende Events
	 */
	public EventQueue queue;


	/**
	 * Erstellt einen neuen Server.
	 *
	 * @param queue EventQueue in welche die eingehenden {@link NetzwerkEvent} gelegt werden
	 * @param sk Serialisierungskontext
	 */
	public Server(EventQueue queue, SerialisierungsKontext sk) {
		this.queue = queue;
		serverBriefkasten = new BriefkastenAdapter(queue);

		daemon = new Daemon(PORT, serverBriefkasten, queue, sk);

		starteDaemon();
	}


	private void starteDaemon() {
		daemon.start();
    }


	/**
	 * Schaltet den Server komplett aus.
	 */
	public void ausschalten() {
	    daemon.auschalten();
    }
}
