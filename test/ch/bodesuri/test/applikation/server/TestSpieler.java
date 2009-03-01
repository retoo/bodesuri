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


package ch.bodesuri.test.applikation.server;

import ch.bodesuri.applikation.nachrichten.SpielBeitreten;
import ch.bodesuri.dienste.eventqueue.EventQueue;
import ch.bodesuri.dienste.netzwerk.EndPunktInterface;
import ch.bodesuri.dienste.netzwerk.Nachricht;
import ch.bodesuri.dienste.netzwerk.server.NetzwerkEvent;

/**
 * Stellt eine Hilfsklasse für den Test des Servers dar. Der TestSpieler
 * simuliert einen Spieler und dessen Verhalten.
 */
public class TestSpieler {
	public EndPunktInterface endpunkt;

	public EventQueue eventQueue;

	public String name;

	/**
	 * Erstellt einen neuen TestSpieler mit Namen, einem Endpunkt und einer
	 * EventQueue.
	 * 
	 * @param name
	 *            Name des Spielers
	 * @param client
	 *            Endpunkt für den Client
	 * @param eventQueue
	 *            EventQueue
	 */
	public TestSpieler(String name, EndPunktInterface client,
			EventQueue eventQueue) {
		this.name = name;
		this.endpunkt = client;
		this.eventQueue = eventQueue;
	}

	/**
	 * Sendet eine neue SpielBeitreten-Nachricht an den Server im Namen des
	 * Spielers.
	 */
	public void sendeBeitritt() {
		sende(new SpielBeitreten(name));
	}

	/**
	 * Sendet eine Nachricht im Namen des Spielers an den Server.
	 * 
	 * @param nachricht
	 *            Zu sendende Nachricht.
	 */
	private void sende(Nachricht nachricht) {
		endpunkt.sende(nachricht);
	}

	/**
	 * @return Empfangene Nachricht.
	 */
	public Nachricht getNachricht() {
		NetzwerkEvent netzwerkEvent = (NetzwerkEvent) eventQueue.dequeue();
		return netzwerkEvent.brief.nachricht;
	}

	/**
	 * @return Prüft, ob eine Nachricht empfangen wurde.
	 */
	public boolean hatNachrichten() {
		return !eventQueue.isLeer();
	}
}
