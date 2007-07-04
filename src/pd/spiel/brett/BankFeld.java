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

import pd.spiel.spieler.Spieler;

/**
 * Bankfeld, das erste Feld, auf das eine Figur kommt, wenn sie das Lager
 * verlässt.
 */
public class BankFeld extends SpielerFeld {
	private HimmelFeld himmelFeld;

	/**
	 * @see SpielerFeld#SpielerFeld(int, Spieler)
	 */
	public BankFeld(int nummer, Spieler spieler) {
		super(nummer, spieler);
	}

	/**
	 * @return true, wenn Bankfeld von ihrem Besitzer besetzt ist
	 */
	public boolean istBesetztVonBesitzer() {
		return istBesetztVon(getSpieler());
	}

	public boolean istBank() {
		return true;
	}

	public boolean istRing() {
		return true;
	}

	/**
	 * Gibt das erste Feld des Himmels zurück.
	 *
	 * @return Erstes Himmelfeld, das nach Bankfeld kommt
	 */
	public HimmelFeld getHimmel() {
		return himmelFeld;
	}

	/**
	 * Setzt das erste Himmelfeld.
	 *
	 * @param himmelFeld
	 *            Erstes Himmelfeld
	 */
	public void setHimmel(HimmelFeld himmelFeld) {
		this.himmelFeld = himmelFeld;
	}
}
