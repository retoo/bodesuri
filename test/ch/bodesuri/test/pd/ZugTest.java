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

import ch.bodesuri.pd.karten.Ass;
import ch.bodesuri.pd.karten.KartenFarbe;
import ch.bodesuri.pd.regelsystem.ZugEingabe;
import ch.bodesuri.pd.regelsystem.verstoesse.RegelVerstoss;
import ch.bodesuri.pd.zugsystem.Aktion;
import ch.bodesuri.pd.zugsystem.Bewegung;

/**
 * Testet die Funktionalität des Zugsystems, speziell die der Zugerfassung.
 */
public class ZugTest extends ProblemDomainTestCase {
	/**
	 * Erstellt eine neue Zugeingabe mit einer Bewegung und einer Karte
	 * und versucht diese zu validieren.
	 * @throws RegelVerstoss
	 */
	public void testZugEingabe() throws RegelVerstoss {
		Bewegung bewegung = new Bewegung(lager(0), bank(0));
		Ass ass = new Ass(KartenFarbe.Herz);
		ZugEingabe ze = new ZugEingabe(spieler(0), ass, bewegung);
		
		assertEquals(spieler(0), ze.getSpieler());
		assertEquals(ass, ze.getKarte());
		assertEquals(bewegung, ze.getBewegung());
		
		ze.validiere();
	}

	/**
	 * Prüft, ob verbotene Aktionen auch tatsächlich verhindert werden.
	 */
	public void testAktionAufBesetztesFeld() {
		Aktion aktion = new Aktion(lager(0), lager(1));
		try {
			aktion.ausfuehren();
			fail("Sollte RuntimeException geben.");
		} catch (RuntimeException e) {
			assertNotNull(e);
		}
	}
}
