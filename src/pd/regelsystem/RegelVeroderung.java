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

import java.util.Vector;

import pd.regelsystem.karten.Karte;
import pd.regelsystem.verstoesse.RegelVerstoss;
import pd.spiel.spieler.Spieler;
import pd.zugsystem.Zug;

/**
 * Regel, die andere Regeln beinhalten kann. Ist gültig, wenn eine der
 * enthaltenen Regeln gültig ist.
 */
public class RegelVeroderung extends Regel {
	private Vector<Regel> regeln = new Vector<Regel>();

	/**
	 * Füge eine Regel der Veroderung hinzu.
	 *
	 * @param regel Regel, die hinzugefügt wird
	 */
	public void fuegeHinzu(Regel regel) {
		regeln.add(regel);
	}

	public boolean arbeitetMitWeg() {
		for (Regel regel : regeln) {
			if (regel.arbeitetMitWeg()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Beim Validieren werden alle enthaltenen Regeln durchgegangen und der
	 * erste gültige Zug zurückgegeben.
	 * 
	 * Wenn keine Regel gültig ist, wird der RegelVerstoss mit der höchsten
	 * Spezifität geworfen.
	 */
	public Zug validiere(ZugEingabe zugEingabe) throws RegelVerstoss {
		RegelVerstoss verstoss = null;
		for (Regel regel : regeln) {
			try {
				Zug resultat = regel.validiere(zugEingabe);
				return resultat;
			} catch (RegelVerstoss rv) {
				if (verstoss == null ||
				    rv.getSpezifitaet() > verstoss.getSpezifitaet()) {
					verstoss = rv;
				}
			}
		}
		throw verstoss;
	}

	protected void liefereZugEingaben(Spieler spieler, Karte karte,
	                                  ZugEingabeAbnehmer abnehmer) {
		for (Regel regel : regeln) {
			regel.liefereZugEingaben(spieler, karte, abnehmer);
		}
	}

	public String getBeschreibung() {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < regeln.size(); ++i) {
			s.append(regeln.get(i).getBeschreibung());
			if (i < regeln.size() - 2) {
				s.append(", ");
			} else if (i == regeln.size() - 2) {
				s.append(" oder ");
			}
		}
		return s.toString();
	}
}
