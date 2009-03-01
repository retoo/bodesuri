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

import ch.bodesuri.pd.spiel.spieler.Figur;
import ch.bodesuri.pd.spiel.spieler.Partnerschaft;
import ch.bodesuri.pd.spiel.spieler.Spieler;

public class PartnerschaftTest extends ProblemDomainTestCase {
	public void testIstFertig() {
		Spieler spielerA = spieler(0);
		Spieler spielerB = spieler(2);
		Partnerschaft partnerschaft = new Partnerschaft(spielerA, spielerB);
		assertEquals(spielerA, partnerschaft.getSpielerA());
		assertEquals(spielerB, partnerschaft.getSpielerB());

		assertFalse(spielerA.istFertig());
		assertFalse(spielerB.istFertig());
		assertFalse(partnerschaft.istFertig());

		macheFertig(spielerA);
		assertTrue(spielerA.istFertig());
		assertFalse(partnerschaft.istFertig());

		macheFertig(spielerB);
		assertTrue(spielerB.istFertig());
		assertTrue(partnerschaft.istFertig());
	}

	private void macheFertig(Spieler spieler) {
		for (int i = 0; i < 4; ++i) {
			Figur figur = spieler.getFiguren().get(i);
			figur.versetzeAuf(brett.getHimmelFelderVon(spieler).get(i));
		}
	}
}
