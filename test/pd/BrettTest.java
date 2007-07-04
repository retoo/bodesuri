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

/**
 * Beinhaltet Tests für das Brett in der Problem-Domain.
 */
public class BrettTest extends ProblemDomainTestCase {
	protected void setUp() {
		super.setUp();
	}
	
	/**
	 * Prüft, ob alle Felder vorhanden sind.
	 */
	public void testGetAlleFelder() {
		assertEquals(96, brett.getAlleFelder().size());
	}
	
	/**
	 * Prüft, ob Freies Lagerfeld gefunden wird und ob keines
	 * gefunden wird, wenn alle Lagerfelder besetzt sind. 
	 */
	public void testGetFreiesLagerFeldVon() {
		try {
			brett.getFreiesLagerFeldVon(spieler(0));
			fail("Sollte RuntimeException geben");
		} catch (RuntimeException e) {
			assertNotNull(e);
		}
		lager(0).versetzeFigurAuf(bank(0));
		assertEquals(lager(0), brett.getFreiesLagerFeldVon(spieler(0)));
	}
}
