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

import ch.bodesuri.pd.karten.Ass;
import ch.bodesuri.pd.karten.KartenFarbe;
import ch.bodesuri.pd.regelsystem.Karte;
import ch.bodesuri.pd.regelsystem.ZugEingabe;
import ch.bodesuri.pd.regelsystem.verstoesse.RegelVerstoss;
import ch.bodesuri.pd.regelsystem.verstoesse.Verstoesse;
import ch.bodesuri.pd.regelsystem.verstoesse.WegLaengeVerstoss;
import ch.bodesuri.pd.zugsystem.Aktion;
import ch.bodesuri.pd.zugsystem.Bewegung;

/**
 * Testet die Funktionalität eines Regelverstosses.
 */
public class RegelVerstossTest extends RegelTestCase {
	private Karte ass;
	protected void setUp() {
		super.setUp();
		ass = new Ass(KartenFarbe.Herz);
	}

	/**
	 * Testet das Verhalten des RegelVerstoss beim Ass, wo je nach Spezifität
	 * der eine oder der andere WegLaengeVerstoss geworfen werden sollte.
	 */
	public void testRegelVerstossBeiAss() {
		start = bank(0);
		ziel  = bank(0).getNtesFeld(3);
		lager(0).versetzeFigurAuf(start);
		try {
			Bewegung b = new Bewegung(start, ziel);
			ZugEingabe ze = new ZugEingabe(spieler(0), ass, b);
			ze.validiere();
			fail("Sollte RegelVerstoss geben.");
		} catch (RegelVerstoss rv) {
			assertTrue(rv instanceof WegLaengeVerstoss);
			WegLaengeVerstoss wlv = (WegLaengeVerstoss) rv;
			assertEquals(3, wlv.getIstLaenge());
			assertEquals(1, wlv.getSollLaenge());
		}

		ziel  = bank(0).getNtesFeld(8);
		try {
			Bewegung b = new Bewegung(start, ziel);
			ZugEingabe ze = new ZugEingabe(spieler(0), ass, b);
			ze.validiere();
			fail("Sollte RegelVerstoss geben.");
		} catch (RegelVerstoss rv) {
			assertTrue(rv instanceof WegLaengeVerstoss);
			WegLaengeVerstoss wlv = (WegLaengeVerstoss) rv;
			assertEquals(8, wlv.getIstLaenge());
			assertEquals(11, wlv.getSollLaenge());
		}
	}

	public void testRegelVerstossBeiAssMitGeschuetzt() {
		start = bank(0).getVorheriges();
		ziel  = bank(0);
		new Aktion(lager(0, 0), bank(0)).ausfuehren();
		lager(0, 1).versetzeFigurAuf(start);

		try {
			Bewegung b = new Bewegung(start, ziel);
			ZugEingabe ze = new ZugEingabe(spieler(0), ass, b);
			ze.validiere();
			fail("Sollte RegelVerstoss geben.");
		} catch (RegelVerstoss rv) {
			assertTrue(rv instanceof Verstoesse.AufOderUeberGeschuetzteFahren);
		}
	}
}
