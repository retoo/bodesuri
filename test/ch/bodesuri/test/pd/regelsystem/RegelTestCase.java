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

import java.util.List;
import java.util.Vector;

import ch.bodesuri.pd.regelsystem.Regel;
import ch.bodesuri.pd.regelsystem.ZugEingabe;
import ch.bodesuri.pd.regelsystem.verstoesse.RegelVerstoss;
import ch.bodesuri.pd.spiel.brett.Feld;
import ch.bodesuri.pd.spiel.spieler.Spieler;
import ch.bodesuri.pd.zugsystem.Bewegung;
import ch.bodesuri.pd.zugsystem.Zug;
import ch.bodesuri.test.pd.ProblemDomainTestCase;


/**
 * Ist Basis für alle TestCases des Regelsystems. Bietet zusätzlich zu
 * den Initialisierungen von <Code>ProblemDomainTestCase</code> noch
 * spezielle Instanzvariablen und Methoden für die Validierung.
 */
public abstract class RegelTestCase extends ProblemDomainTestCase {
	protected Spieler spieler;
	protected Feld start;
	protected Feld ziel;
	protected Regel regel;

	protected void setUp() {
		super.setUp();
		spieler = spieler(0);
	}

	/**
	 * Validiert die standardmässig definierte Regel oder wirft
	 * bei einem Fehlschlag eine Exception.
	 * @throws RegelVerstoss
	 */
	protected void sollteValidieren() throws RegelVerstoss {
		sollteValidieren(regel);
	}

	/**
	 * Validiert die angegebene Regel oder wirft bei einem Fehlschlag
	 * eine Exception.
	 * 
	 * @param regel
	 * 			zu validierende Regel.
	 * @throws RegelVerstoss
	 */
	protected void sollteValidieren(Regel regel) throws RegelVerstoss {
		if (regel == null) {
			throw new RuntimeException("Keine Regel zum Validieren angegeben.");
		}

		ZugEingabe ze = new ZugEingabe(spieler, null, getBewegungen());

		List<ZugEingabe> moeglicheZuege = regel.getMoeglicheZuege(spieler, null);
		assertTrue(moeglicheZuege.contains(ze));

		Zug zug = regel.validiere(ze);
		zug.ausfuehren();
    }

	/**
	 * Prüft, ob Standardregel einen Verstoss verursacht.
	 */
	protected void sollteVerstossGeben() {
		sollteVerstossGeben(regel);
	}

	/**
	 * Prüft, ob eine angegebene Regel einen Verstoss verursacht.
	 * 
	 * @param regel
	 * 			Regel, die den Verstoss auslösen sollte.
	 */
	protected void sollteVerstossGeben(Regel regel) {
		ZugEingabe ze = new ZugEingabe(spieler, null, getBewegungen());

		List<ZugEingabe> moeglicheZuege = regel.getMoeglicheZuege(spieler, null);
		assertFalse(moeglicheZuege.contains(ze));

        try {
	        regel.validiere(ze);
	        fail("Sollte RegelVerstoss geben.");
        } catch (RegelVerstoss rv) {
        	assertNotNull(rv);
        }
	}

	/**
	 * Erstellt eine Liste mit eienr einzelnen Bewegung.
	 * 
	 * @return Liste mit einer Bewegungen.
	 */
	protected List<Bewegung> getBewegungen() {
		Vector<Bewegung> bewegungen = new Vector<Bewegung>();
		bewegungen.add(new Bewegung(start, ziel));
		return bewegungen;
	}
}
