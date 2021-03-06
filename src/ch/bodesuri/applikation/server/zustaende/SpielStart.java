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


package ch.bodesuri.applikation.server.zustaende;

import ch.bodesuri.applikation.nachrichten.SpielStartNachricht;
import ch.bodesuri.applikation.server.pd.Spiel;
import ch.bodesuri.dienste.automat.zustaende.PassiverZustand;
import ch.bodesuri.dienste.automat.zustaende.Zustand;

/**
 * Den Spielern wird der Spielstart mit der Nachricht {@link SpielStartNachricht}
 * angekündet. Die Nachricht {@link SpielStartNachricht} enthält die Namen aller
 * Spieler und die gebildeten Partnerschaften.
 *
 * Geht direkt in den Zustand {@link StartRunde} über.
 */
public class SpielStart extends ServerZustand implements PassiverZustand {
	public Class<? extends Zustand> handle() {
		Spiel spielerschaft = spiel;

		SpielStartNachricht ssn = new SpielStartNachricht(spielerschaft.getSpielInfo(), spielerschaft.getPartnerschaften());
		spielerschaft.broadcast(ssn);
		
		spiel.status.schreibeStatus("<p>Das Spiel ist bereits voll.</p>");

		return StartRunde.class;
	}
}
