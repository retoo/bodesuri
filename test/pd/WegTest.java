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

import pd.zugsystem.Bewegung;
import pd.zugsystem.Weg;

/**
 * Testet die Funktionalität des Zugsystems.
 */
public class WegTest extends ProblemDomainTestCase {
	/**
	 * Testet den normalen Vorwärtszug.
	 */
	public void testWegVorwaerts() {
		Bewegung bewegung = new Bewegung(bank(0), bank(0).getNtesFeld(3));
		Weg weg = bewegung.getWeg();
		
		assertEquals(bewegung.start, weg.get(0));
		assertEquals(bewegung.start.getNtesFeld(1), weg.get(1));
		assertEquals(bewegung.start.getNtesFeld(2), weg.get(2));
		assertEquals(bewegung.ziel, weg.get(3));
		assertEquals(4, weg.size());
		assertFalse(weg.istRueckwaerts());
	}
	
	/**
	 * Testet den Vorwärtszug, der über eine Grenze führt.
	 */
	public void testWegVorwaertsUeberGrenze() {
		Bewegung bewegung = new Bewegung(bank(3), bank(0));
		Weg weg = bewegung.getWeg();
		
		assertEquals(bewegung.start, weg.firstElement());
		assertEquals(bewegung.ziel, weg.lastElement());
		assertEquals(17, weg.size());
		assertFalse(weg.istRueckwaerts());
	}
	
	/**
	 * Testet den normalen Rückwärtszug.
	 */
	public void testWegRueckwaerts() {
		Bewegung bewegung = new Bewegung(bank(0).getNtesFeld(5), bank(0));
		Weg weg = bewegung.getWeg();
		
		assertEquals(bewegung.start, weg.firstElement());
		assertEquals(bewegung.ziel, weg.lastElement());
		assertEquals(6, weg.size());
		assertTrue(weg.istRueckwaerts());
	}
	
	/**
	 * Testet den Rückwärtszug, der über eine Grenze führt.
	 */
	public void testWegRueckwaertsUeberGrenze() {
		Bewegung bewegung = new Bewegung(bank(0), bank(3));
		Weg weg = bewegung.getWeg();
		
		assertEquals(bewegung.start, weg.firstElement());
		assertEquals(bewegung.ziel, weg.lastElement());
		assertEquals(17, weg.size());
		assertTrue(weg.istRueckwaerts());
	}

	/**
	 * Testet den Weg im Himmel auf ein anderes Feld im Himmel.
	 */
	public void testWegVonEinemHimmelZumAndern() {
		Bewegung bewegung = new Bewegung(himmel(0), himmel(1));
		Weg weg = bewegung.getWeg();
		assertNull(weg);
	}
}
