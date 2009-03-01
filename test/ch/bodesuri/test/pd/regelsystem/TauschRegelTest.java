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

import ch.bodesuri.pd.regelsystem.StartRegel;
import ch.bodesuri.pd.regelsystem.TauschRegel;
import ch.bodesuri.pd.regelsystem.verstoesse.RegelVerstoss;

/**
 * Testet die Funktionalität der TauschRegel.
 */
public class TauschRegelTest extends RegelTestCase {
	protected void setUp() {
		super.setUp();
		regel = new TauschRegel();
	}

	public void testArbeitetMitWeg() {
		assertFalse(regel.arbeitetMitWeg());
	}

	/**
	 * Prüft, ob ein simpler Tausch funktioniert.
	 * 
	 * @throws RegelVerstoss
	 */
	public void testTauschen() throws RegelVerstoss {
		start = bank(0).getNtesFeld(2);
		ziel  = bank(0).getNtesFeld(5);
		lager(0).versetzeFigurAuf(start);
		lager(1).versetzeFigurAuf(ziel);
		sollteValidieren();
		
		assertTrue(start.istBesetztVon(spieler(1)));
		assertTrue(ziel.istBesetztVon(spieler(0)));
	}
	
	/**
	 * Prüft, ob zwei eigene Figuren nicht getauscht werden können.
	 */
	public void testTauschenZweiEigene() {
		start = bank(0).getNtesFeld(2);
		ziel  = bank(0).getNtesFeld(5);
		lager(0, 0).versetzeFigurAuf(start);
		lager(0, 1).versetzeFigurAuf(ziel);
		sollteVerstossGeben();
	}
	
	/**
	 * Prüft, ob zwei fremde Figuren nicht getauscht werden können.
	 */
	public void testTauschenZweiFremde() {
		start = bank(0).getNtesFeld(2);
		ziel  = bank(0).getNtesFeld(5);
		lager(1, 0).versetzeFigurAuf(start);
		lager(1, 1).versetzeFigurAuf(ziel);
		sollteVerstossGeben();
	}
	
	/**
	 * Prüft, ob nicht "auf der Stelle", d.h. nur mit einer Figur
	 * getauscht werden kann.
	 */
	public void testTauschenMitEinerFigur() {
		start = bank(0);
		ziel  = bank(0).getNaechstes();
		lager(0).versetzeFigurAuf(start);
		sollteVerstossGeben();
		
		start.versetzeFigurAuf(ziel);
		sollteVerstossGeben();
	}
	
	/**
	 * Prüft, ob geschützte Figuren auch wirklich nicth getauscht 
	 * werden können.
	 * 
	 * @throws RegelVerstoss
	 */
	public void testTauschenGeschuetzt() throws RegelVerstoss {
		start = lager(0);
		ziel  = bank(0);
		sollteValidieren(new StartRegel());
		
		assertTrue(start.istFrei());
		assertTrue(ziel.istBesetztVon(spieler(0)));
		assertTrue(ziel.istGeschuetzt());
		
		start = bank(0);
		ziel  = bank(0).getNtesFeld(7);
		lager(1).versetzeFigurAuf(ziel);
		sollteVerstossGeben();
	}
	
	/**
	 * Prüft, ob Figuren, die auf einem Lagerfeld stehen, nicht
	 * getauscht werden können.
	 */
	public void testTauschenMitLagerFeld() {
		start = lager(0);
		ziel  = bank(0).getNaechstes();
		lager(1).versetzeFigurAuf(ziel);
		sollteVerstossGeben();
	}
}
