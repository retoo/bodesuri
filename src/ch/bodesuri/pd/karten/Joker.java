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


package ch.bodesuri.pd.karten;

import ch.bodesuri.pd.regelsystem.RegelVeroderung;
import ch.bodesuri.pd.regelsystem.RueckwaertsRegel;
import ch.bodesuri.pd.regelsystem.SiebnerRegel;
import ch.bodesuri.pd.regelsystem.StartRegel;
import ch.bodesuri.pd.regelsystem.TauschRegel;
import ch.bodesuri.pd.regelsystem.VorwaertsRegel;

public class Joker extends AbstrakteKarte {
	public Joker(KartenFarbe farbe) {
		super("Joker", farbe);
		RegelVeroderung regel = new RegelVeroderung();
		for (int i = 1; i <= 13; ++i) {
			if (i == 7) continue;
			regel.fuegeHinzu(new VorwaertsRegel(i));
		}
		regel.fuegeHinzu(new RueckwaertsRegel(4));
		regel.fuegeHinzu(new SiebnerRegel());
		regel.fuegeHinzu(new TauschRegel());
		regel.fuegeHinzu(new StartRegel());
		setRegel(regel);
	}

	public String toString() {
		return "Joker";
	}

	public String getRegelBeschreibung() {
		return "Als beliebige Karte einsetzbar";
	}
}
