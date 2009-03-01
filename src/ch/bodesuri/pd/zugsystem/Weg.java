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

import java.util.Vector;

import ch.bodesuri.pd.spiel.brett.Feld;


/**
 * Ein Weg ist eine Liste von Feldern, die bei einer {@link Bewegung} "berührt"
 * werden.
 */
public class Weg extends Vector<Feld> {
	private boolean vorwaerts;

	public Weg(boolean vorwaerts) {
		this.vorwaerts = vorwaerts;
	}

	/**
	 * @return true, wenn der Weg rückwärts geht
	 */
	public boolean istRueckwaerts() {
		return !vorwaerts;
	}

	/**
	 * Berechne die Länge des Weges, also wie viele Schritte gebraucht werden,
	 * um von Start zum Ziel zu kommen.
	 * 
	 * <b>Achtung:</b> Ist nicht das gleiche wie size().
	 * 
	 * @return Länge des Weges
	 */
	public int getWegLaenge() {
		return size() - 1;
	}
}
