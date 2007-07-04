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


package pd.regelsystem.karten;

import java.util.List;

import pd.regelsystem.RegelVeroderung;

public class Joker extends Karte {
	public Joker(KartenFarbe farbe) {
		super("Joker", farbe);
		RegelVeroderung regel = new RegelVeroderung();
		List<Karte> karten = Deck.getKartenFuerFarbe(KartenFarbe.Herz);
		for (Karte karte : karten) {
			regel.fuegeHinzu(karte.getRegel());
		}
		setRegel(regel);
	}

	public String toString() {
		return "Joker";
	}

	public String getRegelBeschreibung() {
		return "Als beliebige Karte einsetzbar";
	}
}
