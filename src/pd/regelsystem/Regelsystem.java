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


package pd.regelsystem;

import java.util.LinkedList;
import java.util.List;

import pd.regelsystem.karten.Karte;
import pd.spiel.spieler.Spieler;

/**
 * Beinhaltet Methoden, die das Abfragen von Regeln mit den Daten eines Spielers
 * vereinfachen.
 */
public class Regelsystem {
	/**
	 * @param spieler Spieler, der überprüft wird
	 * @return true, wenn der Spieler mit seinen Karten ziehen kann
	 */
	public static boolean kannZiehen(Spieler spieler) {
		for (Karte karte : spieler.getKarten()) {
			Regel regel = karte.getRegel();
			if (regel != null && regel.istZugMoeglich(spieler)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param spieler Spieler, der überprüft wird
	 * @return Liste von allen möglichen Zügen als ZugEingaben
	 */
	public static List<ZugEingabe> getMoeglicheZuege(Spieler spieler) {
		List<ZugEingabe> moegliche = new LinkedList<ZugEingabe>();

		for (Karte karte : spieler.getKarten()) {
			Regel regel = karte.getRegel();
			if (regel != null) {
				moegliche.addAll(regel.getMoeglicheZuege(spieler, karte));
			}
		}

		return moegliche;
    }
}
