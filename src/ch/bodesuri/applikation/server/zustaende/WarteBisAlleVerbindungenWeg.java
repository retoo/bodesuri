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

import ch.bodesuri.applikation.server.ServerAutomat;
import ch.bodesuri.dienste.automat.zustaende.EndZustand;
import ch.bodesuri.dienste.automat.zustaende.Zustand;
import ch.bodesuri.dienste.netzwerk.EndPunktInterface;

/**
 * Der Server wartet bis alle verbleibenden Verbindungen beendet sind. Sobald
 * dies der Fall ist wird der {@link ServerAutomat} in den {@link EndZustand}
 * überführt und beendet sich selber.
 */
public class WarteBisAlleVerbindungenWeg extends ServerZustand {
	Class<? extends Zustand> verbindungGeschlossen(EndPunktInterface absender) {
		spiel.entferne(absender);

		if (spiel.getAnzahlSpieler() <= 0)
			return EndZustand.class;
		else
			return this.getClass();
	}
}