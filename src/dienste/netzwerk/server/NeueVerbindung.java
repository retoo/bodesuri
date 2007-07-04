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

import dienste.eventqueue.Event;
import dienste.netzwerk.Empfaenger;
import dienste.netzwerk.EndPunktInterface;

/**
 * Meldet eine neue Verbindung. Wird durch den {@link Empfaenger} bei
 * Verbindungsaufbau erstellt. Da nur der Server neue Verbindungen zulässt ist
 * dieser Event nur für den Server relevant.
 */
public class NeueVerbindung extends Event {
	/**
	 * EndPunkt der frisch erstellten Verbindung
	 */
	public final EndPunktInterface endpunkt;

	/**
	 * Erstellt Nachricht
	 * * @param client
	 */
	public NeueVerbindung(EndPunktInterface client) {
		this.endpunkt = client;
	}
}
