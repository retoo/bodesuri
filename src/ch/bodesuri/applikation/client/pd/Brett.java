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


package ch.bodesuri.applikation.client.pd;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Vector;

/**
 * Dekoriert das Brett aus der PD.
 * Verfügt ausserdem über eigene Felder welche die Felder aus der PD dekorieren.
 */
public class Brett {
	private Vector<Feld> alleFelder = new Vector<Feld>();
	private IdentityHashMap<ch.bodesuri.pd.spiel.brett.Feld, Feld> feldMap;

	public Brett(ch.bodesuri.pd.spiel.brett.Brett brett) {
		feldMap = new IdentityHashMap<ch.bodesuri.pd.spiel.brett.Feld, Feld>();

		for (ch.bodesuri.pd.spiel.brett.Feld feld : brett.getAlleFelder()) {
			Feld appFeld = new Feld(feld);
			alleFelder.add(appFeld);
			feldMap.put(feld, appFeld);
		}
	}

	public List<Feld> getAlleFelder() {
		return alleFelder;
	}

	public Feld getFeld(ch.bodesuri.pd.spiel.brett.Feld f) {
		return feldMap.get(f);
	}

}
