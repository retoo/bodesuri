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


package ch.bodesuri.pd.regelsystem.verstoesse;

/**
 * Wird geworfen, wenn eine Regel ungültig ist, also die Zugeingabe gegen die
 * Regel verstösst.
 */
public abstract class RegelVerstoss extends Exception {
	private double spezifitaet;

	/**
	 * @param erklaerung Erklärung des Verstosses
	 */
	public RegelVerstoss(String erklaerung) {
		this(0.0, erklaerung);
	}

	/**
	 * @param spezifitaet Spezifität des Verstosses, ungefähr von 0.0 bis 2.0
	 * @param erklaerung Erklärung des Verstosses
	 */
	public RegelVerstoss(double spezifitaet, String erklaerung) {
		super(erklaerung);
		this.spezifitaet = spezifitaet;
	}

	/**
	 * Sieht zum Beispiel so aus: "Regelverstoss: Zug muss mit Figur begonnen
	 * werden."
	 */
	public String toString() {
		return "Regelverstoss: " + getMessage();
	}

	/**
	 * @return "Spezifität" des Verstosses; je höher die Spezifität, desto
	 *         genauer ist der Verstoss
	 */
	public double getSpezifitaet() {
		return spezifitaet;
	}
}
