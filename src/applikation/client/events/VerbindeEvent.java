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


package applikation.client.events;

import dienste.eventqueue.Event;

/**
 * Event der ausgelöst wird wenn der Benutzer eine Verbindung zum Server
 * herstellen will.
 */
public class VerbindeEvent extends Event {
	/**
	 * Der Hostname des Servers
	 */
	public final String hostname;
	/**
	 * Der Port des Servers
	 */
	public final Integer port;
	/**
	 * Der Name des Spielers
	 */
	public final String spielerName;

	/**
	 * Der Benutzer will eine Verbindung zum Server herstellen.
	 * 
	 * @param hostname
	 *            Der Hostname des Servers
	 * @param port
	 *            Der Port des Servers
	 * @param spielerName
	 *            Name das Spielers
	 */
	public VerbindeEvent(String hostname, Integer port, String spielerName) {
		this.hostname = hostname;
		this.port = port;
		this.spielerName = spielerName;
	}
}
