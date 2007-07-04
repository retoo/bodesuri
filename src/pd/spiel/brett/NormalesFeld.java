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


package pd.spiel.brett;

/**
 * Normales Feld, welches zwischen den Bankfeldern liegt und auf einem Brett am
 * meisten vorkommt.
 */
public class NormalesFeld extends Feld {
	/**
	 * Erstellt ein normales Feld.
	 *
	 * @param nummer
	 *            Feldnummer, siehe {@link Feld#Feld(int)}
	 */
	public NormalesFeld(int nummer) {
		super(nummer);
	}

	public boolean istNormal() {
		return true;
	}

	public boolean istRing() {
		return true;
	}
}
