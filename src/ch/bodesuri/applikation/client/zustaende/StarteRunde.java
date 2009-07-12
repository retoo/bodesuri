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


package ch.bodesuri.applikation.client.zustaende;

import ch.bodesuri.applikation.client.pd.Spieler;
import ch.bodesuri.dienste.automat.zustaende.PassiverZustand;
import ch.bodesuri.dienste.automat.zustaende.Zustand;

/**
 * Zustand wenn eine neue Runde gestartet wird. Alle Eigenschaften der Spieler
 * werden zurückgesetzt (am Zug, hat Aufgegeben). Der Automat geht nach
 * {@link KarteTauschenAuswaehlen} über.
 */
public class StarteRunde extends ClientZustand implements PassiverZustand {
	public Class<? extends Zustand> handle() {
		for (Spieler spieler : spiel.getSpieler()) {
			spieler.setAmZug(false);
			spieler.setHatAufgebeben(false);
		}

		return KarteTauschenAuswaehlen.class;
	}
}