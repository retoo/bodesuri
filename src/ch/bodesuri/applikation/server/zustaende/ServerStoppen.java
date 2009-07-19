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

import ch.bodesuri.dienste.automat.zustaende.EndZustand;
import ch.bodesuri.dienste.automat.zustaende.PassiverZustand;
import ch.bodesuri.dienste.automat.zustaende.Zustand;


/**
 * Es wird geprüft ob noch irgendwelche {@link Spieler} mit dem {@link Server} verbunden sind.
 * Ist dies der Fall wird in den Zustand {@link WarteBisAlleVerbindungenWeg} gewechselt.
 * Ansonsten wird der {@link ServerAutomat} in den {@link EndZustand} überführt und beendet sich
 * selber.
 */
public class ServerStoppen extends ServerZustand implements PassiverZustand {
	public Class<? extends Zustand> handle() {
		spiel.server.ausschalten();
		spiel.status.loescheDatei();

		if (spiel.getAnzahlSpieler() == 0) {
			return EndZustand.class;
		} else {
			return WarteBisAlleVerbindungenWeg.class;
		}
	}
}