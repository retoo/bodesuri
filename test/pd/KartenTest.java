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
import java.util.List;

import pd.regelsystem.karten.Deck;
import pd.regelsystem.karten.Karte;
import pd.regelsystem.karten.KartenFarbe;
import pd.regelsystem.karten.Sechs;

/**
 * Teste die Funktionalitäten der Karte.
 */
public class KartenTest extends ProblemDomainTestCase {
	/**
	 * Prüft Getter-Methoden und Initialisierung der Karte.
	 */
	public void testKarte() {
		Karte sechs = new Sechs(KartenFarbe.Herz);
		assertEquals("Sechs", sechs.getName());
		assertEquals(KartenFarbe.Herz, sechs.getKartenFarbe());
		assertEquals("Herz Sechs", sechs.toString());
	}
	
	/**
	 * Prüft das Deck, ob es korrekt initialisiert wurde.
	 */
	public void testDeck() {
		List<Karte> deck = Deck.erstelleKarten();
		assertEquals(55, deck.size());
		// Für 100% Abdeckung
		new Deck();
	}
	
	/**
	 * Prüft Verhalten beim Mischens.
	 */
	public void testGetKarte() {
		// Verhalten bei erneutem Mischeln
		for (int i = 0; i < 150; ++i) {
			assertNotNull(kartenGeber.getKarte());
		}
		
		// Es darf nicht 2 x hintereinander das selbe Objekt gezogen werden
		assertNotSame(kartenGeber.getKarte(), kartenGeber.getKarte());
	}
}
