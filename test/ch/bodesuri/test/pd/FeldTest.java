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


package ch.bodesuri.test.pd;
import ch.bodesuri.pd.spiel.brett.Feld;

/**
 * Stellt Tests zur Überprüfung der Funktionalität eines Feldes zur Verfügung.
 */
public class FeldTest extends ProblemDomainTestCase {
	private Feld feld1;
	private Feld feld2;
	
	protected void setUp() {
		super.setUp();
		feld1 = bank(0);
		feld2 = feld1.getNaechstes();
		lager(0).versetzeFigurAuf(feld1);
	}

	/**
	 * Prüft die Feldverkettung.
	 */
	public void testNaechstesVorheriges() {
		Feld feld = feld1.getNaechstes().getVorheriges();
		assertEquals(feld1, feld);
	}
	
	/**
	 * Prüft die Feldverkettung und Getter-Methode.
	 */
	public void testGetNtesFeld() {
		assertEquals(feld1, feld1.getNtesFeld(0));
		assertEquals(feld1.getNaechstes(), feld1.getNtesFeld(1));
		assertEquals(feld1.getNaechstes().getNaechstes(), feld1.getNtesFeld(2));
	}

	/**
	 * Prüft die Getter-Methode.
	 */
	public void testIstFrei() {
		assertFalse(feld1.istFrei());
		assertTrue(feld2.istFrei());
	}

	/**
	 * Prüft die Getter-Methode.
	 */
	public void testIstBesetzt() {
		assertTrue(feld1.istBesetzt());
		assertTrue(feld1.istBesetztVon(spieler(0)));
		assertFalse(feld2.istBesetzt());
		assertFalse(feld2.istBesetztVon(spieler(0)));
	}
	
	/**
	 * Versetzt Figur auf anderes Feld und prüft, ob danach die Verkettung
	 * noch in Ordnung ist.
	 */
	public void testVersetzeFigurAuf() {
		feld1.versetzeFigurAuf(feld2);
		assertTrue(feld1.istFrei());
		assertTrue(feld2.istBesetztVon(spieler(0)));
	}
	
	/**
	 * Prüft auf Konsistenz der Himmelfelder.
	 */
	public void testGetHimmel() {
		assertEquals(brett.getHimmelFelderVon(spieler(0)).get(0),
		             bank(0).getHimmel());
	}
	
	/**
	 * Prüft auf Konstistenz des Spielers.
	 */
	public void testGetSpieler() {
		assertEquals(spieler(0), lager(0).getSpieler());
		assertEquals(spieler(0), bank(0).getSpieler());
		assertEquals(spieler(0), bank(0).getHimmel().getSpieler());
	}
	
	/**
	 * Prüft auf Konsistenz der Feldnummerierung. 
	 */
	public void testGetNummer() {
		assertEquals(0, bank(0).getNummer());
		assertEquals("BankFeld 0", bank(0).toString());
	}
}
