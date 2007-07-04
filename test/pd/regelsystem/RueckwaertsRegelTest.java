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


package pd.regelsystem;

import pd.regelsystem.verstoesse.RegelVerstoss;

/**
 * Testet die Funktionalität der Rückwärtsregel an vielen verschiedenen
 * Standorten, an der eine Figur stehen kann (Himmel, Bank, Lager, normales
 * Feld).
 */
public class RueckwaertsRegelTest extends RegelTestCase {
	/**
	 * Die Weglänge muss stimmen.
	 */
	public void testRueckwaertsFalscheWegLaenge() {
		start = bank(0).getNtesFeld(4);
		ziel  = bank(0);
		lager(0).versetzeFigurAuf(start);
		sollteVerstossGeben(new VorwaertsRegel(5));
	}

	/**
	 * Validiert einen Rückwärtszug, dessen Ziel ein Bankfeld ist.
	 * Bankfeld.
	 * @throws RegelVerstoss
	 */
	public void testRueckwaerts() throws RegelVerstoss {
		start = bank(0).getNtesFeld(5);
		ziel  = bank(0);
		lager(0).versetzeFigurAuf(start);
		sollteValidieren(new RueckwaertsRegel(5));
	}

	/**
	 * Validiert einen Rückwärtszug, dessen Start ein Bankfeld ist
	 * und dessen Ziel vier Felder nach dem Bankfeld liegt. Dies
	 * entspricht in Realität einem Vorwärtszug und gibt deshalb 
	 * einen Verstoss.
	 */
	public void testRueckwaertsVorwaerts() {
		start = bank(0);
		ziel  = bank(0).getNtesFeld(5);
		lager(0).versetzeFigurAuf(start);
		sollteVerstossGeben(new RueckwaertsRegel(5));
	}

	/**
	 * Validiert einen Rückwärtszug, dessen Start im Himmel ist und
	 * dessen Ziel ebenfalls wieder im Himmel ist. Dies ist zwar eine
	 * Rückwärtsbewegung, ist jedoch nicht gültig und gibt einen Verstoss.
	 */
	public void testRueckwaertsImHimmel() {
		start = himmel(0, 3);
		ziel  = himmel(0, 1);
		lager(0).versetzeFigurAuf(start);
		sollteVerstossGeben(new RueckwaertsRegel(2));
	}

	/**
	 * Validiert einen Rückwärtszug, dessen Start im Himmel ist und dessen
	 * Ziel das Bankfeld ist. Dies ist ein ungültiger Zug und gibt einen
	 * Verstoss.
	 */
	public void testRueckwaertsAusHimmel() {
		start = himmel(0).getNaechstes();
		ziel  = bank(0);
		lager(0).versetzeFigurAuf(start);
		sollteVerstossGeben(new RueckwaertsRegel(2));
	}

	/**
	 * Validiert einen Rückwärtszug, dessen Start ein Feld nach dem Bankfeld
	 * ist und dessen Ziel ein Himmelfeld ist. Solch ein Zug ist ungültig
	 * und gibt einen Verstoss.
	 */
	public void testRueckwaertsInHimmel() {
		start = bank(0).getNaechstes();
		ziel  = himmel(0);
		lager(0).versetzeFigurAuf(start);
		sollteVerstossGeben(new RueckwaertsRegel(2));
	}

	/**
	 * Rückwärtszüge aus dem Lager heraus müssen auch einen Verstoss geben.
	 */
	public void testRueckwaertsAusLager() {
		start = lager(0);
		ziel  = bank(0);
		sollteVerstossGeben(new RueckwaertsRegel(1));
	}

	/**
	 * Rückwärtszüge in das Lager ergeben auch einen Verstoss.
	 */
	public void testRueckwaertsInLager() {
		start = bank(0);
		ziel  = lager(0);
		lager(0).versetzeFigurAuf(start);
		sollteVerstossGeben(new RueckwaertsRegel(1));
	}
}
