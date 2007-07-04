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

import java.io.Serializable;

/**
 * Partnerschaft von zwei {@link Spieler}n.
 */
public class Partnerschaft implements Serializable {
	private final Spieler spielerA;
	private final Spieler spielerB;

	public Partnerschaft(Spieler spielerA, Spieler spielerB) {
		this.spielerA = spielerA;
		this.spielerB = spielerB;
	}

	/**
	 * @return true, wenn beide Spieler fertig sind
	 */
	public boolean istFertig() {
		return spielerA.istFertig() && spielerB.istFertig();
	}

	public Spieler getSpielerA() {
		return spielerA;
	}

	public Spieler getSpielerB() {
		return spielerB;
	}

	public String toString() {
		return spielerA + " und " + spielerB;
	}
}
