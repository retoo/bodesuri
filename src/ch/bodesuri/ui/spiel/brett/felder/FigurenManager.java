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


package ch.bodesuri.ui.spiel.brett.felder;

import java.util.IdentityHashMap;

import ch.bodesuri.pd.spiel.spieler.Figur;

/**
 * Dient zur Vereinigung der Problemdomain Figur und der GUI Figur2d.
 */
public class FigurenManager {
	private IdentityHashMap<Figur, Figur2d> figurMap;

	public FigurenManager() {
		figurMap = new IdentityHashMap<Figur, Figur2d>();
	}

	public Figur2d get(Figur figur) {
		Figur2d figur2d = figurMap.get(figur);

		return figur2d;
	}

	/**
	 * Die Problemdomain Figur und die GUI Figur2d werden zur HashMap
	 * hinzugefügt.
	 * 
	 * @param figur
	 * @param figur2d
	 */
	public void registriere(Figur figur, Figur2d figur2d) {
		figurMap.put(figur, figur2d);
	}
}
