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

import dienste.serialisierung.Codierer;

import junit.framework.TestCase;
import pd.regelsystem.karten.KartenGeber;
import pd.spiel.Spiel;
import pd.spiel.brett.BankFeld;
import pd.spiel.brett.Brett;
import pd.spiel.brett.HimmelFeld;
import pd.spiel.brett.LagerFeld;
import pd.spiel.spieler.Spieler;

/**
 * Ist Basis aller TestCases, welche die Problem-Domain abdecken. Initialisiert
 * weite Teile der PD und bietet einige Hilfsmethoden an.
 */
public abstract class ProblemDomainTestCase extends TestCase {
	protected Spiel spiel;
	protected KartenGeber kartenGeber;
	protected Brett brett;
	protected Codierer codierer;
	
	protected void setUp() {
		ProblemDomain problemDomain = new ProblemDomain();

		spiel       = problemDomain.getSpiel();
		kartenGeber = problemDomain.getKartenGeber();
		codierer    = problemDomain.getCodierer();

		brett = spiel.getBrett();

		for (int i = 0; i < 4; ++i) {
			spiel.fuegeHinzu("Spieler" + i);
		}

		List<Spieler> spieler = spiel.getSpieler();
		spieler.get(0).setPartner(spieler.get(2));
		spieler.get(2).setPartner(spieler.get(0));
		spieler.get(1).setPartner(spieler.get(3));
		spieler.get(3).setPartner(spieler.get(1));
	}
	
	/**
	 * Liefert den Spieler anhand der Nummer.
	 * 
	 * @param nummer
	 * 			Nummer des Spielers im Array/Vektor
	 * @return Spezifizierten Spieler
	 */
	protected Spieler spieler(int nummer) {
		return spiel.getSpieler().get(nummer);
	}
	
	/**
	 * Liefert das Bankfeld des Spielers.
	 * 
	 * @param spieler
	 * 			Spieler
	 * @return Das Bankfeld des Spielers.
	 */
	protected BankFeld bank(int spieler) {
		return brett.getBankFeldVon(spiel.getSpieler().get(spieler));
	}
	
	/**
	 * Liefert erstes Lagerfeld des Spielers.
	 * 
	 * @param spieler
	 * 			Spieler
	 * @return Das erste Lagerfeld des Spielers.
	 */
	protected LagerFeld lager(int spieler) {
		return lager(spieler, 0);
	}
	
	/**
	 * Liefert ein konkretes Lagerfeld des Spielers.
	 * 
	 * @param spieler
	 * 			Spieler
	 * @param feld
	 * 			Konkretes Feld.
	 * @return Konkretes Lagerfeld des Spielers.
	 */
	protected LagerFeld lager(int spieler, int feld) {
		return brett.getLagerFelderVon(spiel.getSpieler().get(spieler)).get(feld);
	}
	
	/**
	 * Liefert erstes Himmelfeld des Spielers.
	 * 
	 * @param spieler
	 * 			Spieler
	 * @return Das erste Lagerfeld des Spielers.
	 */
	protected HimmelFeld himmel(int spieler) {
		return himmel(spieler, 0);
	}
	
	/**
	 * Liefert ein konkretes Himmelfeld des Spielers.
	 * 
	 * @param spieler
	 * 			Spieler
	 * @param feld
	 * 			Konkretes Feld.
	 * @return Konkretes Himmelfeld des Spielers.
	 */
	protected HimmelFeld himmel(int spieler, int feld) {
		return brett.getHimmelFelderVon(spiel.getSpieler().get(spieler)).get(feld);
	}
}
