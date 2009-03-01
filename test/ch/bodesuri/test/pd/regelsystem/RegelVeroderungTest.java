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


package ch.bodesuri.test.pd.regelsystem;

import ch.bodesuri.pd.karten.KartenFarbe;
import ch.bodesuri.pd.karten.Vier;
import ch.bodesuri.pd.regelsystem.verstoesse.RegelVerstoss;

/**
 * Testet die Funktionalität der RegelVeroderung anhand
 * der Viererregel.
 */
public class RegelVeroderungTest extends RegelTestCase {
	protected void setUp() {
		super.setUp();
		regel = new Vier(KartenFarbe.Herz).getRegel();
	}

	public void testArbeitetMitWeg() {
		assertTrue(regel.arbeitetMitWeg());
	}

	/**
	 * Validiert die Viererregel, indem ein gültiger Vorwärtszug
	 * ausgeführt wird.
	 * @throws RegelVerstoss
	 */
	public void testVier() throws RegelVerstoss {
		start = bank(0);
		ziel  = start.getNtesFeld(4);
		lager(0).versetzeFigurAuf(start);
		sollteValidieren();
		
		assertTrue(start.istFrei());
		assertTrue(ziel.istBesetztVon(spieler(0)));
	}
	
	/**
	 * Validiert die Viererregel, indem ein Vorwärtszug ausgeführt
	 * wird, der einen Verstoss verursacht.
	 */
	public void testVierFalsch() {
		start = bank(0);
		ziel  = start.getNtesFeld(3);
		lager(0).versetzeFigurAuf(start);
		sollteVerstossGeben();
		
		start = lager(0);
		sollteVerstossGeben();
	}

	/**
	 * Validiert die Viererregel, indem ein gültiger Rückwärtszug
	 * ausgeführt wird.
	 * @throws RegelVerstoss
	 */
	public void testVierRueckwaerts() throws RegelVerstoss {
		start = bank(0).getNtesFeld(4);
		ziel  = bank(0);
		lager(0).versetzeFigurAuf(start);
		sollteValidieren();
		
		assertTrue(start.istFrei());
		assertTrue(ziel.istBesetztVon(spieler(0)));
	}
	
	/**
	 * Validiert die Viererregel, indem ein Rückwärtszug ausgeführt
	 * wird, der einen Verstoss verursacht.
	 */
	public void testVierRueckwaertsFalsch() {
		start = bank(0).getNtesFeld(3);
		ziel  = bank(0);
		lager(0).versetzeFigurAuf(start);
		sollteVerstossGeben();
	}
}
