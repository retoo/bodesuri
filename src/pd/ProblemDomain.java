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

import dienste.serialisierung.Codierer;
import pd.regelsystem.karten.KartenGeber;
import pd.serialisierung.CodiererThreads;
import pd.spiel.Spiel;

/* Signiert durch: rschuett */

/**
 * Einstiegspunkt der Problem-Domain, welcher den {@link Codierer}, das
 * {@link Spiel} und den {@link KartenGeber} erstellt.
 */
public class ProblemDomain {
	private Codierer codierer;
	private Spiel spiel;
	private KartenGeber kartenGeber;

	public ProblemDomain() {
		/*
		 * Der Codierer muss zuerst erstellt werden, damit Spiel und KartenGeber
		 * ihn verwenden können.
		 */
		codierer = new Codierer();
		CodiererThreads.registriere(Thread.currentThread(), codierer);

		spiel = new Spiel();
		kartenGeber = new KartenGeber();
	}

	/**
	 * @return Codierer der Problem-Domain
	 */
	public Codierer getCodierer() {
		return codierer;
	}

	/**
	 * @return Spiel der Problem-Domain
	 */
	public Spiel getSpiel() {
		return spiel;
	}

	/**
	 * @return Kartengeber der Problem-Domain, von dem Karten bezogen werden können
	 */
	public KartenGeber getKartenGeber() {
    	return kartenGeber;
    }
}
