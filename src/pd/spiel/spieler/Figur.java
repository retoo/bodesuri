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


package pd.spiel.spieler;

import java.util.Observable;

import pd.spiel.brett.Feld;

/**
 * Figur, von der jeder {@link Spieler} 4 hat und die auf einem {@link Feld}
 * steht.
 */
public class Figur extends Observable {
	private Spieler spieler;
	private Feld feld;

	/**
	 * @param spieler Spieler, dem die Figur gehört
	 */
	public Figur(Spieler spieler) {
		this.spieler = spieler;
	}

	/**
	 * @return Spieler, dem die Figur gehört
	 */
	public Spieler getSpieler() {
		return spieler;
	}

	/**
	 * @param spieler Spieler, der überprüft wird
	 * @return true, wenn Figur dem Spieler gehört
	 */
	public boolean istVon(Spieler spieler) {
		return this.spieler == spieler;
	}

	/**
	 * @return Feld, auf dem die Figur steht
	 */
	public Feld getFeld() {
		return feld;
	}

	/**
	 * Figur benachrichtigen, dass sie auf ein neues Feld bewegt wurde.
	 * 
	 * @param ziel Feld, auf dem sie neu steht
	 */
	public void versetzeAuf(Feld ziel) {
		this.feld = ziel;
		setChanged();
		notifyObservers();
	}
}
