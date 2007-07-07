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
import pd.regelsystem.verstoesse.Verstoesse;
import pd.spiel.brett.Feld;
import pd.spiel.spieler.Figur;
import pd.spiel.spieler.Spieler;
import pd.zugsystem.Bewegung;
import pd.zugsystem.Weg;

/**
 * Gleich wie {@link VorwaertsRegel}, aber Zugrichtung ist rückwärts.
 */
public class RueckwaertsRegel extends VorwaertsRegel {
	public RueckwaertsRegel(int schritte) {
		super(schritte);
		setBeschreibung(schritte + " rückwärts");
	}

	public boolean arbeitetMitWeg() {
		return true;
	}

	protected void pruefeBewegung(Bewegung bewegung, Spieler spieler) throws RegelVerstoss {
		Feld start = bewegung.start;
		Feld ziel  = bewegung.ziel;

		if (start.istLager() && ziel.istLager()) {
			throw new Verstoesse.ImLagerFahren();
		} else if (start.istLager()) {
			throw new Verstoesse.FalschStarten();
		} else if (ziel.istLager()) {
			throw new Verstoesse.InsLagerFahren();
		} else if (start.istHimmel()) {
				throw new Verstoesse.ImHimmelNurVorwaertsFahren();
		} else if (ziel.istHimmel()) {
			throw new Verstoesse.RueckwaertsInHimmelFahren();
		}
	}

	protected void pruefeWegRichtung(Weg weg) throws RegelVerstoss {
		if (!weg.istRueckwaerts()) {
			throw new Verstoesse.NurRueckwaertsFahren();
		}
	}

	protected void liefereZugEingaben(Spieler spieler, Karte karte,
	                                  ZugEingabeAbnehmer abnehmer) {
		for (Figur figur : spieler.getZiehbareFiguren()) {
			Feld start = figur.getFeld();

			if (start.istLager() || start.istHimmel()) {
				continue;
			}

			Feld ziel = getZiel(start, schritte);
			ZugEingabe ze = getMoeglicheZugEingabe(spieler, karte, start, ziel);
			if (ze != null) {
				boolean abbrechen = abnehmer.nehmeEntgegen(ze);
				if (abbrechen) {
					return;
				}
			}
		}
	}

	private Feld getZiel(Feld start, int schritte) {
		Feld feld = start;
		for (int i = 0; i < schritte; ++i) {
			feld = feld.getVorheriges();
		}
		return feld;
	}
}
