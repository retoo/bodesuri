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
 * Verstoss, der gebraucht wird, wenn die Weglänge nicht gestimmt hat.
 */
public class WegLaengeVerstoss extends RegelVerstoss {
	private int sollLaenge;
	private int istLaenge;

	public WegLaengeVerstoss(int sollLaenge, int istLaenge) {
		super(1.0 / Math.abs(sollLaenge - istLaenge),
		      "Man muss " + sollLaenge +
              " und nicht " + istLaenge + " Felder weit fahren.");
		this.sollLaenge = sollLaenge;
		this.istLaenge  = istLaenge;
	}

	/**
	 * @return Wie lang der Weg in der Zugeingabe war
	 */
	public int getIstLaenge() {
		return istLaenge;
	}

	/**
	 * @return Wie lang der Weg sein sollte
	 */
	public int getSollLaenge() {
		return sollLaenge;
	}
}
