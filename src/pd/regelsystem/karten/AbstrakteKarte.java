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


package pd.regelsystem.karten;

import pd.regelsystem.Karte;
import pd.regelsystem.Regel;
import pd.serialisierung.BodesuriCodierbaresObjekt;

/**
 * Karte, mit welcher im Spiel gespielt wird.
 *
 * Eine Karte hat einen Namen, eine {@link KartenFarbe} und eine {@link Regel}.
 */
public abstract class AbstrakteKarte
		extends BodesuriCodierbaresObjekt implements Karte {
	private KartenFarbe farbe;
	private Regel regel;
	private String name;

	/**
	 * Erstellt eine Karte.
	 *
	 * @param name Name der Karte, wie z. B. "Ass"
	 * @param farbe Farbe der Karte, wie z. B. KartenFarbe.Herz
	 */
	public AbstrakteKarte(String name, KartenFarbe farbe) {
		super("Karte " + farbe + " " + name);
		this.farbe = farbe;
		this.name = name;
	}

	public String toString() {
		return getFarbe() + " " + getName();
	}

	public String getName() {
		return name;
	}

	public String getFarbe() {
		return farbe.toString();
	}

	public String getRegelBeschreibung() {
		return getRegel().getBeschreibung();
	}

	public Regel getRegel() {
		return regel;
	}

	protected void setRegel(Regel regel) {
		this.regel = regel;
	}
}
