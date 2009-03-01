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


package ch.bodesuri.pd.zugsystem;

import ch.bodesuri.pd.spiel.brett.Feld;
import ch.bodesuri.pd.spiel.spieler.Figur;

/**
 * Aktion, die zwei Figuren tauscht.
 */
public class TauschAktion extends Aktion {
	public TauschAktion(Feld start, Feld ziel) {
	    super(start, ziel);
    }

	public void ausfuehren() {
		if (start.istGeschuetzt() || ziel.istGeschuetzt()) {
			throw new RuntimeException("Es dürfen keine geschützten Figuren getauscht werden.");
		}

		Figur tmp = ziel.getFigur();
		start.versetzeFigurAuf(ziel);
		start.setFigur(tmp);
	}
}
