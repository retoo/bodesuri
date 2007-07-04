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


package pd;

import pd.spiel.Spiel;

/**
 * Testet die Funktionalität des Spiels.
 */
public class SpielTest extends ProblemDomainTestCase {
	protected void setUp() {
		super.setUp();
		spiel = new Spiel();
	}

	/**
	 * Prüft auf konsistentes Verhalten des Spielers innerhalb
	 * des Spiels. 
	 */
	public void testSpieler() {
		assertEquals(4, spiel.getSpieler().size());
		spiel.fuegeHinzu("Spieler 0");
		spiel.fuegeHinzu("Spieler 1");
		spiel.fuegeHinzu("Spieler 2");
		spiel.fuegeHinzu("Spieler 3");
		assertEquals(4, spiel.getSpieler().size());

		assertEquals(0, spieler(0).getNummer());
		assertEquals("Spieler 0", spieler(0).getName());
		assertEquals("Spieler 0", spieler(0).toString());
	}
}
